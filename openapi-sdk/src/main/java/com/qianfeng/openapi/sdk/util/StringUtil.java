package com.qianfeng.openapi.sdk.util;

/**
 * 字符串工具类
 *
 * @author Hu Feng
 * @version 1.0, 2011/6/22
 */
public final class StringUtil {

    private StringUtil() {
    }

    /**
     * 检查指定的字符串列表是否不为空
     *
     * @param values
     * @return
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 检查指定的字符串是否为空
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

}
