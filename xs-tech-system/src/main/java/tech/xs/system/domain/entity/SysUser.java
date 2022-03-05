package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.xs.framework.constant.SexConstant;
import tech.xs.framework.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.framework.enums.SexEnum;

import java.util.Date;
import java.util.List;

/**
 * 用户实体类 sys_user
 *
 * @author 沈家文
 * @date 2020/7/27
 */
@Getter
@Setter
@ToString
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 用户性别
     */
    @TableField("sex")
    private SexEnum sex;

    /**
     * 账户状态
     */
    @TableField("account_status")
    private BooleanEnum accountStatus;

    /**
     * 公司ID 1 为公司最高权限管理员所属公司
     */
    @TableField(value = "company_id")
    private Long companyId;

    /**
     * 用户头像URL
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 身份证号
     */
    @TableField(value = "id_number")
    private String idNumber;

    /**
     * 出生日期
     */
    @TableField(value = "birth_date")
    private Date birthDate;

    /**
     * 用户角色列表
     */
    @TableField(exist = false)
    private List<SysRole> roleList;


    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    private List<Long> roleIdList;

    /**
     * 用户所属公司
     */
    @TableField(exist = false)
    private SysCompany company;

}

