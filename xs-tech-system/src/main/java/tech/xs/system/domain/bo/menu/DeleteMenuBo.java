package tech.xs.system.domain.bo.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class DeleteMenuBo {

    @NotNull
    private Long id;

}
