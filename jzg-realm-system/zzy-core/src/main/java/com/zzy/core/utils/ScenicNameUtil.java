package com.zzy.core.utils;

import cn.hutool.extra.qrcode.QrCodeUtil;

public class ScenicNameUtil {

    public static String setScenicName(String parkName) {
        parkName = parkName.replaceFirst("四川", "");
        if (parkName.contains("大熊猫")) {
            return "甲勿海大熊猫保护研究园";
        } else if (parkName.contains("嫩恩桑措")) {
            return "嫩恩桑措(神仙池)";
        } else if (parkName.contains("神仙")) {
            return "嫩恩桑措(神仙池)";
        } else if (parkName.contains("古藏")) {
            return "古藏寨";
        } else if (parkName.contains("甘海")) {
            return "爱情海(甘海子)";
        } else if (parkName.contains("爱情")) {
            return "爱情海(甘海子)";
        } else if (parkName.contains("甲勿")) {
            return "甲勿海";
        } else if (parkName.contains("九寨")) {
            return "九寨沟";
        } else return "";
    }
}
