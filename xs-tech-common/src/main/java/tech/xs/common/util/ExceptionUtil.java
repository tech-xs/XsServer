package tech.xs.common.util;

/**
 * 异常工具
 */
public class ExceptionUtil {

    /**
     * 获取异常中的栈信息列表
     *
     * @param e
     * @return
     */
    public static String getStackMsg(Exception e) {
        StringBuilder strBuf = new StringBuilder();
        if (e != null) {
            strBuf.append(e.getMessage());
            strBuf.append("\r\n");
            StackTraceElement[] stackTrace = e.getStackTrace();
            if (stackTrace != null) {
                for (StackTraceElement t : stackTrace) {
                    strBuf.append(t.toString());
                    strBuf.append("\r\n");
                }
            }
        }
        return strBuf.toString();
    }

}
