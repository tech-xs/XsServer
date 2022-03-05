package tech.xs.doc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

/**
 * 接口错误响应描述
 *
 * @author 沈家文
 * @date 2021/7/8 17:17
 */
@Getter
@Setter
@ToString
@TableName("doc_api_response_code")
public class DocApiResponseCode extends BaseEntity {

    /**
     * 接口ID
     */
    @TableField("api_id")
    private Long apiId;

    /**
     * code值 为null是代表为全局通用返回值
     */
    @TableField("code")
    private Integer code;

    /**
     * 描述
     */
    @TableField("code_describe")
    private String codeDescribe;

}
