package com.bharat.in_mem_db;

import java.util.Arrays;

public class Row {
    private final Object[] values;

    public Row(Object... values) {
        this.values = values;
    }

    public Object get(int index) {
        return values[index];
    }

    public int size() {
        return values.length;
    }

    public Object[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }
}
