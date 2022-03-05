package tech.xs.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

import java.util.Objects;

/**
 * 接口列表
 *
 * @author imsjw
 * Create Time: 2020/8/5
 */
@Getter
@Setter
@ToString
public class BaseAuthUrlResource extends BaseEntity {

    /**
     * 接口路径
     */
    @TableField("`url`")
    private String url;

    /**
     * 请求方式
     */
    @TableField("`method`")
    private String method;

    /**
     * 描述
     */
    @TableField("`remark`")
    private String remark;

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof BaseAuthUrlResource) {
            Long id = ((BaseAuthUrlResource) o).getId();
            if (this.getId() == null || id == null) {
                return false;
            }
            if (this.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

