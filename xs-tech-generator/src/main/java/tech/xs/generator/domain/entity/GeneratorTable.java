package tech.xs.generator.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

/**
 * 数据库中的表信息
 *
 * @author imsjw
 * Create Time: 2020/7/31
 */
@Getter
@Setter
@ToString
@TableName("generator_table")
public class GeneratorTable extends BaseEntity {

    /**
     * 表名
     */
    @TableField("table_name")
    private String name;

    /**
     * 表备注
     */
    @TableField("table_remark")
    private String remark;


}
