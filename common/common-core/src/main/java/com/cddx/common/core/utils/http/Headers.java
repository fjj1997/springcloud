package com.cddx.common.core.utils.http;

import lombok.extern.log4j.Log4j2;

import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求头
 * 
 * @author 范劲松
 */
@Log4j2
public class Headers {

    /**
     * 请求头
     */
    private static final Map<String, String> HEADERS = new HashMap<>();

    static {
        // 设置默认的请求头
        HEADERS.put("Accept-Charset", "utf-8");
        HEADERS.put("accept", "*/*");
        HEADERS.put("contentType", "utf-8");
        HEADERS.put("connection", "Keep-Alive");
    }

    /**
     * 增加请求头
     *
     * @param key   键
     * @param value 值
     */
    public Headers addHeader(String key, String value) {
        if (HttpHelper.isObjectNotEmpty(key) && HttpHelper.isObjectNotEmpty(value)) {
            HEADERS.put(key, value);
        }
        return this;
    }

    /**
     * 直接设置header到urlconnect
     *
     * @param connection 链接对象
     */
    public URLConnection setHeaders(URLConnection connection) {
        if (connection != null) {
            HEADERS.entrySet().stream().parallel().forEach((item) -> {
                connection.setRequestProperty(item.getKey(), item.getValue());
            });
            log.info("headers: {}", connection.getRequestProperties());
        }
        return connection;
    }

    /**
     * 获取某个请求头的值
     *
     * @param key 请求头
     * @return 值
     */
    public String getValue(String key) {
        return HEADERS.get(key);
    }

    /**
     * 获得所有请求头
     *
     * @return 所有请求头
     */
    protected Map<String, String> getHeaders() {
        return HEADERS;
    }
    
}
