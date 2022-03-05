package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

/**
 * 组织机构实体类
 * <p>
 * table name: sys_organization
 * table describe: 组织机构表
 *
 * @author 沈家文
 * @date 2021/01/26
 */
@Getter
@Setter
@ToString
@TableName("sys_organization")
public class SysOrganization extends BaseEntity {

    /**
     * 名称
     */
    @TableField("organization_name")
    private String organizationName;

    /**
     * 父节点
     */
    @TableField("father_id")
    private Long fatherId;

}
