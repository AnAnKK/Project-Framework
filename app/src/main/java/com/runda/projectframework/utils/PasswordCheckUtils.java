package com.runda.projectframework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Kongdq
 *
 * @date $date$
 * Description:
 */
public class PasswordCheckUtils {


    /**
     * 密码格式8-16位数字 字符组合
     * @param password
     * @return
     */
    public static boolean checkPassword(String password){

        if(password.length()<8){
            return false;
        }

        if(!isContainsNum(password)){
            return false;
        }

        if(isNumeric(password)){
            return false;
        }

        return true;
    }

    /**
     * 是否包含数字
     * @param input
     * @return
     */
    public static boolean isContainsNum(String input) {
        int len = input.length();
        for (int i = 0; i < len; i++) {
            if (Character.isDigit(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    /**
     * 是否全是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }


}
