package com.example.coach.api;

public interface ICallbackApi<T> {
    void onSuccess(T result);
    void onError();
}
