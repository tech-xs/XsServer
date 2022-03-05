package tech.xs.common.util;

import java.util.Random;

/**
 * 验证码工具
 *
 * @author 沈家文
 * @date 2021/9/3 20:27
 */
public class VerificationCodeUtil {

    /**
     * 生成固定长度的数值型验证码
     *
     * @param len
     * @return
     */
    public static String getNumCode(int len) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
    
}
