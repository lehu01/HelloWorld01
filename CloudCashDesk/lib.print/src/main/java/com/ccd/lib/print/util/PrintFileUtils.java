package com.ccd.lib.print.util;

import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/4 17:35
 *     desc  : 打印文件工具类
 * </pre>
 */
public class PrintFileUtils {

    /**
     * 创建下载文件File.txt
     *
     * @param dirFile  文件夹名称
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    public static File createFile(String dirFile, String fileName) throws IOException {
        // get root file
        String printDir = StringUtils.appendStr(GlobalVars.context.getFilesDir().getAbsolutePath(), "/", dirFile);

        // create runPrint dir
        File filePrintDir = new File(printDir);
        if (!filePrintDir.exists()) {
            filePrintDir.mkdirs();
        }

        // create runPrint file txt
        File printFile = new File(StringUtils.appendStr(filePrintDir.getAbsolutePath(), "/", fileName));
        if (!printFile.exists()) {
            printFile.createNewFile();
        }
        return printFile;
    }
}
