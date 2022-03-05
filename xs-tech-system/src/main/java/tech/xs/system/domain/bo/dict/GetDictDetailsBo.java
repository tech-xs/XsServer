package tech.xs.system.domain.bo.dict;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class GetDictDetailsBo {

    @NotBlank
    @Length(max = SysParamCheckConstant.SysDict.CODE_MAX_LENGTH)
    private String code;

}
