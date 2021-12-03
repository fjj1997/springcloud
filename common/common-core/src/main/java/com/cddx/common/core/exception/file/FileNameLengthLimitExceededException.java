package com.cddx.common.core.exception.file;

import com.cddx.common.core.exception.file.FileException;

/**
 * 文件名称超长限制异常类
 *
 * @author 范劲松
 */
public class FileNameLengthLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[]{defaultFileNameLength});
    }
}
