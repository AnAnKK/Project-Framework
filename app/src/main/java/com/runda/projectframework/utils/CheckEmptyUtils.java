package com.runda.projectframework.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Kongdq
 * @date 2019/11/8
 * Description:
 */
public class CheckEmptyUtils {

    /**
     * 检查 String是不是空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return isNull(str) || str.length() == 0;
    }

    /**
     * 检查 数组是不是空
     *
     * @param os
     * @return
     */
    public static boolean isEmpty(Object[] os) {
        return isNull(os) || os.length == 0;
    }

    /**
     * 检查 数组是不是空
     *
     * @param os
     * @return
     */
    public static boolean isEmpty(double[] os) {
        return isNull(os) || os.length == 0;
    }

    /**
     * 检查 集合是不是空
     *
     * @param l
     * @return
     */
    public static boolean isEmpty(Collection<?> l) {
        return isNull(l) || l.isEmpty();
    }

    /**
     * 检查 map是不是空
     *
     * @param m
     * @return
     */
    public static boolean isEmpty(Map<?, ?> m) {
        return isNull(m) || m.isEmpty();
    }

    public static boolean isNull(Object o) {
        return o == null;
    }
}
