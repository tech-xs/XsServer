package tech.xs.framework.domain.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.enums.ResultEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询返回结果类
 *
 * @author 沈家文
 * @date 2020/7/28
 */
@Getter
@Setter
@ToString
public class PageResult<T> extends ApiResult<List<T>> {

    /**
     * 总数
     */
    private Long total;

    /**
     * 每页个数
     */
    private Long size;

    /**
     * 当前页下标
     */
    private Long currIndex;

    /**
     * 最多页数
     */
    private Long maxIndex;

    public static <T> PageResult<T> success(Page<T> page) {
        PageResult<T> result = new PageResult<>();
        result.total = page.getTotal();
        result.size = page.getSize();
        result.currIndex = page.getCurrent();
        result.maxIndex = page.getPages();
        result.setData(page.getRecords());
        result.setCode(ResultEnum.success.getCode());
        return result;
    }

    public static <T> PageResult<T> empty(Long size, Long currIndex) {
        PageResult<T> result = new PageResult<>();
        result.total = 0L;
        result.size = size;
        result.currIndex = currIndex;
        result.maxIndex = 1L;
        result.setData(new ArrayList<>());
        result.setCode(ResultEnum.success.getCode());
        return result;
    }


}
