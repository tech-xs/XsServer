package tech.xs.auth.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 页面和资源关联实体类
 *
 * @author imsjw
 * Create Time: 2020/9/2
 */
@Getter
@Setter
@ToString
@TableName("`auth_page_api`")
public class AuthPageApi extends BaseAuthUrlResource {

    /**
     * 页面ID
     */
    @TableField("`page_id`")
    private Long pageId;

}
