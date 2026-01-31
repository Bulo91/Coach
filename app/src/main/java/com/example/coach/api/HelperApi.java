package com.example.coach.api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelperApi {

    private static final IRequestApi api =
            CoachApi.getRetrofit().create(IRequestApi.class);

    public static IRequestApi getApi() {
        return api;
    }

    public static <T> void call(Call<ResponseApi<T>> call, ICallbackApi<T> callback) {
        call.enqueue(new Callback<ResponseApi<T>>() {
            @Override
            public void onResponse(Call<ResponseApi<T>> call, Response<ResponseApi<T>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API", "code=" + response.body().getCode()
                            + " message=" + response.body().getMessage()
                            + " result=" + response.body().getResult());
                    callback.onSuccess(response.body().getResult());
                } else {
                    Log.e("API", "Erreur API: " + response.code());
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<T>> call, Throwable t) {
                Log.e("API", "Erreur API", t);
                callback.onError();
            }
        });
    }
}
