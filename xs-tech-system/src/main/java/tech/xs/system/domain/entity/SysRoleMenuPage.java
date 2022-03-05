package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

@Getter
@Setter
@ToString
@TableName("sys_role_menu_page")
public class SysRoleMenuPage extends BaseEntity {

    @TableField("role_id")
    private Long roleId;

    @TableField("menu_id")
    private Long menuId;
    /**
     * 子页面ID
     */
    @TableField("page_id")
    private Long pageId;

}
