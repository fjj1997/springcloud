package com.cddx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cddx.domain.dto.ListUserDto;
import com.cddx.domain.entity.SysUser;
import com.cddx.domain.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据
 *
 * @author 范劲松
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 查询用户通过手机号
     *
     * @param phone 手机号
     * @return 结果
     */
    SysUser selectUserByPhone(@Param("phone") String phone);

    /**
     * 查询用户列表
     *
     * @param dto 筛选条件参数
     * @return 结果
     */
    List<UserVo> queryUserList(ListUserDto dto);

    /**
     * 查询用户信息通过用户id
     *
     * @param userId 用户id
     * @return 用户展示类
     */
    UserVo queryUserByUserId(Long userId);

    /**
     * 查询用户列表通过用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    List<SysUser> selectExistByUser(SysUser user);

    /**
     * 修改用户密码
     * @param user
     * @return
     */
    Integer changePassword(SysUser user);
}
