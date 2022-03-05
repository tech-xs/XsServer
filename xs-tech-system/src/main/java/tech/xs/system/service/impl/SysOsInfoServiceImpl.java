package tech.xs.system.service.impl;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import tech.xs.common.lang.StringUtils;
import tech.xs.system.domain.entity.SysOsInfo;
import tech.xs.system.service.SysOsInfoService;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OperatingSystem;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 系统信息服务实现类
 *
 * @author 沈家文
 * @date 2021/7/1 16:31
 */
@Service
public class SysOsInfoServiceImpl implements SysOsInfoService {


    @Override
    public SysOsInfo current() {
        SysOsInfo info = new SysOsInfo();
        ApplicationHome appHome = new ApplicationHome(getClass());
        File jarFile = appHome.getSource();
        if (jarFile != null) {
            info.setBuildTime(new Date(jarFile.lastModified()));
        }

        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        String cpuModel = cpuInfo.getCpuModel();
        if (StringUtils.isNotBlank(cpuModel)) {
            String[] split = cpuModel.split("\n");
            if (split != null && split.length > 0) {
                info.setCpuModel(split[0]);
            }
        }

        OperatingSystem os = OshiUtil.getOs();
        if (os != null) {
            info.setOsFamily(os.getFamily());
            info.setOsManufacturer(os.getManufacturer());
            info.setOsVersion(os.getVersionInfo().toString());
        }

        GlobalMemory memory = OshiUtil.getMemory();
        if (memory != null) {
            info.setMemoryTotal(memory.getTotal());
        }
        return info;
    }

}
