package tech.xs.system.service;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import tech.xs.framework.base.BaseService;
import tech.xs.framework.domain.model.ApiResult;

import java.io.IOException;

/**
 * 系统Service
 *
 * @author 沈家文
 * @date 2020/10/6
 */
public interface SysService extends BaseService {

    /**
     * 修改网站图标
     *
     * @param iconFile
     * @return
     */
    ApiResult<String> modifyIcon(MultipartFile iconFile) throws Exception;

    /**
     * 修改网站名称
     *
     * @param webName
     */
    void modifyWebName(String webName);

}
