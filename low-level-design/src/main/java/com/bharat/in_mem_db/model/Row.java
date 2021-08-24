package com.bharat.in_mem_db.model;

import java.util.Collections;
import java.util.List;

public class Row {

    DataType primaryKey;
    List<DataType> values;

    public Row(DataType primaryKey, List<DataType> values) {
        this.primaryKey = primaryKey;
        this.values = values;
    }

    public DataType getPrimaryKey() {
        return primaryKey;
    }

    public List<DataType> getValues() {
        return Collections.unmodifiableList(values);
    }
}

