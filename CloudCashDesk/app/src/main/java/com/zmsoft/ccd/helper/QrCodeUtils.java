package com.zmsoft.ccd.helper;

import android.graphics.Bitmap;
import android.util.TypedValue;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zmsoft.ccd.app.GlobalVars;

import java.util.Hashtable;

/**
 * 二维码生成器
 * Created by fengyu on 2016/10/10.
 */
public class QrCodeUtils {

    private static final int QR_WIDTH = dp2px(150);//dp
    private static final int QR_HEIGHT = dp2px(150);//dp

    public static Bitmap createImage(String codeFormat, int width, int height) {
        try {
            //判断URL合法性
            if (codeFormat == null || "".equals(codeFormat) || codeFormat.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(codeFormat, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            //显示到一个ImageView上面
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap createImage(String codeFormat) {
        return createImage(codeFormat, QR_WIDTH, QR_HEIGHT);
    }


    public static int dp2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, GlobalVars.context.getResources()
                .getDisplayMetrics());
    }
}
