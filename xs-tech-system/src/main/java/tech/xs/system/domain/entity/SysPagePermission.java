package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.xs.framework.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 页面权限实体类
 *
 * @author 沈家文
 * @date 2021/7/8 17:10
 */
@Getter
@Setter
@ToString
@TableName("sys_page_permission")
public class SysPagePermission extends BaseEntity {

    /**
     * 页面ID
     */
    @TableField("page_id")
    private Long pageId;

    /**
     * 权限编码
     */
    @TableField("permission_code")
    private String permissionCode;

    /**
     * 权限名称
     */
    @TableField("permission_name")
    private String permissionName;

    /**
     * 权限备注
     */
    @TableField("permission_remark")
    private String remark;

}
