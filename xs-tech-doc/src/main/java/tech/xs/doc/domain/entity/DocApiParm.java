package tech.xs.doc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.doc.enums.HttpSourceType;
import tech.xs.doc.enums.ParameterType;
import tech.xs.framework.base.BaseEntity;

import java.util.List;

/**
 * 接口参数
 *
 * @author 沈家文
 * @date 2021/5/25 17:16
 */
@Getter
@Setter
@ToString
@TableName("doc_api_parm")
public class DocApiParm extends BaseEntity {

    /**
     * 接口文档ID
     */
    @TableField("api_id")
    private Long apiId;

    /**
     * 参数名
     */
    @TableField("parm_name")
    private String parmName;

    /**
     * 参数标题
     */
    @TableField("parm_title")
    private String parmTitle;

    /**
     * 参数类型
     */
    @TableField("data_type")
    private ParameterType dataType;

    /**
     * 数据源类型
     */
    private HttpSourceType sourceType;

    /**
     * 参数描述
     */
    @TableField("parm_describe")
    private String parmDescribe;

    /**
     * 排序
     */
    @TableField("parm_sort")
    private Integer parmSort;

    /**
     * 参数示例
     */
    @TableField(exist = false)
    private List<DocApiParmExample> exampleList;

    /**
     * 参数约束
     */
    @TableField(exist = false)
    private List<DocApiParmConstraint> constraintList;

    /**
     * 引用常量列表
     */
    @TableField(exist = false)
    private List<DocConst> constList;

}
