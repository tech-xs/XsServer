package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.framework.base.BaseEntity;
import tech.xs.system.domain.entity.SysDictValue;
import tech.xs.system.dao.SysDictValueDao;
import tech.xs.system.service.SysDictValueService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典值实现类
 *
 * @author 沈家文
 * @date 2020/10/21
 */
@Service
public class SysDictValueServiceImpl extends BaseSysServiceImpl<SysDictValueDao, SysDictValue> implements SysDictValueService {

    @Resource
    private SysDictValueDao sysDictValueDao;

    @Override
    public List<SysDictValue> tree(Long dictId) {
        List<SysDictValue> rootNodeList = sysDictValueDao.selectList(Wrappers.<SysDictValue>lambdaQuery()
                .eq(SysDictValue::getDictId, dictId)
                .isNull(SysDictValue::getFatherId));
        if (CollectionUtils.isNotEmpty(rootNodeList)) {
            buildTree(rootNodeList);
        }
        return rootNodeList;
    }

    @Override
    public void buildTree(List<SysDictValue> nodeList) {
        for (SysDictValue item : nodeList) {
            List<SysDictValue> childrenList = sysDictValueDao.selectList(Wrappers.<SysDictValue>lambdaQuery()
                    .eq(SysDictValue::getFatherId, item.getId())
                    .orderByAsc(SysDictValue::getSort));
            if (CollectionUtils.isNotEmpty(childrenList)) {
                item.setChildList(childrenList);
                buildTree(childrenList);
            }
        }
    }

    @Override
    public void updateByIdSetFatherId(SysDictValue dictValue) {
        sysDictValueDao.updateById(dictValue);
        if (dictValue.getFatherId() == null) {
            sysDictValueDao.update(null, Wrappers.<SysDictValue>lambdaUpdate().set(SysDictValue::getFatherId, null).eq(BaseEntity::getId, dictValue.getId()));
        }
    }

}
