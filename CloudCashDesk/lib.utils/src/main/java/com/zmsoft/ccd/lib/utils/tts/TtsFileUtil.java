package com.zmsoft.ccd.lib.utils.tts;

import android.Manifest;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;

import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.lib.utils.PermissionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author DangGui
 * @create 2017/10/31.
 */


public class TtsFileUtil {

    //创建一个临时目录，用于复制临时文件，如assets目录下的离线资源文件
    public static String createTmpDir(Context context) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT
                && !PermissionUtils.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.e("TTS 没有读写存储权限，创建文件夹失败");
            return "";
        }
        String sampleDir = "ccdTTS";

//        String tmpDir = Environment.getExternalStorageDirectory().toString() + "/" + sampleDir;
//        String tmpDir = context.getExternalFilesDir(null) + File.separator + sampleDir;
//        if (!TtsFileUtil.makeDir(tmpDir)) {
        String tmpDir;
        File file = context.getExternalFilesDir(sampleDir);
        if (null != file) {
            tmpDir = file.getAbsolutePath();
            if (!TtsFileUtil.makeDir(tmpDir)) {
                throw new RuntimeException("create model resources dir failed :" + tmpDir);
            }
        } else {
            throw new RuntimeException("create model resources dir failed : get file fail");
        }
//        }
        return tmpDir;
    }

    public static boolean makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return true;
        }
    }

    public static void copyFromAssets(Context context, AssetManager assets, String source, String dest, boolean isCover) throws IOException {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT
                && !PermissionUtils.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.e("TTS 没有读写存储权限，复制asset文件到本地存储失败");
            return;
        }
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = assets.open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                    }
                }
            }
        }
        Logger.d("TTS 文件复制成功：" + dest);
    }
}
