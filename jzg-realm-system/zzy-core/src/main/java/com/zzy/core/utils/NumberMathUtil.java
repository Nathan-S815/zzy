package com.zzy.core.utils;

import cn.hutool.core.util.NumberUtil;

import java.text.DecimalFormat;

public class NumberMathUtil {

    public static double round(Double v, int scale) {
        if (v == null) {
            return -1;
        } else {
            return NumberUtil.round(v, scale).doubleValue();
        }
    }

    public static String getRate(Object numerator, Object denominator, int i) {
        int numeratorI = Integer.parseInt(numerator.toString());
        int denominatorI = Integer.parseInt(denominator.toString());
        if (numeratorI == 0 || denominatorI == 0) {
            return "0";
        }
        double d = 1.0 * numeratorI / denominatorI * 100;
        if (i == 1) {
            d *= 10;
        } else if (i == 2) {
            d *= 100;
        }
        long round = Math.round(d);
        if (i == 0) {
            return String.valueOf(round);
        } else if (i == 1) {
            DecimalFormat df = new DecimalFormat("###0.0");
            return df.format(1.0 * round / 10);
        } else if (i == 2) {
            DecimalFormat df = new DecimalFormat("###0.00");
            return df.format(1.0 * round / 100);
        } else return null;
    }

    public static String getRateUp(Object numerator, Object denominator,int i) {
        int numeratorI = Integer.parseInt(numerator.toString());
        int denominatorI = Integer.parseInt(denominator.toString());
        if (numeratorI == 0 || denominatorI == 0) {
            return "0";
        }
        double d = 1.0 * numeratorI / denominatorI * 100;
        if (i == 1) {
            d *= 10;
        } else if (i == 2) {
            d *= 100;
        }
        double round = Math.ceil(d);
        if (i == 0) {
            DecimalFormat df = new DecimalFormat("###0");
            return df.format(round);
        } else if (i == 1) {
            DecimalFormat df = new DecimalFormat("###0.0");
            return df.format(1.0 * round / 10);
        } else if (i == 2) {
            DecimalFormat df = new DecimalFormat("###0.00");
            return df.format(1.0 * round / 100);
        } else return null;
    }

    public static String getRateDown(Object numerator, Object denominator,int i) {
        int numeratorI = Integer.parseInt(numerator.toString());
        int denominatorI = Integer.parseInt(denominator.toString());
        if (numeratorI == 0 || denominatorI == 0) {
            return "0";
        }
        double d = 1.0 * numeratorI / denominatorI * 100;
        if (i == 1) {
            d *= 10;
        } else if (i == 2) {
            d *= 100;
        }
        double round = Math.floor(d);
        if (i == 0) {
            DecimalFormat df = new DecimalFormat("###0");
            return df.format(round);
        } else if (i == 1) {
            DecimalFormat df = new DecimalFormat("###0.0");
            return df.format(1.0 * round / 10);
        } else if (i == 2) {
            DecimalFormat df = new DecimalFormat("###0.00");
            return df.format(1.0 * round / 100);
        } else return null;
    }

}
