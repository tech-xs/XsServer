package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

/**
 * 系统配置实体类
 *
 * @author 沈家文
 * @date 2021/2/21 21:42
 */
@Getter
@Setter
@ToString
@TableName("sys_config")
public class SysConfig extends BaseEntity {

    /**
     * 所属模块
     */
    @TableField("config_module")
    private String configModule;

    /**
     * 配置名称
     */
    @TableField("config_name")
    private String configName;

    /**
     * 配置别名
     */
    @TableField("config_alias")
    private String configAlias;

    /**
     * 配置类型
     */
    @TableField("config_type")
    private String configType;

    /**
     * key
     */
    @TableField("config_key")
    private String configKey;

    /**
     * 子key
     */
    @TableField("config_sub_key")
    private String configSubKey;

    /**
     * value
     */
    @TableField("config_value")
    private String configValue;

    /**
     * 子value
     */
    @TableField("config_sub_value")
    private String configSubValue;

    /**
     * 公司ID 1 为公司最高权限管理员所属公司
     */
    @TableField(value = "company_id")
    private Long companyId;

}

