package com.zzy.core.utils;

import cn.hutool.extra.qrcode.QrCodeUtil;
import java.io.File;

public class QRCodeUtil {

    private static final int width = 300;// 默认二维码宽度
    private static final int height = 300;// 默认二维码高度
    private static final String format = "png";// 默认二维码文件格式


    /**
     * 生成二维码图片文件
     * true:执行成功
     * @param content
     *            二维码内容
     * @param path
     *            文件保存路径
     */
    public static boolean createQRCode(String content, String path,String fileName) {
        return QrCodeUtil.generate(content,width,height,
                new File(new StringBuilder(path).append(fileName).append(".").append(format).toString()))!=null;
    }


}
