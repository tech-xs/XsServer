package tech.xs.doc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

import java.util.List;

/**
 * 常量文档
 *
 * @author 沈家文
 * @date 2021-52-17 15:52
 */
@Getter
@Setter
@ToString
@TableName("doc_const")
public class DocConst extends BaseEntity {

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 类名
     */
    @TableField("class_name")
    private String className;

    /**
     * 常量值列表
     */
    @TableField(exist = false)
    private List<DocConstValue> valueList;

}
