package tech.xs.doc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

/**
 * 常量值文档
 *
 * @author 沈家文
 * @date 2021-08-17 16:08
 */
@Getter
@Setter
@ToString
@TableName("doc_const_value")
public class DocConstValue extends BaseEntity {

    /**
     * 常量ID
     */
    @TableField("const_id")
    private Long constId;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 常量值
     */
    @TableField("constant_value")
    private String constantValue;

}
