package com.zmsoft.lib.tonglian.helper;

import android.content.Context;
import android.os.Environment;

import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.lib.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import static com.zmsoft.ccd.lib.utils.StringUtils.appendStr;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/24 14:37
 *     desc  : 通联文件存储
 * </pre>
 */
public class AllInFileHelper {

    public static final String FILE_NAME = "cloud_cash_all_in_pos.txt";
    public static final String TAG = AllInFileHelper.class.getSimpleName() + " : ";

    private static File createExternalStoragePublicDirectory() throws IOException {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        if (file != null && file.exists()) {
            String createAllInFilename = appendStr(file.getAbsolutePath(), "/", FILE_NAME);
            File allInFile = new File(createAllInFilename);
            if (!allInFile.exists()) {
                boolean isSuccess = allInFile.createNewFile();
                Logger.d(TAG + "创建" + file.getAbsolutePath() + "/" + FILE_NAME + " 文件，" + isSuccess);
            }
        } else {
            Logger.d(TAG + "公共存储路径为空");
        }
        return file;
    }

    /**
     * 存储pos开关
     * 1.创建存储文件
     * 1.1 取公共存储空间
     * 1.2 创建文件
     * 2.将内容写入文件内
     * 2.1 有文件，先删除内容，在存储
     *
     * @param value
     */
    public static void saveLocalAllInPos(boolean value) {
        File file = null;
        try {
            file = createExternalStoragePublicDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file == null || !file.exists()) {
            return;
        }
        String writeFilePath = appendStr(file.getAbsolutePath(), "/", FILE_NAME);
        File writeFile = new File(writeFilePath);
        if (writeFile.exists()) {
            FileUtils.writeFileFromString(writeFilePath, String.valueOf(value), false);
        }
    }


    /**
     * 同步pos信息
     * <p>
     * 1.获取本地pos开关
     * 2.获取app内部pos开关
     *
     * @return
     */
    public static void isSyncAllInPos(Context context) {
        // 是否是通联设备，通联设备强制开启
        boolean isAllInDevice = AllInHelper.isAllInDevice();
        AllInSpHelper.saveAllInPos(context, isAllInDevice);
    }

}
