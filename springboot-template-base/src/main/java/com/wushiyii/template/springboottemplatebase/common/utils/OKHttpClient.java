package com.wushiyii.template.springboottemplatebase.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.wushiyii.template.springboottemplatebase.common.constants.Constants;
import com.wushiyii.template.springboottemplatebase.common.dict.ErrorCode;
import com.wushiyii.template.springboottemplatebase.exception.BizRuntimeException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OKHttpClient {

    private static final long CONNECTION_TIMEOUT = 1000;

    private static final String HEADER_CONTENT_TYPE = "Content-Type";


    private static final ObjectMapper om = new ObjectMapper();

    private final OkHttpClient okHttpClient;

    static {
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public OKHttpClient(long readTimeout) {
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .build();
    }

    public OKHttpClient(long readTimeout, ConnectionPool connectionPool) {
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .connectionPool(connectionPool)
                .build();
    }


    public <T> T post(String url, Object request, Class<T> resultClass) {
        return post(url, request, resultClass, Maps.newHashMap());
    }

    public <T> T post(String url, Object request, Class<T> resultClass, Map<String, String> headers) {
        try {
            String res = doPostRequest(url, request, headers);

            return om.readValue(res, resultClass);

        } catch (Exception e) {
            try {
                log.error("HttpClient异常,url:{},params;{}", url, om.writeValueAsString(request), e);
            } catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
        }
        throw new BizRuntimeException(ErrorCode.ERR_HTTP_CLIENT, "HttpClient异常:" + url);
    }


    /**
     * 直接返回body字符串
     *
     * @param url
     * @param request
     * @return
     */
    public String post(String url, Object request) {
        try {
            return doPostRequest(url, request, Maps.newHashMap());
        } catch (Exception e) {
            try {
                log.error("HttpClient异常,url:{},params;{}", url, om.writeValueAsString(request), e);
            } catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
            throw new BizRuntimeException(ErrorCode.ERR_HTTP_CLIENT, "HttpClient异常:" + url);
        }
    }


    /**
     * 直接返回body字符串
     *
     * @param url
     * @param request
     * @param headers
     * @return
     */
    public String post(String url, Object request, Map<String, String> headers) {
        try {
            return doPostRequest(url, request, headers);
        } catch (Exception e) {
            try {
                log.error("HttpClient异常,url:{},params;{}", url, om.writeValueAsString(request), e);
            } catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
            throw new BizRuntimeException(ErrorCode.ERR_HTTP_CLIENT, "HttpClient异常:" + url);
        }
    }


    private String doPostRequest(String url, Object requestBody, Map<String, String> headerMap) throws IOException {
        String json = om.writeValueAsString(requestBody);
        String body = null;
        try {
            Map<String, String> headers = Optional.ofNullable(headerMap).orElse(new HashMap<>());
            if (!headers.containsKey(OKHttpClient.HEADER_CONTENT_TYPE)) {
                headers.put(OKHttpClient.HEADER_CONTENT_TYPE, "application/json;charset=UTF-8");
            }
            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse(headers.get(OKHttpClient.HEADER_CONTENT_TYPE)), json));

            addHeaders(builder, headers);
            addTraceHeaders(builder);
            Response response = okHttpClient.newCall(builder.build()).execute();
            if (!response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    body = responseBody.string();
                }
                log.error("POST {} request={},code={},res={}", url, json, response.code(), body);
                String message = String.format("http post error, code=%s, message=%s, url=%s",
                        response.code(), response.message(), response.request().url());
                throw new BizRuntimeException(response.code(), message);
            }
            body = response.body().string();
        } finally {
            log.info("POST {} request:{},response:{}", url, json, body);
        }

        return body;
    }

    /**
     * 直接返回body字符串
     *
     * @param url
     * @param request
     * @return
     */
    public String delete(String url, Object request) {
        try {
            return doDeleteRequest(url, request, Maps.newHashMap());
        } catch (Exception e) {
            try {
                log.error("HttpClient异常,url:{},params;{}", url, om.writeValueAsString(request), e);
            } catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
            throw new BizRuntimeException(ErrorCode.ERR_HTTP_CLIENT, "HttpClient异常:" + url);
        }
    }

    /**
     * 直接返回body字符串
     *
     * @param url
     * @param request
     * @param headers
     * @return
     */
    public String delete(String url, Object request, Map<String, String> headers) {
        try {
            return doDeleteRequest(url, request, headers);
        } catch (Exception e) {
            try {
                log.error("HttpClient异常,url:{},params;{}", url, om.writeValueAsString(request), e);
            } catch (Exception e1) {
                log.error(e1.getMessage(), e1);
            }
            throw new BizRuntimeException(ErrorCode.ERR_HTTP_CLIENT, "HttpClient异常:" + url);
        }
    }

    private String doDeleteRequest(String url, Object requestBody, Map<String, String> headers) throws IOException {
        String json = om.writeValueAsString(requestBody);
        String body = null;
        try {
            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .delete(RequestBody.create(MediaType.parse("application/json"), json))
                    .addHeader("Content-Type", "application/json");
            addHeaders(builder, headers);
            addTraceHeaders(builder);
            Response response = okHttpClient.newCall(builder.build()).execute();
            if (!response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    body = responseBody.string();
                }
                log.error("DELETE {} request={},code={},res={}", url, json, response.code(), body);
                String message = String.format("http delete error, code=%s, message=%s, url=%s",
                        response.code(), response.message(), response.request().url());
                throw new BizRuntimeException(response.code(), message);
            }
            body = response.body().string();
        } finally {
            log.info("DELETE {} request:{},response:{}", url, json, body);
        }

        return body;
    }


    public <T> T get(String url, Class<T> resultClass) {
        return get(url, resultClass, Maps.newHashMap());
    }

    public <T> T get(String url, Class<T> resultClass, Map<String, String> headers) {
        try {
            String res = doGetRequest(url, headers);

            return om.readValue(res, resultClass);

        } catch (Exception e) {
            log.error("HttpClient异常,url:{}", url, e);
        }
        throw new BizRuntimeException(ErrorCode.ERR_HTTP_CLIENT, "HttpClient异常:" + url);
    }


    /**
     * 直接返回body字符串
     *
     * @param url
     * @return
     */
    public String get(String url) {
        try {
            return doGetRequest(url, Maps.newHashMap());
        } catch (Exception e) {
            log.error("HttpClient异常,url:{}", url, e);
            throw new BizRuntimeException(ErrorCode.ERR_HTTP_CLIENT, "HttpClient异常:" + url);
        }
    }


    /**
     * 直接返回body字符串
     *
     * @param url
     * @param headers
     * @return
     */
    public String get(String url, Map<String, String> headers) {
        try {
            return doGetRequest(url, headers);
        } catch (Exception e) {
            log.error("HttpClient异常,url:{},params;{}", url, e);
            throw new BizRuntimeException(ErrorCode.ERR_HTTP_CLIENT, "HttpClient异常:" + url);
        }
    }


    private String doGetRequest(String url, Map<String, String> headers) throws IOException {
        String body = null;
        try {
            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/json");
            addHeaders(builder, headers);
            addTraceHeaders(builder);
            Response response = okHttpClient.newCall(builder.build()).execute();
            if (!response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    body = responseBody.string();
                }
                log.error("GET {} code={},res={}", url, response.code(), body);
                String message = String.format("http post error, code=%s, message=%s, url=%s",
                        response.code(), response.message(), response.request().url());
                throw new BizRuntimeException(response.code(), message);
            }
            body = response.body().string();
        } finally {
            log.info("GET {} response:{}", url, body);
        }
        return body;
    }


    private void addHeaders(Request.Builder builder, Map<String, String> headers) {
        for (Map.Entry<String, String> e : headers.entrySet()) {
            builder.addHeader(e.getKey(), e.getValue());
        }
    }

    private void addTraceHeaders(Request.Builder builder) {
        builder.addHeader(Constants.X_TRACE_ID, this.getTraceValue(Constants.X_TRACE_ID));
        builder.addHeader(Constants.X_PARENT_NAME, this.getTraceValue(Constants.X_PARENT_NAME));
        builder.addHeader(Constants.X_PARENT_HOSTNAME, this.getTraceValue(Constants.X_PARENT_HOSTNAME));
        builder.addHeader(Constants.X_PARENT_ID, this.getTraceValue(Constants.X_SPAN_ID));
        builder.addHeader(Constants.X_SPAN_ID, OKHttpClient.traceId());
    }


    private String getTraceValue(String key) {
        String value = MDC.get(key);
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        return value;
    }


    public static String traceId() {
        return UUID.randomUUID().toString().toUpperCase();
    }

}
