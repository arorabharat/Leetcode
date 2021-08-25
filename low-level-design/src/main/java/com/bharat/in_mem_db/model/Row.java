package com.bharat.in_mem_db.model;

import java.util.Collections;
import java.util.List;

public class Row {

    List<DataType> values;

    public Row(List<DataType> values) {
        this.values = values;
    }

    public List<DataType> getValues() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public String toString() {
        return "Row{" +
                "values=" + values +
                '}';
    }
}

