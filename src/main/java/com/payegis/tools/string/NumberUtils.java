package com.payegis.tools.string;

import com.payegis.tools.util.RegexUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * company:
 * user: chenzuoli
 * date: 2018/6/13
 * time: 13:17
 * description: 数值工具类
 */
public class NumberUtils {
    private static Logger logger = Logger.getLogger(NumberUtils.class);

    /**
     * description: 将金额的缩写全部展开为最小单位元或者美元
     * param: [moneyAbbr]
     * return: java.lang.String
     * date: 2018/6/13
     * time: 15:41
     */
    public static String moneyEtl(String moneyAbbr) {
        String returnMoney = moneyAbbr;
        try {
            Pattern point = Pattern.compile(RegexUtils.pointMoneyRegex);
            Pattern rmb = Pattern.compile(RegexUtils.rmbMoneyRegex);
            Pattern dollar = Pattern.compile(RegexUtils.dollarMoneyRegex);
            Pattern tenThousand = Pattern.compile(RegexUtils.tenThousandMoneyRegex);
            Pattern hundredThousand = Pattern.compile(RegexUtils.hundredThousandMoneyRegex);
            Pattern million = Pattern.compile(RegexUtils.millionMoneyRegex);
            Pattern tenMillion = Pattern.compile(RegexUtils.tenMillionMoneyRegex);
            Pattern billion = Pattern.compile(RegexUtils.billionMoneyRegex);
            Matcher matcher;
            if (moneyAbbr.contains("亿")) {
                matcher = billion.matcher(moneyAbbr);
                if (matcher.find()) {
                    String group = matcher.group();
                    double money = Double.parseDouble(group.substring(0, group.length() - 1));
                    returnMoney = new BigDecimal(money * 100000000).toString();
                }
            } else if (moneyAbbr.contains("千万")) {
                matcher = tenMillion.matcher(moneyAbbr);
                if (matcher.find()) {
                    String group = matcher.group();
                    double money = Double.parseDouble(group.substring(0, group.length() - 2));
                    returnMoney = new BigDecimal(money * 10000000).toString();
                }
            } else if (moneyAbbr.contains("百万")) {
                matcher = million.matcher(moneyAbbr);
                if (matcher.find()) {
                    String group = matcher.group();
                    double money = Double.parseDouble(group.substring(0, group.length() - 2));
                    returnMoney = new BigDecimal(money * 1000000).toString();
                }
            } else if (moneyAbbr.contains("十万")) {
                matcher = hundredThousand.matcher(moneyAbbr);
                if (matcher.find()) {
                    String group = matcher.group();
                    double money = Double.parseDouble(group.substring(0, group.length() - 2));
                    returnMoney = new BigDecimal(money * 100000).toString();
                }
            } else if (moneyAbbr.contains("万")) {
                matcher = tenThousand.matcher(moneyAbbr);
                if (matcher.find()) {
                    String group = matcher.group();
                    double money = Double.parseDouble(group.substring(0, group.length() - 1));
                    returnMoney = new BigDecimal(money * 10000).toString();
                }
            } else if (moneyAbbr.contains("美元")) {
                matcher = dollar.matcher(moneyAbbr);
                if (matcher.find()) {
                    String group = matcher.group();
                    returnMoney = group.substring(0, group.length() - 2);
                }
            } else if (moneyAbbr.contains("元") || moneyAbbr.contains("块")) {
                matcher = rmb.matcher(moneyAbbr);
                if (matcher.find()) {
                    String group = matcher.group();
                    returnMoney = group.substring(0, group.length() - 1);
                }
            } else if (moneyAbbr.contains("角")) {
                matcher = point.matcher(moneyAbbr);
                if (matcher.find()) {
                    String group = matcher.group();
                    double money = Double.parseDouble(group.substring(0, group.length() - 1));
                    returnMoney = new BigDecimal(money * 0.1).toString();
                }
            } else {
                logger.info(moneyAbbr + "，金额非法！");
            }
        } catch (Exception e) {
            logger.error(moneyAbbr + " transform exception!", e);
        }
        return returnMoney;
    }

