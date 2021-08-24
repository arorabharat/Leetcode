package com.bharat.in_mem_db.model;

public class Response<K> {

    private final int statusCode;
    private final K object;

    public Response(int statusCode, K object) {
        this.statusCode = statusCode;
        this.object = object;
    }
}
