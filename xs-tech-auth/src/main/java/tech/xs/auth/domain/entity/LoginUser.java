package tech.xs.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import tech.xs.framework.enums.BooleanEnum;

/**
 * @author imsjw
 * Create Time: 2020/8/5
 */
@Getter
@Setter
@TableName("sys_user")
public class LoginUser {

    /**
     * 用户ID,用户的唯一标识符
     */
    @TableId("id")
    private Long userId;

    /**
     * 登陆用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 登陆手机号
     */
    @TableField("phone")
    private String phone;


    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 用户密码
     */
    @JsonIgnore
    @TableField("login_password")
    private String loginPassword;

    /**
     * 用户状态
     */
    @TableField("account_status")
    private BooleanEnum accountStatus;

    /**
     * 公司ID 1 总公司  其他值为其他公司
     */
    @TableField("company_id")
    private Long companyId;

}
