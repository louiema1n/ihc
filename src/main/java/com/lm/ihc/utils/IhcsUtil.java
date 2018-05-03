package com.lm.ihc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IhcsUtil {
    /**
     * 根据项目名称获取total数
     * @param prjName
     * @return
     */
    public static int formatTotal(String prjName) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(prjName);
        return Integer.parseInt(matcher.replaceAll("").trim());
    }

    /**
     * 获取病理号
     * @param testNo
     * @return
     */
    public static int getNumber(String testNo) {
        String number = testNo.substring(0, testNo.length() - 3);
        return Integer.parseInt(number);
    }

    /**
     * 获取小号
     * @param testNo
     * @return
     */
    public static int getSon(String testNo) {
        String son = testNo.substring(testNo.length() - 2);
        return Integer.parseInt(son);
    }

    /**
     * 获取具体细项
     * @param result
     * @return
     */
    public static String getItems(String result) {
        return result.substring(result.lastIndexOf("\n") + 1);
    }
}
