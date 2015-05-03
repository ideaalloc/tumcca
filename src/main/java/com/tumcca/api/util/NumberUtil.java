package com.tumcca.api.util;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-21
 */
public class NumberUtil {
    public static boolean isNumber(String value) {
        return value.matches("-?\\d+");
    }
}
