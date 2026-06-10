package com.nuwa.hotel.start.server.constants;

public enum NoticeMessageTypeEnum {
    VALID_CODE(1, "【中智游】您的验证码：%s，5分钟有效，请勿把验证码告知他人。") ,; //验证码

    // 成员变量
    private Integer value;

    /**
     * 结果枚举信息
     */
    private String message;

    NoticeMessageTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    /**
     * 通过枚举<code>value</code>获得枚举
     *
     * @param value
     * @return
     */
    public static NoticeMessageTypeEnum getByValue(Integer value) {
        for (NoticeMessageTypeEnum enu : values()) {
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
        return "SendSmsEnum{" +
                "value=" + value +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
