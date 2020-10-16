package com.runda.projectframework.app.others;

import android.os.Environment;

import com.blankj.utilcode.util.Utils;

import java.io.File;

/**
 * Created by Kongdq
 *
 * @date 2019/10/31
 * Description:静态常量存放处
 */
public class Constants {

    public static final boolean DEBUG = true;
    public static final int PAGE_SIZE = 10;
    public static final int TIME_DELAY_CLICK = 250;
    public static final int TIME_THROTTLE_CLICK = 500;
    public static final int TIME_RECORD_INFO_KEEP = 7 * 1000 * 60 * 60 * 24;//登录后信息保存有效时长，单位 毫秒
    public static final int ERROR_CODE_LOCAL = 1000;
    public static final int ERROR_CODE_REMOTE = 1001;
    public static final int ERROR_CODE_WECHAT_BIND = 204;
    public static final int ERROR_CODE_OTHERS = 1002;
    public static final int ERROR_CODE_TIMEOUT = 1003;
    public static final int ERROR_CODE_CONNECT = 1004;
    public static final int ERROR_CODE_DECRYPT = 1005;
    public static final int ERROR_CODE_JSONPARSE = 1006;
    public static final int ERROR_CODE_NONETWORK = 1101;
    public static final int ERROR_CODE_NOTSIGNED = 1102;
    public static final int ERROR_CODE_TOKENVOERTIME_4033 = 4033;
    public static final String ERROR_STRING_UNKNOWN = "发生了一点错误 ⊙ˍ⊙";
    public static final String ERROR_STRING_TIMEOUT = "连接超时啦 (*´Д｀*)";
    public static final String ERROR_STRING_CONNECT = "服务器君不见了 (#°Д°)";
    public static final String ERROR_STRING_SERVICE = "服务器君操作失败 (#°Д°)";
    public static final String ERROR_STRING_JSONPARSE = "参数解析错误 ⊙ˍ⊙";
    public static final String ERROR_STRING_CREATEPARAM = "生成参数错误 ⊙ˍ⊙";
    public static final String ERROR_STRING_GETDATAFAILED = "获取信息失败了 >_<";
    public static final String ERROR_STRING_NONETWORK = "当前网络不畅,请检查您的网络设置";
    public static final String ERROR_STRING_NOTSIGNED = "您还尚未登录";
    public static final String ERROR_STRING_TOKENVOERTIME = "当前登录会话已过期，请重新登录";
    public static final int STATELAYOUT_SUCCESS = 200;


    //版本更新相关
    public static final String APP_UPDATEURL = Constants.WEB_HOST+"app/user/getSysVersion";


    //首次登陆
    public static final String APP_FIRST_IN = "com.runda.projectframework.appfirstin";
    /**
     * ip
     */
    //正式地址
    public static final String WEB_HOST = "http://112.126.62.229:9999/";
    public static final String WEB_IMAGE = "http://112.126.62.229:9999";

    //测试地址
//    public static final String WEB_HOST = "http://112.126.62.229:8888/";
//    public static final String WEB_IMAGE = "http://112.126.62.229:8888";
    /**
     * token
     */
//    public static final String TOKEN = "token";
//    public static final String REFRESH_TOKEN = "refreshtoken";

    // String "1"代表家长端, "2"代表教师端,常量用,判断家长教师请用下方用户信息
//    public static final String USER_PARENT = "1";
//    public static final String USER_TEACHER = "2";
    /**
     * 用户信息
     */
//    public static final String USER_ID = "jjpt_userid";
//    public static final String USER_NAME = "jjpt_username";
//    public static final String USER_NICKNAME = "jjpt_nickname";
//    public static final String USER_PHONE = "jjpt_phone";
//    public static final String USER_CHILDID = "jjpt_childid";
//    public static final String USER_PORTRAIT = "jjpt_portrait";//用户头像
//    public static final String USER_USETTYPE = "jjpt_usertype"; //String 家长端为1，教师端为2,
//    public static final String USER_EXAMINE = "jjpt_examine"; //教师端审核状态 0正在审核,1审核通过,2审核未通过3未审核
//    public static final String USER_BALANCE = "jjpt_teacher_balance"; //教师端余额
//    /**
//     * 微信提现信息
//     */
//    public static final String WECHAT_CASH_NICKNAME = "wechat_cash_nickname";
//    public static final String WECHAT_CASH_HEADIMGURL = "wechat_cash_headimgurl";
    /**
     * 登录状态
     */
//    public static Boolean ISLOGIN = false;
    /**
     * buggly
     */
    public static final String APPID_BUGLY = "ba4c097f64";
    /**
     * 路径
     */
    public static final String PATH_CACHE = Utils.getApp().getCacheDir().getAbsolutePath()
            + File.separator + "jjpt" + File.separator + "cache";
    public static final String PATH_IMAGES = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "jjpt" + File.separator + "Images";
    public static final String PATH_IMAGESCROP = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "jjpt" + File.separator + "Images" + File.separator + "Crops";
    public static final String PATH_DOWNLOAD = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "jjpt" + File.separator + "Downloads";
    public static final String PATH_CACHEFILE = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "jjpt" + File.separator + "CacheFiles";
    public static final String PATH_DOWNLOADIMG = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "jjpt" + File.separator + "IMG";

}

