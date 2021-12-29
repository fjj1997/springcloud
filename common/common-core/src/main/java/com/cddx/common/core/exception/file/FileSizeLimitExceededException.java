package com.cddx.common.core.exception.file;

/**
 * 文件名大小限制异常类
 *
 * @author 范劲松
 */
public class FileSizeLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }
}
