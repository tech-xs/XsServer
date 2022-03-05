package tech.xs.generator.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

/**
 * 数据库中的列信息
 *
 * @author imsjw
 * Create Time: 2020/7/31
 */
@Getter
@Setter
@ToString
@TableName("generator_column")
public class GeneratorColumn extends BaseEntity {

    /**
     * 所属表ID
     */
    @TableField("table_id")
    private Long tableId;

    /**
     * 列名称
     */
    @TableField("column_name")
    private String name;

    /**
     * 列字段类型
     */
    @TableField("field_type")
    private String fieldType;

    /**
     * 字段长度
     */
    @TableField("field_length")
    private Long fieldLength;

    /**
     * 字段备注
     */
    @TableField("field_remark")
    private String fieldRemark;

}
