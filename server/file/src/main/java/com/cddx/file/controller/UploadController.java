package com.cddx.file.controller;

import com.cddx.common.core.model.vo.file.File;
import com.cddx.common.core.utils.file.FileUtils;
import com.cddx.common.core.web.response.R;
import com.cddx.file.service.IFileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 上传控制器
 *
 * @author 范劲松
 */
@Log4j2
@RestController
public class UploadController {

    @Resource
    private IFileService iFileService;

    /**
     * 文件上传请求
     */
    @PostMapping("upload")
    public R<File> upload(MultipartFile file) {
        try {
            // 上传并返回访问地址
            String url = iFileService.uploadFile(file);
            File f = new File();
            f.setName(FileUtils.getName(url));
            f.setUrl(url);
            return R.ok(f);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }

}
