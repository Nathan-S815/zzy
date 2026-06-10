package com.zzy.core.dto;

import cn.hutool.core.date.DateUtil;
import com.zzy.core.utils.JsonUtil;

import java.util.HashMap;

public class R extends HashMap<String, Object> {
    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    public static final String DATE_TAG = "ts";


    /**
     * 状态类型
     */
    public enum Type
    {
        /** 成功 */
        SUCCESS(10000),
        /** 警告 */
        WARN(10001),
        /** 错误 */
        ERROR(10002);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    /**
     * 初始化一个新创建的 R 对象，使其表示一个空消息。
     */
    public R()
    {
    }

    /**
     * 初始化一个新创建的 R 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     */
    public R(Type type, String msg)
    {
        super.put(DATE_TAG, DateUtil.now());
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 R 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     * @param data 数据对象
     */
    public R(Type type, String msg, Object data)
    {
        super.put(DATE_TAG, DateUtil.now());
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);

        if (data!=null)
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static R ok()
    {
        return R.ok("success");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static R ok(Object data)
    {
        return R.ok("success", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static R ok(String msg)
    {
        return R.ok(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static R ok(String msg, Object data)
    {
        return new R(Type.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static R warn(String msg)
    {
        return R.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static R warn(String msg, Object data)
    {
        return new R(Type.WARN, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static R error()
    {
        return R.error("failed");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static R error(String msg)
    {
        return R.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static R error(String msg, Object data) {
        return new R(Type.ERROR, msg, data);
    }


    public static R success(){
        return R.ok("操作成功");
    }

    public static R fail(){
        return R.error("操作失败");
    }


    public static R nullValueError() {
        return R.warn("参数不能为空");
    }


    @Override
    public String toString() {
        return JsonUtil.toJsonStr(this);
    }
}
