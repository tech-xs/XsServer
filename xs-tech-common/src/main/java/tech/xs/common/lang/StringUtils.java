package tech.xs.common.lang;

/**
 * 字符串工具
 *
 * @author 沈家文
 * @date 2021/7/22 14:23
 */
public class StringUtils {

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 下换线转驼峰规则
     *
     * @param str              需要转换的字符串
     * @param startCapitalized 首字母是否大写 true大写,false小写
     * @return 转换好的字符串
     */
    public static String underlineToCamel(String str, boolean startCapitalized) {
        String[] array = str.split("_");
        StringBuilder builder = new StringBuilder();
        for (String item : array) {
            String capitalized = item.substring(0, 1).toUpperCase();
            builder.append(capitalized);
            builder.append(item.substring(1).toLowerCase());
        }
        String res = builder.toString();
        if (startCapitalized) {
            return builder.toString();
        }
        String startChar = res.substring(0, 1);
        return res.replaceFirst(startChar, startChar.toLowerCase());
    }

    public static boolean equalsAny(final CharSequence string, final CharSequence... searchStrings) {
        if (ArrayUtils.isNotEmpty(searchStrings)) {
            for (final CharSequence next : searchStrings) {
                if (equals(string, next)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            return cs1.equals(cs2);
        }
        // Step-wise comparison
        final int length = cs1.length();
        for (int i = 0; i < length; i++) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNoneBlank(final CharSequence... css) {
        return !isAnyBlank(css);
    }

    public static boolean isAnyBlank(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return false;
        }
        for (final CharSequence cs : css) {
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllBlank(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isNotBlank(cs)) {
                return false;
            }
        }
        return true;
    }

}
