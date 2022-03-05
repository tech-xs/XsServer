package tech.xs.doc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;
import tech.xs.system.domain.entity.SysApi;

import java.util.List;

/**
 * 接口文档
 *
 * @author 沈家文
 * @date 2021/5/25 11:53
 */
@Getter
@Setter
@ToString
@TableName("doc_api")
public class DocApi extends BaseEntity {

    /**
     * 接口ID
     */
    @TableField("api_id")
    private Long apiId;

    /**
     * 接口描述
     */
    @TableField("api_describe")
    private String apiDescribe;

    /**
     * 接口
     */
    @TableField(exist = false)
    private SysApi api;

    /**
     * 请求参数列表
     */
    @TableField(exist = false)
    private List<DocApiParm> reqParmList;

    /**
     * 响应参数列表
     */
    @TableField(exist = false)
    private List<DocApiParm> respParmList;

    /**
     * 请求响应内容
     */
    @TableField(exist = false)
    private List<DocApiBody> bodyList;

    /**
     * 响应错误code列表
     */
    @TableField(exist = false)
    private List<DocApiResponseCode> respCodeList;

}
