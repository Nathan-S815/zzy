package com.zzy.api.service.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class ExcelReportService {

    @Data
    public static class ReportBaseHotelExcel extends BaseRowModel {
        /**
         * 酒店名称
         */
        @ExcelProperty(value = "酒店名称", index = 0)
        private String hotelName;

        /**
         * 地址
         */
        @ExcelProperty(value = "地址", index = 1)
        private String address;

        /**
         * 酒店联系电话
         */
        @ExcelProperty(value = "酒店联系电话", index = 2)
        private String hotelPhone;

        /**
         * 联系人
         */
        @ExcelProperty(value = "联系人", index = 3)
        private String contactPeople;

        /**
         * 联系人电话
         */
        @ExcelProperty(value = "联系人电话", index = 4)
        private String contactPhone;

        /**
         * 营业执照
         */
        @ExcelProperty(value = "营业执照", index = 5)
        private String businessLicense;

        /**
         * 法人
         */
        @ExcelProperty(value = "法人", index = 6)
        private String legalPerson;

        /**
         * 法人身份证
         */
        @ExcelProperty(value = "法人身份证", index = 7)
        private String legalIdcard;
    }

    @Data
    public static class ReportBaseTrafficExcel extends BaseRowModel {
        /**
         * 交通名称
         */
        @ExcelProperty(value = "交通名称", index = 0)
        private String trafficName;

        /**
         * 地址
         */
        @ExcelProperty(value = "地址", index = 1)
        private String address;

        /**
         * 交通联系电话
         */
        @ExcelProperty(value = "交通联系电话", index = 2)
        private String trafficPhone;

        /**
         * 联系人
         */
        @ExcelProperty(value = "联系人", index = 3)
        private String contactPeople;

        /**
         * 联系人电话
         */
        @ExcelProperty(value = "联系人电话", index = 4)
        private String contactPhone;

        /**
         * 营业执照
         */
        @ExcelProperty(value = "营业执照", index = 5)
        private String businessLicense;

        /**
         * 法人
         */
        @ExcelProperty(value = "法人", index = 6)
        private String legalPerson;

        /**
         * 法人身份证
         */
        @ExcelProperty(value = "法人身份证", index = 7)
        private String legalIdcard;
    }

    @Data
    public static class ReportBaseEntertainmentExcel extends BaseRowModel {
        /**
         * 娱乐名称
         */
        @ExcelProperty(value = "娱乐名称", index = 0)
        private String entertainmentName;

        /**
         * 地址
         */
        @ExcelProperty(value = "地址", index = 1)
        private String address;

        /**
         * 娱乐联系电话
         */
        @ExcelProperty(value = "娱乐联系电话", index = 2)
        private String entertainmentPhone;

        /**
         * 联系人
         */
        @ExcelProperty(value = "联系人", index = 3)
        private String contactPeople;

        /**
         * 联系人电话
         */
        @ExcelProperty(value = "联系人电话", index = 4)
        private String contactPhone;

        /**
         * 营业执照
         */
        @ExcelProperty(value = "营业执照", index = 5)
        private String businessLicense;

        /**
         * 法人
         */
        @ExcelProperty(value = "法人", index = 6)
        private String legalPerson;

        /**
         * 法人身份证
         */
        @ExcelProperty(value = "法人身份证", index = 7)
        private String legalIdcard;

    }

    @Data
    public static class ReportBaseShoppingExcel extends BaseRowModel {

        /**
         * 购物名称
         */
        @ExcelProperty(value = "购物名称", index = 0)
        private String shoppingName;

        /**
         * 地址
         */
        @ExcelProperty(value = "地址", index = 1)
        private String address;

        /**
         * 购物联系电话
         */
        @ExcelProperty(value = "购物联系电话", index = 2)
        private String shoppingPhone;

        /**
         * 联系人
         */
        @ExcelProperty(value = "联系人", index = 3)
        private String contactPeople;

        /**
         * 联系人电话
         */
        @ExcelProperty(value = "联系人电话", index = 4)
        private String contactPhone;

        /**
         * 营业执照
         */
        @ExcelProperty(value = "营业执照", index = 5)
        private String businessLicense;

        /**
         * 法人
         */
        @ExcelProperty(value = "法人", index = 6)
        private String legalPerson;

        /**
         * 法人身份证
         */
        @ExcelProperty(value = "法人身份证", index = 7)
        private String legalIdcard;
    }

    @Data
    public static class ReportBaseRestaurantExcel extends BaseRowModel {

        /**
         * 餐饮名称
         */
        @ExcelProperty(value = "餐饮名称", index = 0)
        private String restaurantName;

        /**
         * 地址
         */
        @ExcelProperty(value = "地址", index = 1)
        private String address;

        /**
         * 餐饮联系电话
         */
        @ExcelProperty(value = "餐饮联系电话", index = 2)
        private String restaurantPhone;

        /**
         * 联系人
         */
        @ExcelProperty(value = "联系人", index = 3)
        private String contactPeople;

        /**
         * 联系人电话
         */
        @ExcelProperty(value = "联系人电话", index = 4)
        private String contactPhone;

        /**
         * 营业执照
         */
        @ExcelProperty(value = "营业执照", index = 5)
        private String businessLicense;

        /**
         * 法人
         */
        @ExcelProperty(value = "法人", index = 6)
        private String legalPerson;

        /**
         * 法人身份证
         */
        @ExcelProperty(value = "法人身份证", index = 7)
        private String legalIdcard;
    }

    @Data
    public static class ReportBaseTravelExcel extends BaseRowModel {
        /**
         * 旅行社名称
         */
        @ExcelProperty(value = "旅行社名称", index = 0)
        private String travelName;

        /**
         * 地址
         */
        @ExcelProperty(value = "地址", index = 1)
        private String address;

        /**
         * 旅行社联系电话
         */
        @ExcelProperty(value = "旅行社联系电话", index = 2)
        private String travelPhone;

        /**
         * 联系人
         */
        @ExcelProperty(value = "联系人", index = 3)
        private String contactPeople;

        /**
         * 联系人电话
         */
        @ExcelProperty(value = "联系人电话", index = 4)
        private String contactPhone;

        /**
         * 营业执照
         */
        @ExcelProperty(value = "营业执照", index = 5)
        private String businessLicense;

        /**
         * 法人
         */
        @ExcelProperty(value = "法人", index = 6)
        private String legalPerson;

        /**
         * 法人身份证
         */
        @ExcelProperty(value = "法人身份证", index = 7)
        private String legalIdcard;
    }

    @Data
    public static class ReportBaseScenicExcel extends BaseRowModel {

        /**
         * 景区名称
         */
        @ExcelProperty(value = "景区名称", index = 0)
        private String scenicName;

        /**
         * 地址
         */
        @ExcelProperty(value = "地址", index = 1)
        private String address;

        /**
         * 景区联系电话
         */
        @ExcelProperty(value = "景区联系电话", index = 2)
        private String scenicPhone;

        /**
         * 联系人
         */
        @ExcelProperty(value = "联系人", index = 3)
        private String contactPeople;

        /**
         * 联系人电话
         */
        @ExcelProperty(value = "联系人电话", index = 4)
        private String contactPhone;

        /**
         * 营业执照
         */
        @ExcelProperty(value = "营业执照", index = 5)
        private String businessLicense;

        /**
         * 法人
         */
        @ExcelProperty(value = "法人", index = 6)
        private String legalPerson;

        /**
         * 法人身份证
         */
        @ExcelProperty(value = "法人身份证", index = 7)
        private String legalIdcard;

    }

    @Data
    public static class ReportBaseGuideExcel extends BaseRowModel {

        /**
         * 导游名称
         */
        @ExcelProperty(value = "导游名称", index = 0)
        private String guideName;

        /**
         * 导游编号
         */
        @ExcelProperty(value = "导游编号", index = 1)
        private String guideLicenseNumber;

        /**
         * 导游信用分
         */
        @ExcelProperty(value = "导游信用分", index = 2)
        private String credit;

        /**
         * 信用分更新时间
         */
        @ExcelProperty(value = "信用分更新时间", index = 3)
        private String updateTime;

        @ExcelProperty(value = "上报时间", index = 4)
        private String createTime;


    }
}
