package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.xs.system.dao.SysDictValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.framework.base.BaseEntity;
import tech.xs.system.domain.bo.dict.GetDictDetailsBo;
import tech.xs.system.domain.entity.SysDict;
import tech.xs.system.domain.entity.SysDictValue;
import tech.xs.system.dao.SysDictDao;
import tech.xs.system.service.SysDictService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 字典Service实现类
 *
 * @author 沈家文
 * @date 2020/10/21
 */
@Service
public class SysDictServiceImpl extends BaseSysServiceImpl<SysDictDao, SysDict> implements SysDictService {

    @Resource
    private SysDictDao sysDictDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        sysDictValueService.delete(Wrappers.<SysDictValue>lambdaQuery().eq(SysDictValue::getDictId, id));
        sysDictDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIdList(Collection<Long> idList) {
        sysDictValueService.delete(Wrappers.<SysDictValue>lambdaQuery().in(SysDictValue::getDictId, idList));
        sysDictDao.delete(Wrappers.<SysDict>lambdaQuery().in(BaseEntity::getId, idList));
    }


    @Override
    public SysDict getDetails(GetDictDetailsBo bo) {
        SysDict dict = sysDictDao.selectOne(Wrappers.<SysDict>lambdaQuery()
                .eq(SysDict::getCode, bo.getCode()));
        if (dict == null) {
            return null;
        }
        List<SysDictValue> valueList = sysDictValueService.list(Wrappers.<SysDictValue>lambdaQuery()
                .eq(SysDictValue::getDictId, dict.getId()));
        dict.setValueList(valueList);
        return dict;
    }

}
