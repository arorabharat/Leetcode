package com.bharat.in_mem_db.model;

import java.util.Objects;

public class CustomInt extends DataType {
    private final Integer value;

    public CustomInt(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomInt that = (CustomInt) o;
        return Objects.equals(value, that.value);
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "CustomInt{" +
                "value=" + value +
                '}';
    }
}
