package com.cifpay.cifpaylib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * Created by cifpay on 2015/6/15.
 * 二维码公共方法
 */
public class ZXingUtils {

    /**
     * *
     * 根据输入字符串生成二维码，并可设置宽度与高度
     *
     * @param str
     * @param widthAndHeight
     * @return
     * @throws WriterException
     */
    public static Bitmap createQRCode(String str, int widthAndHeight)
            throws WriterException {
        int BLACK = 0xff000000;
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 根据字符串生成 中间带图片的二维码 ，默认 300，可修改
     *
     * @param str
     * @param context
     * @return
     * @throws WriterException
     */
    public static Bitmap cretaeQRCode(String str, Context context, int resId)
            throws WriterException {

        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败

        BitMatrix matrix = new MultiFormatWriter().encode(str,

                BarcodeFormat.QR_CODE, 300, 300);// 如果要指定二维码的边框以及容错率，最好给encode方法增加一个参数：hints
        // 图片宽度的一般
        // 构造需要插入的图片对象

        Bitmap mBitmap = ((BitmapDrawable) context.getResources().getDrawable(resId)).getBitmap();

        int IMAGE_HALFWIDTH = 20; // 一个Hashmap

        int width = matrix.getWidth();

        int height = matrix.getHeight();

        // 二维矩阵转为一维像素数组,也就是一直横着排了

        int halfW = width / 2;

        int halfH = height / 2;

        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                        && y > halfH - IMAGE_HALFWIDTH

                        && y < halfH + IMAGE_HALFWIDTH) {

                    pixels[y * width + x] = mBitmap.getPixel(x - halfW
                            + IMAGE_HALFWIDTH, y

                            - halfH + IMAGE_HALFWIDTH);

                } else {

                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；

                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000
                            : 0xfffffff;

                }

            }

        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,

                Bitmap.Config.ARGB_8888);

        // 通过像素数组生成bitmap

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;

    }

    public static Bitmap cretaeQRCode(String str, Context context, Bitmap mBitmap )
            throws WriterException {

        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败

        BitMatrix matrix = new MultiFormatWriter().encode(str,

                BarcodeFormat.QR_CODE, 300, 300);// 如果要指定二维码的边框以及容错率，最好给encode方法增加一个参数：hints
        // 图片宽度的一般
        // 构造需要插入的图片对象

        int IMAGE_HALFWIDTH = 20; // 一个Hashmap

        int width = matrix.getWidth();

        int height = matrix.getHeight();

        // 二维矩阵转为一维像素数组,也就是一直横着排了

        int halfW = width / 2;

        int halfH = height / 2;

        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                        && y > halfH - IMAGE_HALFWIDTH

                        && y < halfH + IMAGE_HALFWIDTH) {

                    pixels[y * width + x] = mBitmap.getPixel(x - halfW
                            + IMAGE_HALFWIDTH, y

                            - halfH + IMAGE_HALFWIDTH);

                } else {

                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；

                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000
                            : 0xfffffff;

                }

            }

        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,

                Bitmap.Config.ARGB_8888);

        // 通过像素数组生成bitmap

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;

    }
}
