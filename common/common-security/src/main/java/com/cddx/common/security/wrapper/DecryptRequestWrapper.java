package com.cddx.common.security.wrapper;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.IOUtils;

import javax.crypto.IllegalBlockSizeException;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 解密请求处理
 *
 * @author 范劲松
 */
@Log4j2
public class DecryptRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 解密密匙
     */
    private static final String DECODE_SECRET = "c3e861597efa4fabb3fb031ffe5138ad";

    /**
     * 参数字节数组
     */
    private byte[] requestByteBody;

    /**
     * Http请求对象
     */
    private final HttpServletRequest request;

    public DecryptRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    public void getParamsAndDecrypt() throws IOException, IllegalBlockSizeException {
        // 只解Body参数，Query参数不解
        if (null == this.requestByteBody) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(request.getInputStream(), baos);
            this.requestByteBody = baos.toByteArray();
            log.info("body: {}", JSONObject.parseObject(new String(this.requestByteBody)));
            // 获取加密数据
            String data = JSONObject.parseObject(new String(this.requestByteBody)).getString("data");
            // 解密
            data = decrypt(data);
            log.info("decode: {}", data);
            // 重新写入requestByteBody参数
            requestByteBody = data.getBytes(StandardCharsets.UTF_8);
        }
    }

    private static String decrypt(String data) throws IllegalBlockSizeException {
//        return SignatureUtils.decode(Mode.CTR, Padding.PKCS5Padding, DECODE_SECRET, data);
        return "";
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestByteBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() {
                return bais.read();
            }
        };
    }
}
