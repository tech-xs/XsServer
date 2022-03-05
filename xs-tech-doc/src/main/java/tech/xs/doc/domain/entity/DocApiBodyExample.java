package tech.xs.doc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.doc.enums.HttpSourceType;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.enums.TextFormatEnum;

/**
 * 接口文档示例实体类
 *
 * @author 沈家文
 * @date 2021/7/8 17:19
 */
@Getter
@Setter
@ToString
@TableName("doc_api_body_example")
public class DocApiBodyExample extends BaseEntity {

    /**
     * Body ID
     */
    @TableField("body_id")
    private Long bodyId;

    /**
     * 格式
     *
     * @see TextFormatEnum
     */
    @TableField("value_format")
    private TextFormatEnum valueFormat;

    /**
     * 示例值
     */
    @TableField("example_value")
    private String exampleValue;


    /**
     * 请求体类型
     *
     * @see HttpSourceType
     */
    @TableField("source_type")
    private Integer sourceType;

    /**
     * 排序
     */
    @TableField("example_sort")
    private Integer sort;

}
