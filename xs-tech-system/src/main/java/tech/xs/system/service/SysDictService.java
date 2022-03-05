package tech.xs.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestBody;
import tech.xs.framework.base.CrudService;
import tech.xs.system.domain.bo.dict.GetDictDetailsBo;
import tech.xs.system.domain.entity.SysDict;

import javax.validation.Valid;
import java.util.List;

/**
 * 字典Service
 *
 * @author 沈家文
 * @date 2020/10/21
 */
public interface SysDictService extends BaseSysService<SysDict> {

    SysDict getDetails( GetDictDetailsBo bo);

}
