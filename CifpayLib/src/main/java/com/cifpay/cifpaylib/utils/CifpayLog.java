package com.cifpay.cifpaylib.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author BaoHang
 * @version 1.0
 * @data 2012-2-20
 */
@SuppressLint("SimpleDateFormat")
public class CifpayLog {

    private static Boolean MYLOG_WRITE_TO_FILE = true;//

    private static Boolean MYLOG_WRITE_TO_FILE_W = false;
    private static Boolean MYLOG_WRITE_TO_FILE_E = true;
    private static Boolean MYLOG_WRITE_TO_FILE_D = false;
    private static Boolean MYLOG_WRITE_TO_FILE_I = false;
    private static Boolean MYLOG_WRITE_TO_FILE_V = false;
    public static String MYLOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory() + "/cifpay/log/";//
    private static int SDCARD_LOG_FILE_SAVE_DAYS = 7;//
    private static String MYLOGFILEName = "Log.txt";//
    private static boolean enableWriteFile = true;
    private static boolean enablePrint = true;
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");//
    private static SimpleDateFormat logfile = new SimpleDateFormat(
            "yyyy-MM-dd_HH");//

    public static void w(String tag, Object msg) { //
        if (null == msg) {
            return;
        }
        log(tag, msg.toString(), 'w');
    }

    public static void e(String tag, Object msg) { //
        if (null == msg) {
            return;
        }
        log(tag, msg.toString(), 'e');
    }

    public static void d(String tag, Object msg) {//
        if (null == msg) {
            return;
        }
        log(tag, msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {//
        if (null == msg) {
            return;
        }
        log(tag, msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), 'v');
    }

    public static void w(String tag, String text) {
        log(tag, text, 'w');
    }

    public static void e(String tag, String text) {
        log(tag, text, 'e');
    }

    public static void d(String tag, String text) {
        log(tag, text, 'd');
    }

    public static void i(String tag, String text) {
        log(tag, text, 'i');
    }

    public static void v(String tag, String text) {
        log(tag, text, 'v');
    }

    /**
     * 打印并将log写入文件
     * @param tag
     * @param msg
     * @param level log等级
     */
    private static void log(String tag, String msg, char level) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (CifpayLog.enablePrint) {// MYLOG_SWITCH
            if ('w' == level) {
                Log.w(tag, msg);
            } else if ('d' == level) {
                Log.d(tag, msg);
            } else if ('i' == level) {
                Log.i(tag, msg);
            } else if ('v' == level) {
                Log.v(tag, msg);
            } else if ('e' == level) { //
                Log.e(tag, msg);
            }
        }
        if (MYLOG_WRITE_TO_FILE) {
            switch (level) {
                case 'w':
                    MYLOG_WRITE_TO_FILE = MYLOG_WRITE_TO_FILE_W;
                    break;
                case 'e':
                    MYLOG_WRITE_TO_FILE = MYLOG_WRITE_TO_FILE_E;
                    break;
                case 'd':
                    MYLOG_WRITE_TO_FILE = MYLOG_WRITE_TO_FILE_D;
                    break;
                case 'i':
                    MYLOG_WRITE_TO_FILE = MYLOG_WRITE_TO_FILE_I;
                    break;
                case 'v':
                    MYLOG_WRITE_TO_FILE = MYLOG_WRITE_TO_FILE_V;
                    break;
            }
            // sdcard 剩余存储空间大于200M时，才能写入文件
            if (MYLOG_WRITE_TO_FILE
                    && StorageUtil.getAvailableExternalMemorySize() >= 200 * 1024 * 1024) {
                if (level != 'e') {// 非 'e'级别的循环更新协议LOG
                    if (!CifpayLog.enableWriteFile)
                        return;// 若log开关关闭，则不写入文件，避免LOG文件过大
                }
                writeLogtoFile(String.valueOf(level), tag, msg);
            }
        }
    }

    /**
     * 向文件写入log信息
     * @param mylogtype
     * @param tag
     * @param text
     */
    private static void writeLogtoFile(String mylogtype, String tag, String text) {//
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype
                + "    " + tag + "    " + text;
        File file = new File(MYLOG_PATH_SDCARD_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFiel + MYLOGFILEName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                // 创建新文件的时候检测并删除过期的文件
                tyrDeleteFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Utilties.execRootCmdSilent("mount -o remount,rw vfat /mnt/sdcard");
            // //此命令可以短暂修复，但是很快变为系统只读,需要删除所有文件后正常
            // Utilties.execRootCmdSilent("rm -r /mnt/sdcard/*");
            // new ENCHomeAct().copyAssetFiles();
        }
    }

    /**
     * 删除多少天以前的LOG
     */
    public static void tyrDeleteFile() {
        String timeString = getDateBefore();
        // 去除 - 和 : 符号
        timeString = timeString.replace("-", "");
        // 得到过期时间点
        long time = Long.valueOf(timeString.substring(0, 8));

        File[] files = new File(MYLOG_PATH_SDCARD_DIR).listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String name = files[i].getName();
                if (name.length() != 20) {// 生成错误文件名(文件名里面意外夹杂00)的文件删除，并删除就版本log文件(长度为17或19)
                    files[i].delete();
                } else {// 将过期的log文件删除
                    name = name.replace("-", "");
                    for (int j = 0; j < 8; j++) {
                        if (name.charAt(j) < '0' || name.charAt(j) > '9') {
                            files[i].delete();
                            return;
                        }
                    }
                    long time2 = Long.valueOf(name.substring(0, 8));
                    if (time >= time2) {
                        files[i].delete();
                    }
                }
            }
        }
    }

    /**
     * 获取 SDCARD_LOG_FILE_SAVE_DAYS 天以前的时间，
     *
     * @return yyyyMMdd字符串
     */
    private static String getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS);
        return logfile.format(now.getTime());
    }

    public static void saveException(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        log("saveException", sw.toString(), 'e');
    }

    public static void saveException(String TAG, Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        log(TAG + ": saveException", sw.toString(), 'e');
    }

    public static void saveThrowable(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        log("saveThrowable", sw.toString(), 'e');
    }

}