    /**
     * description: 数值进行相加，num2有可能是空串
     * param: [num1, num2]
     * return: java.lang.Float
     * date: 2018/6/13
     * time: 15:41
     */
    public static Float addNum(String num1, String num2) {
        Float add = 0.0f;
        if (num1.matches(RegexUtils.numberRegex)) {
            float numf1 = Float.parseFloat(num1);
            add = numf1;
            if (num2.matches(RegexUtils.numberRegex)) {
                float numf2 = Float.parseFloat(num2);
                add = numf1 + numf2;
            }
        } else if (num2.matches(RegexUtils.numberRegex)) {
            float numf2 = Float.parseFloat(num2);
            add = numf2;
        }
        return add;
    }

    /**
     * description: 数值进行相比较，返回大数，num1和num2均有可能为空字符串
     * param: [num1, num2]
     * return: java.lang.String
     * date: 2018/6/13
     * time: 15:41
     */
    public static String maxNum(String num1, String num2) {
        String max = "0";
        if (num1.matches(RegexUtils.numberRegex)) {
            max = num1;
            if (num2.matches(RegexUtils.numberRegex)) {
                Float f1 = Float.parseFloat(num1);
                Float f2 = Float.parseFloat(num2);
                if (f1 < f2) {
                    max = f2.toString();
                }
            }
        } else if (num2.matches(RegexUtils.numberRegex)) {
            max = num2;
        }
        return max;
    }

    /**
     * description: float类型字符串转换成int类型字符串，如果参数非数值类型，则原样返回
     * param: [floatStr]
     * return: java.lang.String
     * date: 2018/6/13
     * time: 15:41
     */
    public static String transformFloatStrToIntStr(String floatStr) {
        String intStr = null;
        if (floatStr != null) {
            if (floatStr.matches(RegexUtils.numberRegex)) {
                Float v = Float.parseFloat(floatStr);
                intStr = String.valueOf(Math.round(v));
            } else {
                intStr = floatStr;
            }
        }
        return intStr;
    }

    /**
     * description: 将数值转换成double类型字符串
     * param: [number]
     * return: java.lang.String
     * date: 2018/6/15
     * time: 10:44
     */
    public static String transformNumberToDouble(Number number) {
        return String.valueOf(number.doubleValue());
    }

    /**
     * description: 将数值类型字符串转换成double类型字符串
     * param: [number]
     * return: java.lang.String
     * date: 2018/6/15
     * time: 11:13
     */
    public static String transformNumberToDouble(String number) {
        if (number == null) return null;
        Number returnNum;
        if (number.matches(RegexUtils.numberRegex)) {
            returnNum = Double.parseDouble(number);
        } else {
            return number;
        }
        return String.valueOf(returnNum);
    }

    /**
     * description: float类型字符串转换成int类型
     * param: [floatStr]
     * return: java.lang.Integer
     * date: 2018/6/13
     * time: 15:41
     */
    public static Integer transformFloatStrToInt(String floatStr) {
        Integer i = null;
        if (floatStr != null) {
            if (floatStr.matches(RegexUtils.numberRegex)) {
                Float v = Float.parseFloat(floatStr);
                i = Math.round(v);
            }
        }
        return i;
    }

    /**
     * description: 获取比给定数值小并大于等于0的值
     * param: [num]
     * return: long
     * date: 2018/6/13
     * time: 15:41
     */
    public static long getLessThanNum(long num) {
        if (num < 0) return 0;
        return (long) (Math.random() * num);
    }

    /**
     * description: 生成假数字 生成指定个数的随机数字
     * param: [num]
     * return: java.lang.String
     * date: 2018/6/13
     * time: 15:42
     */
    public static String getRandomNum(long num) {
        StringBuilder returnLong = new StringBuilder("");
        try {
            for (int i = 0; i < num; i++) {
                long l = (long) (Math.random() * 10);
                while (l == 0l) {
                    l = (long) (Math.random() * 10);
                }
                returnLong.append(l);
            }
        } catch (Exception e) {
            logger.error("exception: " + e + ", get random num exception!");
        }
        return returnLong.toString();
    }

}
