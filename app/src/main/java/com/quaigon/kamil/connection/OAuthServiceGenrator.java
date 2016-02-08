package com.quaigon.kamil.connection;


import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class OAuthServiceGenrator {

    public static final String API_BASE_URL = "https://online-go.com/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder retroBuilder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

//    public static <S> S createService (Class<S> serviceClass) {
//        return createService(serviceClass,null);
//    }

    public static <S> S createService(Class<S> serviceClass) {

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .method(original.method(),original.body());

                    Request request = requestBuilder.build();
                    return  chain.proceed(request);
                }
            });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = retroBuilder.client(client).build();
        return retrofit.create(serviceClass);
    }



    public static <S> S createService(Class<S> serviceClass, final AccessToken token ) {
        if (token != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
//                            .header("Authorization", token.getTokenType()+ " " + token.getAccessToken())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = retroBuilder.client(client).build();
        return retrofit.create(serviceClass);
    }




}
