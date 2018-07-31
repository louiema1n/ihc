package com.lm.ihc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IhcsUtil {

    private boolean flag = false;
    private Pattern pattern;
    private Matcher matcher;
    private String result;
    /**
     * 根据项目名称获取total数
     * @param prjName
     * @return
     */
    public int formatTotal(String prjName, String reg) {
        pattern = Pattern.compile(reg);
        matcher = pattern.matcher(prjName);
        result = matcher.replaceAll("").trim();
        if ((result.equals("") || result == null)) {
            return 0;
        }

        String[] regs = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        for (int i = 0; i < regs.length; i++) {
            if (result.equals(regs[i])) {
                result = i + 1 + "";
                break;
            }
        }
        return Integer.parseInt(result);
    }

    /**
     * 获取病理号
     * @param testNo
     * @return
     */
    public static String getNumber(String testNo) {
        String str = testNo;
        int index = testNo.indexOf("-");
        if (index > -1) {
            str = testNo.substring(0, index);
        }
        return str;
    }

    /**
     * 获取小号
     * @param testNo
     * @return
     */
    public static String getSon(String testNo) {
        String str;
        int index = testNo.indexOf("-");
        if (index > -1) {
            str = testNo.substring(index + 1, testNo.length());
            return str;
        } else {
            return "01";
        }
    }

    /**
     * 获取具体细项-广州
     * @param result
     * @return
     */
    public static String getGZItems(String result) {
        Pattern pattern = Pattern.compile("((免疫组化：|免疫荧光：|特殊染色：|免疫组化:|免疫荧光:|特殊染色:)(\\S+[、|, |，]+)*(\\w| |/|-*\\w+)+)(\\S+[、|, |，]+)*(\\w| |/|-*\\w+)+");
        Matcher matcher = pattern.matcher(result);
        while (matcher.find()) {
            String str = matcher.group();
            str = str.substring(5, str.length());
            str = str.replaceAll("[, |，]", "、");
            str = str.replaceAll("、、", "、");
            return str;
        }
        return null;
    }

    /**
     * 获取具体细项-贵阳
     * @param result
     * @return
     */
    public static String getGYItems(String result) {
        return result.substring(result.lastIndexOf("\n") + 1);
    }
}
