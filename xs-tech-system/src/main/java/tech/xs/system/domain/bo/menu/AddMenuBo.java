package tech.xs.system.domain.bo.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AddMenuBo {

    @NotBlank
    @Length(max = SysParamCheckConstant.SysMenu.NAME_MAX_LENGTH)
    private String name;
    @NotBlank
    @Length(max = SysParamCheckConstant.SysMenu.CODE_MAX_LENGTH)
    private String code;
    @NotNull
    @Min(SysParamCheckConstant.SysMenu.SORT_MIN)
    @Max(SysParamCheckConstant.SysMenu.SORT_MAX)
    private Integer sort;
    private Long fatherId;
    @Length(max = SysParamCheckConstant.SysMenu.ICON_MAX_LENGTH)
    private String icon;
    private Long pageId;

}
