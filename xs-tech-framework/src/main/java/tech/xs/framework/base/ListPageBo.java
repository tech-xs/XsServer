package tech.xs.framework.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.constant.ParamCheckConstant;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页查询Bo
 */
@Getter
@Setter
@ToString
public class ListPageBo<T> {

    /**
     * 分页查询下标
     * 最小值为0
     */
    @NotNull
    @Min(ParamCheckConstant.PAGE_MIN_INDEX)
    private Long pageIndex;

    /**
     * 每页数量
     */
    @NotNull
    @Min(ParamCheckConstant.PAGE_MIN_SIZE)
    @Max(ParamCheckConstant.PAGE_MAX_SIZE)
    private Long pageSize;

    public Page<T> getPage() {
        return new Page<T>(pageIndex, pageSize);
    }

}
