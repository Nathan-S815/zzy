package com.zzy.client.common;

public class JzgApiConstant {

    public enum ServiceCodeEnum {
        /**
         * 停⻋场剩余⻋位(历史)
         */
        get_remaining_space_h("MWEIAJ5C"),

        /**
         * 停⻋场信息
         */
        get_park_info("HXXNK4JD"),

        /**
         * ⻋辆出场纪录
         */
        get_out_car("Z2Q7V6TO"),

        /**
         * ⻋辆⼊场纪录
         */
        get_enter_car("MF5N2VYG"),

        /**
         * 停⻋场剩余⻋位
         */
        get_remaining_space("HY7JNGIK"),


        /**
         * 景区当日入园人数表
         */
        scenic_enter_people("LUB67RAW"),

        /**
         * 景区未来n日余票信息表
         */
        future_ticket_information("VQX4IGS6"),


        /**
         * 景区当日团体/个人订票信息表
         */
        booking_ticket_information("RHUVI35V"),

        /**
         * 车辆静态信息表
         */
        car_info("NCW6THQP"),

        /**
         * 车辆报警信息表
         */
        car_alarm("3VIB4RV4"),

        /**
         * 驾驶员信息表
         */
        driver("Y64W6LDX"),

        /**
         * 车辆gps信息表
         */
        car_gps("1CDNG3CF"),




        ;
        private String serviceCode;

        ServiceCodeEnum(String code) {
            this.serviceCode = code;
        }

        public String getServiceCode() {
            return serviceCode;
        }
    }




    private String client_id;
    private String secret;
    private String serviceCode;

}
