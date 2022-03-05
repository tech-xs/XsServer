package tech.xs.system.domain.bo.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.framework.base.ListPageBo;
import tech.xs.system.constant.SysParamCheckConstant;
import tech.xs.system.domain.entity.SysPage;


@Getter
@Setter
@ToString
public class PageListPageBo extends ListPageBo<SysPage> {

    @Length(max = SysParamCheckConstant.SysPage.NAME_MAX_LENGTH)
    private String likeName;
    @Length(max = SysParamCheckConstant.SysPage.URL_MAX_LENGTH)
    private String likeUri;

}
