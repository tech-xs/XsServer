package tech.xs.system.domain.bo.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class AddPageBo {

    @NotBlank
    @Length(max = SysParamCheckConstant.SysPage.URL_MAX_LENGTH)
    private String uri;

    @NotBlank
    @Length(max = SysParamCheckConstant.SysPage.NAME_MAX_LENGTH)
    private String name;

    @Length(max = SysParamCheckConstant.SysPage.REMARK_MAX_LENGTH)
    private String remark;

    private Long fatherId;
}
