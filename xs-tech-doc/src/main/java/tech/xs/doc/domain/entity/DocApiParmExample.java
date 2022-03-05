package tech.xs.doc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.enums.TextFormatEnum;

/**
 * 接口参数示例
 *
 * @author 沈家文
 * @date 2021/5/25 17:27
 */
@Getter
@Setter
@ToString
@TableName("doc_api_parm_example")
public class DocApiParmExample extends BaseEntity {

    /**
     * 参数ID
     */
    @TableField("parm_id")
    private Long parmId;

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
     * 排序
     */
    @TableField("example_sort")
    private Integer sort;

}
