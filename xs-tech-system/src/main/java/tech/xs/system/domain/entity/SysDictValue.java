package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.xs.system.constant.SysParamCheckConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

import java.util.List;


/**
 * 字典值
 *
 * @author 沈家文
 * @date 2020/10/21
 */
@Getter
@ToString
@TableName("sys_dict_value")
public class SysDictValue extends BaseEntity {

    /**
     * 字典ID
     * 非空约束
     * 业务逻辑关联 {@link SysDict#id}
     */
    @Setter
    @TableField("dict_id")
    private Long dictId;

    /**
     * 值
     * 非空约束
     * 长度约束
     *
     * @see SysParamCheckConstant.SysDictValue#VALUE_MAX_LENGTH
     */
    @TableField("dict_value")
    private String value;

    /**
     * 名称
     * 非空约束
     * 长度约束
     *
     * @see SysParamCheckConstant.SysDictValue#NAME_MAX_LENGTH
     */
    @Setter
    @TableField("dict_value_name")
    private String name;

    /**
     * 排序,从小到大排
     * 非空约束
     */
    @Setter
    @TableField("dict_value_sort")
    private Integer sort;

    /**
     * 父节点ID
     */
    @Setter
    @TableField(value = "father_id")
    private Long fatherId;

    /**
     * 备注
     * 长度限制
     *
     * @see SysParamCheckConstant.SysDictValue#REMARK_MAX_LENGTH
     */
    @Setter
    @TableField("dict_value_remark")
    private String remark;

    /**
     * 子节点列表
     */
    @Setter
    @TableField(exist = false)
    private List<SysDictValue> childList;

    public void setValue(Integer value) {
        this.value = value.toString();
    }

    public void setValue(String value) {
        this.value = value;
    }

}
