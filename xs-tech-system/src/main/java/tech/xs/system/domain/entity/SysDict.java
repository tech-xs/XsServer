package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.xs.system.constant.SysParamCheckConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;
import tech.xs.system.enmus.DictStructureTypeEnum;
import tech.xs.system.enmus.DictValueTypeEnum;

import java.util.List;

/**
 * 字典
 *
 * @author 沈家文
 * @date 2020/10/21
 */
@Getter
@Setter
@ToString
@TableName("sys_dict")
public class SysDict extends BaseEntity {

    /**
     * 编号 唯一约束
     * 非空约束
     * 唯一约束
     * 长度约束
     *
     * @see SysParamCheckConstant.SysDict#CODE_MAX_LENGTH
     */
    @TableField("dict_code")
    private String code;

    /**
     * 名称
     * 非空约束
     * 唯一约束
     * 长度约束
     *
     * @see SysParamCheckConstant.SysDict#NAME_MAX_LENGTH
     */
    @TableField("dict_name")
    private String name;

    /**
     * 字典结构类型
     */
    @TableField("structure_type")
    private DictStructureTypeEnum structureType;

    /**
     * 字典值类型
     */
    @TableField("value_type")
    private DictValueTypeEnum valueType;

    /**
     * 备注
     * 长度限制
     *
     * @see SysParamCheckConstant.SysDict#REMARK_MAX_LENGTH
     */
    @TableField("dict_remark")
    private String remark;

    /**
     * 字典值列表
     */
    @TableField(exist = false)
    private List<SysDictValue> valueList;

}
