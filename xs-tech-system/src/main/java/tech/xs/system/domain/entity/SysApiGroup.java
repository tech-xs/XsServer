package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

import java.util.Objects;

/**
 * Api接口分组
 *
 * @author 沈家文
 * @date 2021-13-23 18:13
 */
@Getter
@Setter
@ToString
@TableName("sys_api_group")
public class SysApiGroup extends BaseEntity {

    /**
     * 组名
     */
    @TableField("group_name")
    private String name;

    /**
     * 类名
     * 唯一约束
     */
    @TableField("class_name")
    private String className;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysApiGroup that = (SysApiGroup) o;
        if (that.getId() != null && this.getId() != null && this.getId().equals(that.getId())) {
            return true;
        }
        if (this.getClassName() != null && that.getClassName() != null && this.getClassName().equals(that.getClassName())) {
            return true;
        }
        return false;
    }


}
