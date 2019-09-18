package com.willemgeo.szdb.utils;

import java.util.regex.Pattern;

public class JIdCardUtils {
    private final static Pattern PARTTERN_CARD_NO = Pattern.compile("\\d{15}|\\d{17}[0-9X]");
    private final static Pattern PARTTERN_DATE = Pattern.compile(
            "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)");
    // 1-17位相乘因子数组
    private final static int[] FACTOR = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
    // 18位随机码数组
    private final static char[] RANDOM = "10X98765432".toCharArray();

    public boolean validate(String idCardNo) {
        // 对身份证号进行长度等简单判断
        if (idCardNo == null || !PARTTERN_CARD_NO.matcher(idCardNo).matches()) {
            return false;
        }
        int len = idCardNo.length();
        // 一代身份证
        if (len == 15) {
            return PARTTERN_DATE.matcher("19" + idCardNo.substring(6, 12)).matches();
        }
        // 二代身份证
        if (len == 18 && PARTTERN_DATE.matcher(idCardNo.substring(6, 14)).matches()) {
            // 判断随机码是否相等
            return calculateRandom(idCardNo) == idCardNo.charAt(17);
        } else {
            return false;
        }

    }

    /**
     * 计算最后一位随机码
     *
     * @param idCardNo
     * @return
     */
    private char calculateRandom(String idCardNo) {
        // 计算1-17位与相应因子乘积之和
        int total = 0;
        for (int i = 0; i < 17; i++) {
            total += Character.getNumericValue(idCardNo.charAt(i)) * FACTOR[i];
        }
        // 判断随机码是否相等
        return RANDOM[total % 11];
    }

    /**
     * 计算最后一位随机码
     *
     * @param idCardNo
     * @return
     */
    private char calculateRandom(char[] idCardNo) {
        // 计算1-17位与相应因子乘积之和
        int total = 0;
        for (int i = 0; i < 17; i++) {
            total += Character.getNumericValue(idCardNo[i]) * FACTOR[i];
        }
        // 判断随机码是否相等
        return RANDOM[total % 11];
    }

    /**
     * 15和18位身份证号码转换
     *
     * @param idCardNo
     * @return
     */
    public String convert(String idCardNo) {
        if (idCardNo == null) {
            throw new IllegalArgumentException("idCardNo must not null.");
        }
        if (!validate(idCardNo)) {
            throw new IllegalArgumentException("idCardNo invalid.");
        }
        int len = idCardNo.length();
        char[] result = null;
        int index = 0;
        if (len == 15) {// 添加年份与随机码
            result = new char[18];
            // 复制行政区域代码
            for (; index < 6; index++) {
                result[index] = idCardNo.charAt(index);
            }
            // 添加年份，固定值：19
            result[index++] = '1';
            result[index++] = '9';
            // 添加年月日与附加信息
            for (; index < 17; index++) {
                result[index] = idCardNo.charAt(index - 2);
            }
            result[index] = calculateRandom(result);
        } else if (len == 18) {// 去除年份与随机码
            result = new char[15];
            // 复制行政区域代码
            for (; index < 6; index++) {
                result[index] = idCardNo.charAt(index);
            }
            // 跳过两位年份；
            index += 2;
            // 去除最后一位
            for (; index < 17; index++) {
                result[index - 2] = idCardNo.charAt(index);
            }
        }
        if (result == null) {
            throw new IllegalArgumentException("idCardNo length must equals 15 or 18.");
        }
        return new String(result);
    }
}
