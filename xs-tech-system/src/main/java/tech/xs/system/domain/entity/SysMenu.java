package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

import java.util.List;

/**
 * 菜单
 *
 * @author 沈家文
 * @date 2020/10/22
 */
@Getter
@Setter
@ToString
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    @TableField("menu_name")
    private String name;

    @TableField("menu_code")
    private String code;

    /**
     * 数值越小越靠前
     */
    @TableField("menu_sort")
    private Integer sort;

    /**
     * 菜单路径
     */
    @TableField("page_id")
    private Long pageId;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 父菜单
     */
    @TableField(value = "father_id")
    private Long fatherId;

    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<SysMenu> childList;

    /**
     * 绑定的页面
     */
    @TableField(exist = false)
    private SysPage page;

}
