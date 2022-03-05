package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.xs.framework.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 页面接口
 *
 * @author 沈家文
 * @date 2021/5/19 9:48
 */
@Getter
@Setter
@ToString
@TableName("sys_page_api")
public class SysPageApi extends BaseEntity {

    @TableField("page_id")
    private Long pageId;

    @TableField("api_id")
    private Long apiId;

    @TableField(value = "permission_id")
    private Long permissionId;

    @TableField("api_remark")
    private String remark;

    @TableField(exist = false)
    private SysApi api;

    @TableField(exist = false)
    private SysPagePermission permission;

}
