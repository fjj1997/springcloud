package com.cddx.system.service;

import com.alibaba.fastjson.JSONObject;
import com.cddx.base.LoginUser;
import com.cddx.base.PageGrid;
import com.cddx.domain.dto.AddUserDto;
import com.cddx.domain.dto.EditUserDto;
import com.cddx.domain.dto.ListUserDto;
import com.cddx.domain.entity.SysUser;
import com.cddx.domain.entity.SysUserDept;
import com.cddx.domain.entity.SysUserRole;
import com.cddx.domain.vo.ChangePasswordVo;
import com.cddx.domain.vo.SmsResult;
import com.cddx.domain.vo.UserVo;
import com.cddx.enums.Status;
import com.cddx.exception.ServiceException;
import com.cddx.exception.http.ServerErrorException;
import com.cddx.mapper.*;
import com.cddx.utils.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 用户service
 *
 * @author 范劲松
 */
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysDeptMapper deptMapper;

    @Resource
    private SysUserDeptMapper userDeptMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private PermissionService permissionService;

    private StringRedisTemplate stringRedisTemplate;

    private static String smsUrl;
    private static String appId;
    private static String appKey;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Value("${smsUrl}")
    public void setSmsUrl(String smsUrl) {
        SysUserService.smsUrl = smsUrl;
    }

    @Value("${appId}")
    public void setAppId(String appId) {
        SysUserService.appId = appId;
    }

    @Value("${appKey}")
    public void setAppKey(String appKey) {
        SysUserService.appKey = appKey;
    }

    /**
     * 查询用户列表
     *
     * @param dto 传输类
     * @return 用户列表
     */
    public MyPage<UserVo> list(PageGrid<ListUserDto> dto) {
        Integer pageSize = dto.getPageSize();
        Integer pageNo = dto.getPageNo();
        Page<UserVo> page = PageHelper.startPage(pageNo, pageSize);
        sysUserMapper.queryUserList(dto.getParameter());
        MyPage<UserVo> myPage = new MyPage<>(page);
        myPage.setList(page);
        return myPage;
    }

    /**
     * 发送修改密码验证码
     *
     * @param userId 用户id
     * @return 结果
     */
    public UserVo getUserInfoById(Long userId) {
        UserVo userVo = sysUserMapper.queryUserByUserId(userId);
        if (com.cddx.utils.StringUtils.isNull(userVo)) {
            throw new ServerErrorException(20002);
        }
        if (Status.DISABLED.equals(userVo.getStatus())) {
            throw new ServerErrorException(20016);
        }
        return userVo;
    }

    /**
     * 通过手机号查询用户信息
     *
     * @param phone 手机号
     * @return 用户信息
     */
    public SysUser queryLoginUserByPhone(String phone) {
        SysUser sysUser = sysUserMapper.selectUserByPhone(phone);
        if (null == sysUser) {
            throw new ServerErrorException(20002);
        }
        if (Status.DISABLED.equals(sysUser.getStatus())) {
            throw new ServerErrorException(20016);
        }
        return sysUser;
    }

    /**
     * 添加用户
     *
     * @param dto      添加实体
     * @param createId 创建者
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUser(AddUserDto dto, Long createId) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyPropertiesIgnoreNull(dto, sysUser);
        // 检测用户是否存在
        if (sysUserMapper.selectExistByUser(sysUser).size() > 0) {
            throw new ServerErrorException(20001);
        }
        sysUser.setCreateId(createId);
        sysUser.setCreateTime(System.currentTimeMillis() / 1000);
        sysUser.setDelFlag(false);
        sysUserMapper.insert(sysUser);
        // 创建部门关联
        insertDept(sysUser.getId(), dto.getDeptId(), dto.getPosition());
        // 创建角色关联
        insertRole(sysUser.getId(), dto.getRoleId());
    }

    /**
     * 编辑用户信息
     *
     * @param dto          编辑实体
     * @param updateUserId 创建者
     */
    @Transactional(rollbackFor = Exception.class)
    public void editUser(EditUserDto dto, Long updateUserId) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyPropertiesIgnoreNull(dto, sysUser);
        // 检测输入信息是否存在
        List<SysUser> existUsers = sysUserMapper.selectExistByUser(sysUser);
        // 移除它自己
        existUsers.removeIf(user -> user.getId().equals(dto.getId()));
        // 检测用户是否存在
        if (sysUserMapper.selectExistByUser(sysUser).size() > 0) {
            throw new RuntimeException("20001");
        }
        sysUser.setLastUpdateId(updateUserId);
        sysUser.setLastUpdateTime(System.currentTimeMillis() / 1000);
        sysUserMapper.updateById(sysUser);
        // 删除部门关联
        userDeptMapper.deleteDeptByUserId(dto.getId());
        // 删除角色关联
        userRoleMapper.deleteRoleByUserId(dto.getId());
        // 创建部门关联
        insertDept(dto.getId(), dto.getDeptId(), dto.getPosition());
        // 创建角色关联
        insertRole(dto.getId(), dto.getRoleId());
    }

    /**
     * 关联部门及职位信息
     *
     * @param userId   用户
     * @param deptId   部门
     * @param position 职位
     */
    public void insertDept(Long userId, Long deptId, String position) {
        // 对加入的部门存在性校验
        boolean exist = deptMapper.existDeptById(deptId);
        if (!exist) {
            throw new ServerErrorException(20030);
        }
        SysUserDept sysUserDept = new SysUserDept();
        sysUserDept.setUserId(userId);
        sysUserDept.setDeptId(deptId);
        sysUserDept.setPosition(position);
        userDeptMapper.insert(sysUserDept);
    }

    /**
     * 关联角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    public void insertRole(Long userId, Long roleId) {
        // 对加入的角色存在性校验
        boolean exist = roleMapper.existRoleById(roleId);
        if (!exist) {
            throw new ServerErrorException(20050);
        }
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        userRoleMapper.insert(sysUserRole);
    }

    /**
     * 获取登录用户信息
     *
     * @param loginUser 登录用户
     * @return 结果
     */
    public JSONObject getInfo(LoginUser loginUser) {
        Long userId = loginUser.getSysUser().getId();
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(userId);
        JSONObject ajax = new JSONObject();
        ajax.put("user", sysUserMapper.queryUserByUserId(userId));
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 修改用户密码
     *
     * @param changePasswordVo
     */
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(ChangePasswordVo changePasswordVo,Long updateUserId) throws Exception {
        //密码验证
        String pwd = RsaUtils.decrypt(new String(Base64Encoder.decode(changePasswordVo.getPassword().getBytes()),
                StandardCharsets.UTF_8));
        String oldPwd = RsaUtils.decrypt(new String(Base64Encoder.decode(changePasswordVo.getRePassword().getBytes()),
                StandardCharsets.UTF_8));
        if (!pwd.equals(oldPwd)) {
            throw new ServerErrorException(20553);
        }
        String phone = RsaUtils.decrypt(new String(Base64Encoder.decode(changePasswordVo.getTelephone().getBytes()),
                StandardCharsets.UTF_8));
        //查询手机号是否存在
        SysUser userByPhone = this.sysUserMapper.selectUserByPhone(phone);
        if (userByPhone == null) {
            throw new ServerErrorException(20002);
        }
        //验证验证码
        String loginCodeKey = "updatePasswdCode_" + phone;
        String updatePasswdCode = this.stringRedisTemplate.opsForValue().get(loginCodeKey);
        if (!changePasswordVo.getVerificationCode().equals(updatePasswdCode)) {
            throw new ServerErrorException(20012);
        }
        //弱密码检查/加密MD5
        String password;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (PasswordUtils.evalPassword(pwd)){
            password = bCryptPasswordEncoder.encode(DigestUtils.md5Hex(pwd));
        }else{
            throw new ServerErrorException(20054);
        }
        //修改密码
        SysUser sysUser = new SysUser();
        sysUser.setId(userByPhone.getId());
        BeanUtils.copyPropertiesIgnoreNull(changePasswordVo, sysUser);
        sysUser.setPassword(password);
        sysUser.setLastUpdateId(updateUserId);
        sysUser.setLastUpdateTime(System.currentTimeMillis());
        MessageUtils.commonJudgeMsg(this.sysUserMapper.changePassword(sysUser));
    }

    /**
     * 发送修改密码验证码
     *
     * @param phone
     */
    public void sendUpdatePasswdSms(String phone) {
        String loginCodeCount = this.stringRedisTemplate.opsForValue().get("updatePasswdCodeCount_" + phone);
        String code = NumberUtils.getSix();
        if (StringUtils.isBlank(loginCodeCount)) {
            SmsResult smsResult = SmsUtils
                    .sendSms(phone, MessageFormat.format(MessageUtils.SMSUPDATEPASSWD, code), smsUrl, appId, appKey);
            if (smsResult == null) {
                throw new ServerErrorException(20013);
            }
            if ("success".equals(smsResult.getMsg())) {
                this.stringRedisTemplate.opsForValue()
                        .set("updatePasswdCode_" + phone, code, NumberUtils.NB_FIVE, TimeUnit.MINUTES);
                this.stringRedisTemplate.opsForValue()
                        .set("updatePasswdCodeCount_" + phone, "1", NumberUtils.NB_TEN, TimeUnit.MINUTES);
            } else {
                throw new ServiceException(smsResult.getMsg());
            }
        } else {
            int count = Integer.parseInt(loginCodeCount);
            if (count == NumberUtils.NB_THREE) {
                throw new ServerErrorException(20014);
            } else {
                SmsResult smsResult = SmsUtils
                        .sendSms(phone, MessageFormat.format(MessageUtils.SMSUPDATEPASSWD, code), smsUrl, appId, appKey);
                if (smsResult == null) {
                    throw new ServerErrorException(20013);
                }
                if ("success".equals(smsResult.getMsg())) {
                    this.stringRedisTemplate.opsForValue()
                            .set("updatePasswdCode_" + phone, code, NumberUtils.NB_FIVE, TimeUnit.MINUTES);
                    this.stringRedisTemplate.opsForValue()
                            .set("updatePasswdCodeCount_" + phone, "1", NumberUtils.NB_TEN, TimeUnit.MINUTES);
                } else {
                    throw new ServiceException(smsResult.getMsg());
                }
                this.stringRedisTemplate.opsForValue()
                        .set("updatePasswdCode_" + phone, code, NumberUtils.NB_FIVE, TimeUnit.MINUTES);
                count++;
                this.stringRedisTemplate.opsForValue()
                        .set("updatePasswdCodeCount_" + phone, count + "", NumberUtils.NB_TEN, TimeUnit.MINUTES);
            }
        }
    }
}
