package com.runda.projectframework.utils;

import com.runda.projectframework.app.others.Constants;
import com.runda.projectframework.app.others.event.Event;
import com.runda.projectframework.app.others.event.EventBusUtil;
import com.runda.projectframework.app.others.event.EventCode;
import com.runda.projectframework.app.repository.bean.user.UserInfo;
import com.tencent.mmkv.MMKV;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * Created by Kongdq
 *
 * @date $date$
 * Description:
 */
public class CommonUtils {

    /**
     * 是否是手机号
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isMobilePhone(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(14[4-9])|(15[^4])|(16[6-7])|(17[^9])|(18[0-9])|(19[1|8|9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 登出登录
     */

    public static void loginOut() {

//        JPushInterface.deleteAlias(ApplicationMine.getInstance(),0);

        Constants.ISLOGIN = false;

        MMKV.defaultMMKV().encode(Constants.LOGINSTATUS, false);
        MMKV.defaultMMKV().removeValueForKey(Constants.USER_ID);
        MMKV.defaultMMKV().removeValueForKey(Constants.USER_NAME);
        MMKV.defaultMMKV().removeValueForKey(Constants.USER_NICKNAME);
        MMKV.defaultMMKV().removeValueForKey(Constants.USER_PHONE);
        MMKV.defaultMMKV().removeValueForKey(Constants.USER_CHILDID);
        MMKV.defaultMMKV().removeValueForKey(Constants.USER_PORTRAIT);
        MMKV.defaultMMKV().removeValueForKey(Constants.WECHAT_CASH_NICKNAME);
        MMKV.defaultMMKV().removeValueForKey(Constants.WECHAT_CASH_HEADIMGURL);
        MMKV.defaultMMKV().removeValueForKey(Constants.USER_BALANCE);
        MMKV.defaultMMKV().removeValueForKey(Constants.USER_EXAMINE);
        MMKV.defaultMMKV().removeValueForKey(Constants.TOKEN);
        MMKV.defaultMMKV().removeValueForKey(Constants.REFRESH_TOKEN);

        if (Constants.USER_TEACHER.equals(MMKV.defaultMMKV().decodeString(Constants.USER_USETTYPE))) {
            EventBusUtil.sendEvent(new Event(EventCode.LOGINOUT_TEACHER));
        } else {
            EventBusUtil.sendEvent(new Event(EventCode.LOGINOUT_PARENT));
        }

    }

    /**
     * 登录成功保存用户信息
     *
     * @param userInfo
     */
    public static void saveUserInfo(UserInfo userInfo) {


        if (userInfo == null) {
            return;
        }

        Constants.ISLOGIN = true;
        MMKV.defaultMMKV().encode(Constants.LOGINSTATUS, true);

        if (!CheckEmptyUtils.isEmpty(userInfo.getId())) {
            MMKV.defaultMMKV().encode(Constants.USER_ID, userInfo.getId());
        }

        if (!CheckEmptyUtils.isEmpty(userInfo.getUsername())) {
            MMKV.defaultMMKV().encode(Constants.USER_NAME, userInfo.getUsername());
        }
        if (!CheckEmptyUtils.isEmpty(userInfo.getNickname())) {
            MMKV.defaultMMKV().encode(Constants.USER_NICKNAME, userInfo.getNickname());
        }
        if (!CheckEmptyUtils.isEmpty(userInfo.getPhone())) {
            MMKV.defaultMMKV().encode(Constants.USER_PHONE, userInfo.getPhone());
        }

        if(!CheckEmptyUtils.isEmpty(userInfo.getChildId())){
            MMKV.defaultMMKV().encode(Constants.USER_CHILDID, userInfo.getChildId());
        }
        if (!CheckEmptyUtils.isEmpty(userInfo.getPortrait())) {
            MMKV.defaultMMKV().encode(Constants.USER_PORTRAIT, userInfo.getPortrait());
        }
        if (!CheckEmptyUtils.isEmpty(userInfo.getUserType())) {
            MMKV.defaultMMKV().encode(Constants.USER_USETTYPE, userInfo.getUserType());
        }

        if(!CheckEmptyUtils.isEmpty(userInfo.getPayNickname())){
            MMKV.defaultMMKV().encode(Constants.WECHAT_CASH_NICKNAME, userInfo.getPayNickname());
        }

        if(!CheckEmptyUtils.isEmpty(userInfo.getPayPortrait())){
            MMKV.defaultMMKV().encode(Constants.WECHAT_CASH_HEADIMGURL, userInfo.getPayPortrait());
        }


        if(Constants.USER_TEACHER.equals(MMKV.defaultMMKV().decodeString(Constants.USER_USETTYPE))){
            MMKV.defaultMMKV().encode(Constants.USER_BALANCE, userInfo.getBalance());
        }

        if (Constants.USER_TEACHER.equals(MMKV.defaultMMKV().decodeString(Constants.USER_USETTYPE))) {
            MMKV.defaultMMKV().encode(Constants.USER_EXAMINE, userInfo.getExamine());
        }

        if (null != userInfo.getUserToken()) {
            MMKV.defaultMMKV().encode(Constants.TOKEN, userInfo.getUserToken().getAccessToken());
            MMKV.defaultMMKV().encode(Constants.REFRESH_TOKEN, userInfo.getUserToken().getRefreshToken());
        }
    }


    /**
     * 是否是office pdf txt
     * @param filePath
     * @return
     */
    public static boolean isOffice(String filePath){
        String path = filePath.toUpperCase();
        return path.endsWith(".DOC") || path.endsWith(".DOCX")
                || path.endsWith(".XLS")|| path.endsWith(".XLSX")  || path.endsWith(".PPT") ||path.endsWith(".PPTX")||path.endsWith(".PDF")||path.endsWith(".TXT");
    }


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
