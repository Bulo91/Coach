package com.example.coach.api;

public class ResponseApi<T> {
    private int code;
    private String message;
    private T result;

    public int getCode() { return code; }
    public String getMessage() { return message; }
    public T getResult() { return result; }
}

