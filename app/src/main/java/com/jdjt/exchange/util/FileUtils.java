package com.jdjt.exchange.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wmy
 * @Description: 文件工具类
 * @FileName:FileUtils
 * @Package
 * @Date 2016/9/6 11:44
 */
public class FileUtils {
  /*
  *  检测SDCard是否存在
  * */
  public static boolean checkoutSDCard() {
    return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
  }

  /*
  *  将asserts下一个指定文件夹中所有文件copy到SDCard中
  * */
  public static void copyDirToSDCardFromAsserts(Context context, String dirName, String dirName2) {
    try {
      AssetManager assetManager = context.getAssets();
      String[] fileList = assetManager.list(dirName2);
      outputStr(dirName2, fileList); // 输出dirName2中文件名
      String dir = Environment.getExternalStorageDirectory() + File.separator + dirName;

      if (fileList != null && fileList.length > 0) {
        File file = null;

        // 创建文件夹
        file = new File(dir);
        if (!file.exists()) {
          file.mkdirs();
        } else {
          Log.w("FileUtils", dir + "已存在.");
        }

        // 创建文件
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        byte[] buffer = new byte[1024];
        int len = -1;
        for (int i = 0; i < fileList.length; i++) {
          file = new File(dir, fileList[i]);
          if (!file.exists()) {
            file.createNewFile();
          }
          inputStream = assetManager.open(dirName2 + File.separator + fileList[i]);
          outputStream = new FileOutputStream(file);
          while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
          }
          outputStream.flush();
        }

        // 关流
        if (inputStream != null) {
          inputStream.close();
        }
        if (outputStream != null) {
          outputStream.close();
        }
      }
    } catch (IOException e) {
      Log.e("FileUtils", "IOException e");
      e.printStackTrace();
    }
  }

  /*
  * 输出String[]中内容
  * 作用：输出文件夹中文件名
  * */
  public static void outputStr(String dirName, String[] listStr) {
    if (listStr != null) {
      if (listStr.length <= 0) {
        Log.w("FileUtils", dirName + "文件为空");
      } else {
        Log.w("FileUtils", dirName + "文件中有以下文件：");
        for (String str : listStr) {
          Log.w("FileUtils", str);
        }
      }
    }
  }

  /*
  * 解析区域位置数据
  * */
  public static String getStringFromAsserts(Context context, String filePath) throws IOException {
    if (TextUtils.isEmpty(filePath)){
      return null;
    }

    StringBuilder builder = new StringBuilder();
    AssetManager assetManager = context.getAssets();
    InputStream inputStream = null;
    inputStream = assetManager.open(filePath);
    byte[] buffer = new byte[1024];
    int len = -1;
    while ((len = inputStream.read(buffer)) != -1){
          builder.append(new String(buffer, 0, len));
    }

    return builder.toString();
  }

}
