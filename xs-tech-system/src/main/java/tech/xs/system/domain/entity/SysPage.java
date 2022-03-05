package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

import java.util.List;

/**
 * 页面
 *
 * @author 沈家文
 * @date 2020/9/2
 */
@Getter
@Setter
@ToString
@TableName("sys_page")
public class SysPage extends BaseEntity {

    /**
     * 页面名称
     */
    @TableField("page_name")
    private String name;

    /**
     * URL
     */
    @TableField("page_uri")
    private String uri;

    /**
     * 父页面ID
     */
    @TableField("father_id")
    private Long fatherId;

    /**
     * 备注
     */
    @TableField("page_remark")
    private String remark;

    /**
     * 权限列表
     */
    @TableField(exist = false)
    private List<SysPagePermission> pagePermissionList;

    @TableField(exist = false)
    private List<SysPageApi> pageApiList;

    /**
     * 父节点
     */
    @TableField(exist = false)
    private SysPage father;

    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<SysPage> childList;

}
