package tech.xs.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.enums.BooleanEnum;

/**
 * 客户端表
 *
 * @author imsjw
 * Create Time: 2020/7/30
 */
@Getter
@Setter
@ToString
@TableName("auth_client_type")
public class AuthClientType extends BaseEntity {

    /**
     * 名称
     */
    @TableField("client_name")
    private String name;

    /**
     * access token 有效时长
     */
    @TableField("access_token_validity")
    private Long accessTokenValidity;

    /**
     * 自动刷新时间间隔
     */
    @TableField("access_token_refresh_interval")
    private Long accessTokenRefreshInterval;


    /**
     * refresh token 有效时长
     */
    @TableField("refresh_token_validity")
    private Long refreshTokenValidity;

    /**
     * 状态 1:正常 2:禁用
     */
    @TableField("client_status")
    private BooleanEnum status;

    /**
     * 描述
     */
    @TableField("client_remark")
    private String remark;
}
