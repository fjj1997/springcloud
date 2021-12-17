package com.cddx.model.vo.file;

import lombok.Data;

/**
 * 文件对象
 *
 * @author 范劲松
 */
@Data
public class File {
    /**
     * 文件名
     */
    private String name;

    /**
     * 访问地址
     */
    private String url;
}
