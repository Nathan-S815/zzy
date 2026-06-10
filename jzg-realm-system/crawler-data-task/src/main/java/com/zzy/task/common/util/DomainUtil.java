package com.zzy.task.common.util;

import cn.hutool.core.util.StrUtil;
import com.vdurmont.emoji.EmojiParser;

public final class DomainUtil {



    public static int getPages(int allCount,int size){
        return allCount%size==0?allCount/size:(allCount/size)+1;
    }


    public static String removeEmojiUtf8mb4(String str){
        if(StrUtil.isBlankOrUndefined(str)) return "";
        return EmojiParser.removeAllEmojis(str.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]",""));
    }

}
