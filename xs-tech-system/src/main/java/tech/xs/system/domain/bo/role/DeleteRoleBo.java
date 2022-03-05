package tech.xs.system.domain.bo.role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@ToString
public class DeleteRoleBo {

    @NotEmpty
    private List<Long> idList;

}
