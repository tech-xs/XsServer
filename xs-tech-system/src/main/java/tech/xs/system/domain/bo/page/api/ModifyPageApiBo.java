package tech.xs.system.domain.bo.page.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ModifyPageApiBo {

    @NotNull
    private Long id;
    @NotNull
    private Long pageId;
    @NotNull
    private Long apiId;
    private Long permissionId;
    @Length(max = SysParamCheckConstant.SysPageApi.REMARK_MAX_LENGTH)
    private String remark;

}
