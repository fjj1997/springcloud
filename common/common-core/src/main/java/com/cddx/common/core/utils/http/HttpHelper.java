package com.cddx.common.core.utils.http;

import com.alibaba.fastjson.JSONObject;
import com.cddx.common.core.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

/**
 * 通用http工具封装
 *
 * @author 范劲松
 */
@Log4j2
public class HttpHelper {

    /**
     * 获取 String Body
     *
     * @param request ServletRequest
     * @return 结果
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try (InputStream inputStream = request.getInputStream()) {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.warn("getBodyString出现问题！");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(ExceptionUtils.getMessage(e));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取 JSON Body
     *
     * @param request ServletRequest
     * @return 结果
     */
    public static JSONObject getBodyJson(ServletRequest request) {
        return JSONObject.parseObject(getBodyString(request));
    }

    /**
     * 排序参数，同时过滤掉空key, 并转换为key=value&key=value形式
     *
     * @param param 参数
     * @return 结果
     */
    public static String formatParam(Map<String, Object> param) {
        if (StringUtils.isNull(param)) {
            return "";
        }
        // 对参数进行排序
        param = new TreeMap<>(param);
        // 参数拼接
        StringBuilder sb = new StringBuilder();
        param.entrySet().stream()
                // 过滤空key和空value
                .filter((e) -> isObjectNotEmpty(e.getKey()) && isObjectNotEmpty(e.getValue()))
                .forEach((e) -> {
                    sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
                });
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 判断是否空对象
     *
     * @param object 对象
     * @return 结果
     */
    public static boolean isObjectNotEmpty(Object object) {
        boolean flag;
        // 判断是不是null
        flag = StringUtils.isNotNull(object);
        // 如果是字符串类型，还需判断 "" 和 "  " 的情况
        if (flag && object.getClass() == String.class) {
            flag = StringUtils.isNotEmpty((String) object) && StringUtils.isNotBlank((String) object);
        }
        return flag;
    }


    /**
     * If the parameter data was sent in the request body, such as occurs
     * with an HTTP POST request, then reading the body directly via
     *
     * @param request HttpServletRequest
     * @return String
     * @see ServletRequest#getInputStream or
     * @see ServletRequest#getReader
     */
    public static JSONObject getPostData(HttpServletRequest request) {
        StringBuilder data = new StringBuilder();
        String line;
        BufferedReader reader;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return JSONObject.parseObject(data.toString());
    }
}
