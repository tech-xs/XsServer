package tech.xs.system.domain.bo.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class MailTestBo {

    /**
     * 收件人
     */
    @Email
    @NotBlank
    private String to;

}
