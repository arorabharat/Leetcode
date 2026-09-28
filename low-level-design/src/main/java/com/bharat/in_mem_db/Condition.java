package com.bharat.in_mem_db;

import java.util.Objects;

public record Condition(String columnName, Operator operator, Object value) {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public boolean evaluate(Row row, Schema schema) {
        int colIndex = schema.getColumnIndex(columnName);
        Object cell = row.get(colIndex);

        if (cell == null || value == null) {
            return switch (operator) {
                case EQUALS -> Objects.equals(cell, value);
                case NOT_EQUALS -> !Objects.equals(cell, value);
                default -> false;
            };
        }

        if (operator == Operator.EQUALS) return cell.equals(value);
        if (operator == Operator.NOT_EQUALS) return !cell.equals(value);

        if (cell instanceof Comparable c && value.getClass().isAssignableFrom(cell.getClass())) {
            int cmp = c.compareTo(value);
            return switch (operator) {
                case GREATER_THAN -> cmp > 0;
                case LESS_THAN -> cmp < 0;
                case GREATER_THAN_OR_EQUAL -> cmp >= 0;
                case LESS_THAN_OR_EQUAL -> cmp <= 0;
                default -> false;
            };
        }

        throw new IllegalArgumentException(
                "Cannot perform " + operator + " comparison on values of type "
                        + cell.getClass().getSimpleName() + " and " + value.getClass().getSimpleName()
        );
    }
}
