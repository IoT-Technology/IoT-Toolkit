/*
 * Copyright © 2019-2025 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iot.technology.client.toolkit.common.http;

import iot.technology.client.toolkit.common.constants.HttpConfig;
import iot.technology.client.toolkit.common.constants.NbIoTHttpConfigEnum;
import okhttp3.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HttpRequestExecutor {

    public static final MediaType mediaType = MediaType.get("application/json;charset=utf-8");

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

    public static HttpResponseEntity executePost(HttpRequestEntity request) {
        RequestBody body = RequestBody.create(request.getJson(), mediaType);
        final OkHttpClient client = initOkHttp3(request.getType());

        HttpUrl.Builder httpBuilder = HttpUrl.parse(request.getUrl()).newBuilder();

        Request.Builder builder = new Request.Builder();
        builder.url(httpBuilder.build()).post(body);
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            builder.headers(Headers.of(request.getHeaders()));
        }
        Call call = client.newCall(builder.build());
        HttpResponseEntity entity = new HttpResponseEntity();
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                entity.setBody(Objects.requireNonNull(response.body()).string());
                entity.setMultiMap(response.headers().toMultimap());
            }
        } catch (Exception e) {
        }
        return entity;
    }

    public static HttpResponseEntity executePut(HttpRequestEntity request) {
        RequestBody body = RequestBody.create(request.getJson(), mediaType);
        final OkHttpClient client = initOkHttp3(request.getType());

        HttpUrl.Builder httpBuilder = HttpUrl.parse(request.getUrl()).newBuilder();
        if (request.getParams() != null && !request.getParams().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                httpBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        Request.Builder builder = new Request.Builder();
        builder.url(httpBuilder.build()).put(body);
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            builder.headers(Headers.of(request.getHeaders()));
        }
        Call call = client.newCall(builder.build());
        HttpResponseEntity entity = new HttpResponseEntity();
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                entity.setBody(Objects.requireNonNull(response.body()).string());
                entity.setMultiMap(response.headers().toMultimap());
            }
        } catch (Exception e) {
        }
        return entity;
    }

    public static HttpResponseEntity executeGet(HttpRequestEntity request) {
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
        HttpResponseEntity entity = new HttpResponseEntity();
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                entity.setBody(Objects.requireNonNull(response.body()).string());
                entity.setMultiMap(response.headers().toMultimap());
            }
        } catch (Exception e) {
        }
        return entity;
    }

    public static HttpResponseEntity executeDelete(HttpRequestEntity request) {
        RequestBody body = RequestBody.create(request.getJson(), mediaType);
        final OkHttpClient client = initOkHttp3(request.getType());

        HttpUrl.Builder httpBuilder = HttpUrl.parse(request.getUrl()).newBuilder();
        if (request.getParams() != null && !request.getParams().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                httpBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        Request.Builder builder = new Request.Builder();
        builder.url(httpBuilder.build()).delete(body);
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            builder.headers(Headers.of(request.getHeaders()));
        }
        Call call = client.newCall(builder.build());
        HttpResponseEntity entity = new HttpResponseEntity();
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                entity.setBody(Objects.requireNonNull(response.body()).string());
                entity.setMultiMap(response.headers().toMultimap());
            }
        } catch (Exception e) {
        }
        return entity;
    }

}
