package com.nuwa.discovery.start.api.constants;


public enum SmsStatusEnum {
    UNSENT(1, "未发送"), SENT(2, "已发送"), FAILURE(3, "发送失败");

    // 成员变量
    private Integer value;

    /**
     * 结果枚举信息
     */
    private String message;

    /**
     * 默认构造方法
     *
     * @param value 枚举值
     */
    SmsStatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    /**
     * 通过枚举<code>value</code>获得枚举
     *
     * @param value
     * @return
     */
    public static SmsStatusEnum getByValue(Integer value) {
        for (SmsStatusEnum enu : values()) {
            if (enu.getValue().equals(value)) {
                return enu;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SmsStatusEnum, {");
        sb.append("value=");
        sb.append(value);
        sb.append(", message=");
        sb.append(message);
        sb.append('}');
        return sb.toString();
    }
}
