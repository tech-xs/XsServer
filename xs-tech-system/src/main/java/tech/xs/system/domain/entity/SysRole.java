package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.xs.framework.enums.BooleanEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

import java.util.List;
import java.util.Objects;

/**
 * 角色实体类
 *
 * @author 沈家文
 * @date 2020/10/15 21:40
 */
@Getter
@Setter
@ToString
@TableName("sys_role")
public class SysRole extends BaseEntity {

    /**
     * 角色Code 唯一约束
     */
    @TableField("role_code")
    private String code;

    /**
     * 角色名
     */
    @TableField("role_name")
    private String name;

    /**
     * 角色状态
     */
    @TableField("role_status")
    private BooleanEnum status;

    /**
     * 备注
     */
    @TableField("role_remark")
    private String remark;

    /**
     * 菜单列表
     */
    @TableField(exist = false)
    private List<SysMenu> menuList;

    /**
     * 菜单Id列表
     */
    @TableField(exist = false)
    private List<Long> menuIdList;

    /**
     * 页面列表
     */
    @TableField(exist = false)
    private List<SysPage> pageList;

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }
        if (!(o instanceof SysRole)) {
            return false;
        }
        SysRole sysRole = (SysRole) o;
        if (this.getId() != null && this.getId().equals(sysRole.getId())) {
            return true;
        }
        if (this.getCode() != null && this.getCode().equals(sysRole.getCode())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, remark);
    }
}
