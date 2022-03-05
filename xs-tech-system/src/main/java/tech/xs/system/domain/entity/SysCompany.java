package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.xs.framework.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 公司实体类
 *
 * @author 沈家文
 * @date 2021/6/10 16:37
 */
@Getter
@Setter
@ToString
@TableName("sys_company")
public class SysCompany extends BaseEntity {

    /**
     * 公司简称
     */
    @TableField("company_short_name")
    private String shortName;

    /**
     * 公司全称
     */
    @TableField("company_full_name")
    private String fullName;

    /**
     * 备注
     */
    @TableField("company_remark")
    private String remark;

}
