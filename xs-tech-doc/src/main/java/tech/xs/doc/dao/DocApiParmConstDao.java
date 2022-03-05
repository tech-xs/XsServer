package tech.xs.doc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.xs.doc.domain.entity.DocApiParmConst;
import tech.xs.doc.domain.entity.DocConst;

import java.util.List;

/**
 * 参数常量关联Dao
 *
 * @author 沈家文
 * @date 2021-34-17 17:34
 */
public interface DocApiParmConstDao extends BaseMapper<DocApiParmConst> {

    /**
     * 根据参数ID查询管理常量
     *
     * @param id 参数常量
     * @return 返回常量列表
     */
    List<DocConst> selectConstByParmId(Long id);

}
