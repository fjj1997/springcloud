package com.cddx.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件接口类
 *
 * @author 范劲松
 */
public interface IFileService {
    /**
     * 文件上传
     *
     * @param file MultipartFile文件对象
     * @return 文件访问地址
     * @exception Exception
     */
    String uploadFile(MultipartFile file) throws Exception;
}
