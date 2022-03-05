package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.constant.BooleanConstant;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.framework.enums.HttpMethodEnum;

import java.util.Objects;

/**
 * table name: sys_uri_resource
 * table describe: uri资源
 *
 * @author 沈家文
 * @date 2021/03/02 11:32
 */
@Getter
@Setter
@ToString
@TableName("sys_api")
public class SysApi extends BaseEntity {

    /**
     * 组ID
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 接口路径
     */
    @TableField("uri")
    private String uri;

    /**
     * 名称
     */
    @TableField("api_name")
    private String name;

    /**
     * 请求方式
     */
    @TableField(value = "method")
    private HttpMethodEnum method;

    /**
     * 是否存在
     */
    @TableField("api_exist")
    private BooleanEnum exist;

    /**
     * 备注
     */
    @TableField("api_remark")
    private String remark;

    public SysApi() {

    }

    public SysApi(HttpMethodEnum method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public SysApi(String method, String uri) {
        this.method = HttpMethodEnum.getByCode(method);
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysApi)) {
            return false;
        }
        SysApi eqObj = (SysApi) o;
        if (this.getId() != null && eqObj.getId() != null && this.getId().equals(eqObj.getId())) {
            return true;
        }
        if (this.getUri() != null && this.getMethod() != null && eqObj.getUri() != null && eqObj.getMethod() != null && this.getUri().equals(eqObj.getUri()) && this.getMethod().equals(eqObj.getMethod())) {
            return true;
        }
        if (this.getUri() != null && eqObj.getUri() != null && this.getMethod() == null && eqObj.getMethod() == null && this.getUri().equals(eqObj.getUri())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uri, method, remark);
    }
}
