package com.test.util;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.text.TextUtils;
import android.util.Log;
/**
 * 打印完整的日志的工具类
 *
 * @author donkor
 */
public class LongLog {
    //LogUtil.java
    private final static int LogUtilMaxLen = 1024*3;
    public static void printMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (msg.length()<=LogUtilMaxLen) {
            Log.i(TAG,msg);
            return;
        }

        String subStr = msg.substring(0,LogUtilMaxLen);
        Log.i(TAG,subStr);
        String nextStr = msg.substring(LogUtilMaxLen);

        //递归调用
        printMsg(nextStr);
    }
    }