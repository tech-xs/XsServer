package tech.xs.system.domain.bo.page;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ModifyPageBo {

    @NotNull
    private Long id;

    @Length(min = SysParamCheckConstant.SysPage.URL_MIN_LENGTH, max = SysParamCheckConstant.SysPage.URL_MAX_LENGTH)
    private String uri;

    @Length(min = SysParamCheckConstant.SysPage.NAME_MIN_LENGTH, max = SysParamCheckConstant.SysPage.NAME_MAX_LENGTH)
    private String name;

    @Length(max = SysParamCheckConstant.SysPage.REMARK_MAX_LENGTH)
    private String remark;

    private Long fatherId;

}
