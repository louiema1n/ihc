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
        if ((result.equals("") || result == null) && !flag) {
            formatTotal(prjName, "[^一|二|三|四|五|六|七|八|九|十]");
            flag = true;
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
        return testNo.substring(0, testNo.length() - 3);
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
}
