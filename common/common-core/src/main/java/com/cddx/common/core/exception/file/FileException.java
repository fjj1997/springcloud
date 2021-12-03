package com.cddx.common.core.exception.file;

import com.cddx.common.core.exception.BaseException;

/**
 * 文件信息异常类
 *
 * @author 范劲松
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
