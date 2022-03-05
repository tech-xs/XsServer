package tech.xs.doc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

/**
 * 接口参数常量关联
 *
 * @author 沈家文
 * @date 2021-24-17 17:24
 */
@Getter
@Setter
@ToString
@TableName("doc_api_parm_const")
public class DocApiParmConst extends BaseEntity {

    @TableField("parm_id")
    private Long parmId;

    @TableField("const_id")
    private Long constId;

}
