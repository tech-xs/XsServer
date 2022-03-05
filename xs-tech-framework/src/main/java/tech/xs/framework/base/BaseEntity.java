package tech.xs.framework.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 *
 * @author 沈家文
 * @date 2020/7/27
 */
@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {

    /**
     * 的唯一标识符
     */
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

    /**
     * 创建者
     */
    @JsonIgnore
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    protected String createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * 更新者
     */
    @JsonIgnore
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    protected String updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;

    protected Object clone(BaseEntity baseEntity) throws CloneNotSupportedException {
        if (baseEntity == null) {
            return null;
        }
        baseEntity.setId(this.getId());
        baseEntity.setCreateTime(this.getCreateTime());
        baseEntity.setCreateUser(this.getCreateUser());
        baseEntity.setUpdateTime(this.getUpdateTime());
        baseEntity.setUpdateUser(this.getUpdateUser());
        return baseEntity;
    }
}
