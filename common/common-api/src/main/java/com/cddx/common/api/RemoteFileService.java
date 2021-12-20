package com.cddx.common.api;

import com.cddx.common.api.factory.RemoteFileFallbackFactory;
import com.cddx.common.core.constant.ServiceNameConstants;
import com.cddx.common.core.model.vo.file.File;
import com.cddx.common.core.web.response.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 *
 * @author 范劲松
 */
@FeignClient(contextId = "remoteFileService", value = ServiceNameConstants.FILE_SERVICE, fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService {
    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<File> upload(@RequestPart(value = "file") MultipartFile file);
}
