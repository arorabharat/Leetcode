package com.bharat.in_mem_db.model;

import java.util.Objects;

public class CustomString extends DataType {

    private final String value;

    public CustomString(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomString that = (CustomString) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
