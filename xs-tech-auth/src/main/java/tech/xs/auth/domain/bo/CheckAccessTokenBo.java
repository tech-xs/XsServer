package tech.xs.auth.domain.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CheckAccessTokenBo {

    @NotBlank
    private String accessToken;

}
