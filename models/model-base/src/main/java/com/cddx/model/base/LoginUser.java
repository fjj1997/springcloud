package com.cddx.model.base;

import com.cddx.model.entity.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户
 *
 * @author 范劲松
 */
@Data
public class LoginUser implements Serializable {

    private SysUser sysUser;

}
