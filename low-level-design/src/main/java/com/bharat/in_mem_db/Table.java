package com.bharat.in_mem_db;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Table {
    private final String name;
    private final Schema schema;

    // Primary Index: Primary Key -> Row
    private final Map<Object, Row> primaryIndex = new LinkedHashMap<>();

    // Secondary Indexes: Column Name -> (Indexed Value -> Set of Primary Keys)
    private final Map<String, Map<Object, Set<Object>>> secondaryIndexes = new HashMap<>();

    // Concurrency: allows multiple concurrent readers, exclusive writers
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public Table(String name, Schema schema) {
        this.name = name;
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public Schema getSchema() {
        return schema;
    }

    public void createIndex(String columnName) {
        rwLock.writeLock().lock();
        try {
            schema.getColumnIndex(columnName); // validates column exists
            Map<Object, Set<Object>> index = new HashMap<>();
            int colIdx = schema.getColumnIndex(columnName);
            int pkIdx = schema.getPrimaryKeyIndex();

            for (Row row : primaryIndex.values()) {
                Object val = row.get(colIdx);
                Object pk = row.get(pkIdx);
                index.computeIfAbsent(val, k -> new HashSet<>()).add(pk);
            }
            secondaryIndexes.put(columnName, index);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void insert(Row row) {
        rwLock.writeLock().lock();
        try {
            schema.validate(row);
            Object pk = row.get(schema.getPrimaryKeyIndex());
            if (primaryIndex.containsKey(pk)) {
                throw new IllegalStateException("Duplicate primary key: " + pk);
            }

            primaryIndex.put(pk, row);

            // Update secondary indexes
            for (Map.Entry<String, Map<Object, Set<Object>>> entry : secondaryIndexes.entrySet()) {
                int colIdx = schema.getColumnIndex(entry.getKey());
                Object val = row.get(colIdx);
                entry.getValue().computeIfAbsent(val, k -> new HashSet<>()).add(pk);
            }
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public Optional<Row> get(Object primaryKey) {
        rwLock.readLock().lock();
        try {
            return Optional.ofNullable(primaryIndex.get(primaryKey));
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public boolean delete(Object primaryKey) {
        rwLock.writeLock().lock();
        try {
            Row removedRow = primaryIndex.remove(primaryKey);
            if (removedRow == null) {
                return false;
            }

            // Remove from secondary indexes
            for (Map.Entry<String, Map<Object, Set<Object>>> entry : secondaryIndexes.entrySet()) {
                int colIdx = schema.getColumnIndex(entry.getKey());
                Object val = removedRow.get(colIdx);
                Set<Object> pks = entry.getValue().get(val);
                if (pks != null) {
                    pks.remove(primaryKey);
                    if (pks.isEmpty()) {
                        entry.getValue().remove(val);
                    }
                }
            }
            return true;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void update(Object primaryKey, Row updatedRow) {
        rwLock.writeLock().lock();
        try {
            if (!primaryIndex.containsKey(primaryKey)) {
                throw new NoSuchElementException("Row with primary key " + primaryKey + " not found");
            }
            schema.validate(updatedRow);

            Object newPk = updatedRow.get(schema.getPrimaryKeyIndex());
            if (!Objects.equals(primaryKey, newPk)) {
                throw new IllegalArgumentException("Modifying primary key during update is not supported");
            }

            // Re-use delete + insert logic internally for index consistency
            delete(primaryKey);
            insert(updatedRow);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public List<Row> query(List<Condition> conditions, List<String> projectedColumns, Integer limit) {
        rwLock.readLock().lock();
        try {
            Collection<Row> candidates = resolveCandidates(conditions);
            List<Row> results = new ArrayList<>();

            for (Row row : candidates) {
                boolean matches = true;
                if (conditions != null) {
                    for (Condition cond : conditions) {
                        if (!cond.evaluate(row, schema)) {
                            matches = false;
                            break;
                        }
                    }
                }

                if (matches) {
                    results.add(schema.project(row, projectedColumns));
                    if (limit != null && results.size() >= limit) {
                        break;
                    }
                }
            }
            return results;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    private Collection<Row> resolveCandidates(List<Condition> conditions) {
        if (conditions != null) {
            for (Condition cond : conditions) {
                if (cond.operator() == Operator.EQUALS && secondaryIndexes.containsKey(cond.columnName())) {
                    Set<Object> matchingPks = secondaryIndexes.get(cond.columnName()).get(cond.value());
                    if (matchingPks == null || matchingPks.isEmpty()) {
                        return Collections.emptyList();
                    }
                    List<Row> candidateRows = new ArrayList<>(matchingPks.size());
                    for (Object pk : matchingPks) {
                        Row r = primaryIndex.get(pk);
                        if (r != null) candidateRows.add(r);
                    }
                    return candidateRows;
                }
            }
        }
        return primaryIndex.values();
    }
}
