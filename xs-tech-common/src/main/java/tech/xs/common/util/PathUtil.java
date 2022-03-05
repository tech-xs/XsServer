package tech.xs.common.util;


import tech.xs.common.constant.Symbol;

/**
 * 路径工具
 *
 * @author 沈家文
 * @date 2021-25-29 17:25
 */
public class PathUtil {

    /**
     * 修正文件夹路径
     * 如果文件夹路径最后没有斜杠则添加斜杠,否则不处理
     *
     * @param path 文件夹路径
     * @return 返回修正过的结果
     */
    public static String repairDirPath(String path) {
        if (!path.endsWith(Symbol.SLASH) && !path.endsWith(Symbol.BACK_SLASH)) {
            return path + Symbol.SLASH;
        }
        return path;
    }

}
