package tech.xs.system.domain.entity;

import tech.xs.framework.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 系统信息
 *
 * @author 沈家文
 * @date 2021/7/1 16:30
 */
@Getter
@Setter
@ToString
public class SysOsInfo {

    /**
     * cpu型号
     */
    private String cpuModel;

    /**
     * 系统制造商
     */
    private String osManufacturer;

    /**
     * 系统系列
     */
    private String osFamily;

    /**
     * 系统版本
     */
    private String osVersion;

    /**
     * 物理内存大小 单位byte
     */
    private Long memoryTotal;

    /**
     * 项目打包时间
     */
    private Date buildTime;

}
