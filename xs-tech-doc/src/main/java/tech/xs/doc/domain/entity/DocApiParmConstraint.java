package tech.xs.doc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

/**
 * 参数约束
 *
 * @author 沈家文
 * @date 2021-29-16 18:29
 */
@Getter
@Setter
@ToString
@TableName("doc_api_parm_constraint")
public class DocApiParmConstraint extends BaseEntity {

    /**
     * '参数ID'
     */
    @TableField("parm_id")
    private Long parmId;

    /**
     * '约束类型'
     */
    @TableField("constraint_type")
    private Integer constraintType;

    /**
     * '约束值'
     */
    @TableField("constraint_value")
    private Long constraintValue;

    /**
     * 描述
     */
    @TableField("constraint_describe")
    private String constraintDescribe;

    /**
     * 排序
     */
    @TableField("constraint_sort")
    private Integer constraintSort;

}
