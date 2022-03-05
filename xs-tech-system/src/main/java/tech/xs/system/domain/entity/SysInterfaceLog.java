package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.xs.framework.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * table name: sys_third_party_interface_log
 * table describe: 接口调用日志表
 *
 * @author 沈家文
 * @date 2021/04/22 14:30
 */
@Getter
@Setter
@ToString
@TableName("sys_interface_log")
public class SysInterfaceLog extends BaseEntity {

    /**
     * 组织机构id
     */
    @TableField(value = "company_id", fill = FieldFill.INSERT)
    private Long companyId;

    /**
     * 业务key
     */
    @TableField("business_key")
    private String businessKey;

    /**
     * 请求地址
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 请求内容
     */
    @TableField("request_content")
    private String requestContent;

    /**
     * 响应码
     */
    @TableField("response_code")
    private String responseCode;

    /**
     * 响应内容
     */
    @TableField("response_content")
    private String responseContent;


}
