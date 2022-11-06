package iot.technology.client.toolkit.common.http;

import iot.technology.client.toolkit.common.constants.HttpConfig;
import iot.technology.client.toolkit.common.constants.NbIoTHttpConfigEnum;
import iot.technology.client.toolkit.common.constants.TelecomSettings;
import okhttp3.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpRequestExecutor {



    private static OkHttpClient initOkHttp3(String type) {
        HttpConfig config = NbIoTHttpConfigEnum.type(type);
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(config.getMaxIdleConnections(),
                        config.getKeepAliveSecond(), TimeUnit.SECONDS))
                .callTimeout(config.getCallTimeout(), TimeUnit.SECONDS)
                .connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(config.getWriteTimeout(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(config.getRetryOnConnectionFailure());
        return okHttpBuilder.build();
    }

    public static String executePost(HttpRequestEntity request) throws Exception {
        RequestBody body = RequestBody.create(request.getJson(), MediaType.get("application/json;charset=utf-8"));
        final OkHttpClient client = initOkHttp3(request.getType());

        HttpUrl.Builder httpBuilder = HttpUrl.parse(request.getUrl()).newBuilder();

        Request.Builder builder = new Request.Builder();
        builder.url(httpBuilder.build()).post(body);
        if (!request.getHeaders().isEmpty()) {
            builder.headers(Headers.of(request.getHeaders()));
        }
        Call call = client.newCall(builder.build());

        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                try (ResponseBody responseBody = response.body()) {
                    return responseBody.string();
                }
            } else {
                System.out.format("request error, code:%s, url:%s", response.code(), request.getUrl());
            }
        }
        return null;
    }

    public static HttpGetResponseEntity executeGet(HttpRequestEntity request) throws Exception {
        final OkHttpClient client = initOkHttp3(request.getType());

        HttpUrl.Builder urlBuilder = HttpUrl.parse(request.getUrl()).newBuilder();
        if (request.getParams() != null && !request.getParams().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        Request.Builder builder = new Request.Builder().url(urlBuilder.build());

        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            builder.headers(Headers.of(request.getHeaders()));
        }
        Call call = client.newCall(builder.build());
        HttpGetResponseEntity entity = new HttpGetResponseEntity();
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                entity.setBody(response.body().string());
                entity.setMultiMap(response.headers().toMultimap());
            }
        }
        return entity;
    }

    public static HttpGetResponseEntity executeDelete(HttpRequestEntity request) throws Exception {
        final OkHttpClient client = initOkHttp3(request.getType());

        HttpUrl.Builder urlBuilder = HttpUrl.parse(request.getUrl()).newBuilder();
        if (request.getParams() != null && !request.getParams().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        Request.Builder builder = new Request.Builder().url(urlBuilder.build()).delete();

        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            builder.headers(Headers.of(request.getHeaders()));
        }
        Call call = client.newCall(builder.build());
        HttpGetResponseEntity entity = new HttpGetResponseEntity();
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                entity.setBody(response.body().string());
                entity.setMultiMap(response.headers().toMultimap());
            }
        }
        return entity;
    }

}
