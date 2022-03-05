package tech.xs.system.domain.bo.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@ToString
public class DeletePageDetailsBo {

    @NotEmpty
    private List<Long> idList;

}
