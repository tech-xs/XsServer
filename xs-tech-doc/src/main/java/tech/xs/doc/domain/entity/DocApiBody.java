package tech.xs.doc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

import java.util.List;

/**
 * 接口中的请求响应内容
 *
 * @author 沈家文
 * @date 2021/5/26 14:25
 */
@Getter
@Setter
@ToString
@TableName("doc_api_body")
public class DocApiBody extends BaseEntity {

    /**
     * 接口文档ID
     */
    @TableField("api_id")
    private Long apiId;

    /**
     * 标题
     */
    @TableField("body_title")
    private String bodyTitle;

    /**
     * 描述
     */
    @TableField("body_describe")
    private String bodyDescribe;

    /**
     * 排序
     */
    @TableField("body_sort")
    private Integer sort;

    @TableField(exist = false)
    private List<DocApiBodyExample> exampleList;

}
