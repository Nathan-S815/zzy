package com.zzy.api.service.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.zzy.core.utils.TimeDateUtil;
import com.zzy.db.entity.base.BaseGuide;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExcelService {

    @Data
    public static class ScenicExcelModel extends BaseRowModel {
        /**
         * 名称
         */
        @ExcelProperty(value = "名称", index = 0)
        private String scenicName;

        /**
         * 等级
         */
        @ExcelProperty(value = "等级", index = 1)
        private String scenicLevel;

        /**
         * 统一信用代码
         */
        @ExcelProperty(value = "统一信用代码", index = 2)
        private String creditCode;

        /**
         * 行政区划
         */
        @ExcelProperty(value = "行政区划", index =3)
        private String areaCode;

        /**
         * 纬度
         */
        @ExcelProperty(value = "纬度", index = 4)
        private String lat;

        /**
         * 经度
         */
        @ExcelProperty(value = "经度", index = 5)
        private String lng;

        /**
         * 邮编
         */
        @ExcelProperty(value = "邮编", index = 6)
        private String zipCode;

        /**
         * 类型
         */
        @ExcelProperty(value = "类型", index = 7)
        private String siteType;

        /**
         * 宜游月份
         */
        @ExcelProperty(value = "宜游月份", index = 8)
        private String appropriateMonth;
    }

    @Data
    public static class BaseGuideExcelModel extends BaseRowModel{
        /**
         * 导游姓名
         */
        @ExcelProperty(value = "导游姓名", index = 0)
        private String guideName;

        /**
         * 性别
         */
        @ExcelProperty(value = "性别", index = 1)
        private Integer sex;

        /**
         * 身份证号
         */
        @ExcelProperty(value = "身份证号", index = 2)
        private String idNumber;

        /**
         * 出生年月
         */
        @ExcelProperty(value = "出生年月", index = 3)
        private String birthday;

        /**
         * 联系电话
         */
        @ExcelProperty(value = "联系电话", index = 4)
        private String tel;

        /**
         * 民族
         */
        @ExcelProperty(value = "民族", index = 5)
        private String nation;

        /**
         * 学历
         */
        @ExcelProperty(value = "学历", index = 6)
        private Integer educational;

        /**
         * 专业
         */
        @ExcelProperty(value = "专业", index = 7)
        private String major;

        /**
         * 毕业院校
         */
        @ExcelProperty(value = "毕业院校", index = 8)
        private String graduationUniversity;

        /**
         * 所属旅行社
         */
        @ExcelProperty(value = "所属旅行社", index = 9)
        private String subTravel;

        /**
         * 导游证书
         */
        @ExcelProperty(value = "导游证书", index = 10)
        private String guideCertificate;

        /**
         * 等级证号
         */
        @ExcelProperty(value = "等级证号", index = 11)
        private Integer gradeNum;

        /**
         * 导游等级
         */
        @ExcelProperty(value = "导游等级", index = 12)
        private String guideGrade;

        /**
         * 语言
         */
        @ExcelProperty(value = "语言", index = 13)
        private String languageGrade;

        /**
         * 资格证书
         */
        @ExcelProperty(value = "资格证书", index = 14)
        private String seniorityCertificate;

        /**
         * 工作性质 0-全职 1-兼职
         */
        @ExcelProperty(value = "工作性质", index = 15)
        private Integer natureWork;

        /**
         * 注册机构名称
         */
        @ExcelProperty(value = "注册机构名称", index = 16)
        private String registeOrganName;

    }


    @Data
    public static class BaseHotelExcel extends BaseRowModel{
        /**
         * 名称
         */
        @ExcelProperty(value = "名称", index = 0)
        private String hotelName;

        /**
         * 等级
         */
        @ExcelProperty(value = "等级", index = 1)
        private String hotelLevel;

        /**
         * 统一信用代码
         */
        @ExcelProperty(value = "统一信用代码", index = 2)
        private String creditCode;

        /**
         * 行政区划
         */
        @ExcelProperty(value = "行政区划", index = 3)
        private String areaCode;

        /**
         * 纬度
         */
        @ExcelProperty(value = "纬度", index = 4)
        private String lat;

        /**
         * 经度
         */
        @ExcelProperty(value = "经度", index = 5)
        private String lng;

        /**
         * 邮编
         */
        @ExcelProperty(value = "邮编", index = 6)
        private String zipCode;

        /**
         * 类型
         */
        @ExcelProperty(value = "类型", index = 7)
        private String siteType;

        /**
         * 咨询电话
         */
        @ExcelProperty(value = "咨询电话", index = 8)
        private String zxPhone;

        /**
         * 投诉电话
         */
        @ExcelProperty(value = "投诉电话", index = 9)
        private String tsPhone;

        /**
         * 房间总数
         */
        @ExcelProperty(value = "房间总数", index = 10)
        private Integer roomCount;

        /**
         * 床位数量
         */
        @ExcelProperty(value = "床位数量", index = 11)
        private Integer bedCount;

        /**
         * 停车位数量
         */
        @ExcelProperty(value = "停车位数量", index = 12)
        private Integer carParkNum;

    }

    @Data
    public static class BaseRecreationExcel extends BaseRowModel{
        /**
         * 休闲娱乐名称
         */
        @ExcelProperty(value = "名称", index = 0)
        private String recreationName;

        /**
         * 等级
         */
        @ExcelProperty(value = "等级", index = 1)
        private String recreationLevel;

        /**
         * 统一信用代码
         */
        @ExcelProperty(value = "统一信用代码", index = 2)
        private String creditCode;

        /**
         * 行政区划
         */
        @ExcelProperty(value = "行政区划", index = 3)
        private String areaCode;

        /**
         * 纬度
         */
        @ExcelProperty(value = "纬度", index = 4)
        private String lat;

        /**
         * 经度
         */
        @ExcelProperty(value = "经度", index = 5)
        private String lng;

        /**
         * 邮编
         */
        @ExcelProperty(value = "邮编", index = 6)
        private String zipCode;

        /**
         * 类型
         */
        @ExcelProperty(value = "类型", index = 7)
        private String siteType;

        /**
         * 咨询电话
         */
        @ExcelProperty(value = "咨询电话", index = 8)
        private String zxPhone;

        /**
         * 投诉电话
         */
        @ExcelProperty(value = "投诉电话", index = 9)
        private String tsPhone;

        /**
         * 许可证号
         */
        @ExcelProperty(value = "许可证号", index = 10)
        private String licenseNo;

        /**
         * 法人姓名
         */
        @ExcelProperty(value = "法人姓名", index =11)
        private String legalPerson;

        /**
         * 出资人信息
         */
        @ExcelProperty(value = "出资人信息", index = 12)
        private String contributor;
    }

    @Data
    public static class BaseRestaurantExcel extends BaseRowModel{

        /**
         * 餐厅名称
         */
        @ExcelProperty(value = "名称", index = 0)
        private String restaurantName;

        /**
         * 等级
         */
        @ExcelProperty(value = "等级", index = 1)
        private String restaurantLevel;

        /**
         * 统一信用代码
         */
        @ExcelProperty(value = "统一信用代码", index = 2)
        private String creditCode;

        /**
         * 行政区划
         */
        @ExcelProperty(value = "行政区划", index = 3)
        private String areaCode;

        /**
         * 纬度
         */
        @ExcelProperty(value = "纬度", index = 4)
        private String lat;

        /**
         * 经度
         */
        @ExcelProperty(value = "经度", index = 5)
        private String lng;

        /**
         * 邮编
         */
        @ExcelProperty(value = "邮编", index = 6)
        private String zipCode;

        /**
         * 类型
         */
        @ExcelProperty(value = "类型", index = 7)
        private String siteType;

        /**
         * 咨询电话
         */
        @ExcelProperty(value = "咨询电话", index = 8)
        private String zxPhone;

        /**
         * 投诉电话
         */
        @ExcelProperty(value = "投诉电话", index = 9)
        private String tsPhone;

        /**
         * 包间数
         */
        @ExcelProperty(value = "包间数", index = 10)
        private String roomNum;

        /**
         * 停车位数量
         */
        @ExcelProperty(value = "停车位数量", index = 11)
        private Integer carParkNum;

        /**
         * 营业时间
         */
        @ExcelProperty(value = "营业时间", index = 12)
        private String businessTime;
    }
    @Data
    public static class BaseShoppingExcel extends BaseRowModel{
        /**
         * 购物场所名称
         */
        @ExcelProperty(value = "名称", index = 0)
        private String shoppingName;

        /**
         * 等级
         */
        @ExcelProperty(value = "等级", index = 1)
        private String shoppingLevel;

        /**
         * 统一信用代码
         */
        @ExcelProperty(value = "统一信用代码", index = 2)
        private String creditCode;

        /**
         * 行政区划
         */
        @ExcelProperty(value = "行政区划", index = 3)
        private String areaCode;

        /**
         * 纬度
         */
        @ExcelProperty(value = "纬度", index = 4)
        private String lat;

        /**
         * 经度
         */
        @ExcelProperty(value = "经度", index = 5)
        private String lng;

        /**
         * 邮编
         */
        @ExcelProperty(value = "邮编", index = 6)
        private String zipCode;

        /**
         * 类型
         */
        @ExcelProperty(value = "类型", index = 7)
        private String siteType;

        /**
         * 咨询电话
         */
        @ExcelProperty(value = "咨询电话", index = 8)
        private String zxPhone;

        /**
         * 投诉电话
         */
        @ExcelProperty(value = "投诉电话", index = 9)
        private String tsPhone;

        /**
         * 许可证号
         */
        @ExcelProperty(value = "许可证号", index = 10)
        private String licenseNo;

        /**
         * 法人姓名
         */
        @ExcelProperty(value = "法人姓名", index = 11)
        private String legalPerson;

        /**
         * 出资人信息
         */
        @ExcelProperty(value = "出资人信息", index = 12)
        private String contributor;
    }

    @Data
    public static class BaseTravelExcel extends BaseRowModel{
        /**
         * 旅行社名称
         */
        @ExcelProperty(value = "名称", index = 0)
        private String travelName;

        /**
         * 等级
         */
        @ExcelProperty(value = "等级", index = 1)
        private String travelLevel;

        /**
         * 统一信用代码
         */
        @ExcelProperty(value = "统一信用代码", index = 2)
        private String creditCode;

        /**
         * 行政区划
         */
        @ExcelProperty(value = "行政区划", index = 3)
        private String areaCode;

        /**
         * 纬度
         */
        @ExcelProperty(value = "名称", index = 4)
        private String lat;

        /**
         * 经度
         */
        @ExcelProperty(value = "经度", index = 5)
        private String lng;

        /**
         * 邮编
         */
        @ExcelProperty(value = "邮编", index = 6)
        private String zipCode;

        /**
         * 类型
         */
        @ExcelProperty(value = "名称", index = 7)
        private String siteType;

        /**
         * 咨询电话
         */
        @ExcelProperty(value = "咨询电话", index = 8)
        private String zxPhone;

        /**
         * 投诉电话
         */
        @ExcelProperty(value = "投诉电话", index = 9)
        private String tsPhone;

        /**
         * 许可证号
         */
        @ExcelProperty(value = "许可证号", index = 10)
        private String licenseNo;

        /**
         * 法人姓名
         */
        @ExcelProperty(value = "法人姓名", index = 11)
        private String legalPerson;

        /**
         * 出资人信息
         */
        @ExcelProperty(value = "出资人信息", index = 12)
        private String contributor;
    }

    @Data
    public static class TouristDistribution extends BaseRowModel {
        /**
         * 景点名称
         */
        @ExcelProperty(value = "景点名称", index = 0)
        private String scenicName;

        /**
         * 人数
         */
        @ExcelProperty(value = "人数", index = 1)
        private String peopleNum;

        /**
         * 游客占比
         */
        @ExcelProperty(value = "游客占比", index = 2)
        private String peopleRate;

    }
    @Data
    public static class ScenicReport extends BaseRowModel{
        /**
         * 景区名
         */
        @ExcelProperty(value = "景区名", index = 0)
        private String scenicName;

        /**
         * 上报时间
         */
        @ExcelProperty(value = "上报时间", index = 1)
        private String reportTime;

        /**
         * 散客人数
         */
        @ExcelProperty(value = "散客人数", index = 2)
        private String individualPeopleNum;

        /**
         * 餐饮服务保障人数
         */
        @ExcelProperty(value = "餐饮服务保障人数", index = 3)
        private String restaurantPeopleNum;

        /**
         * 总人数
         */
        @ExcelProperty(value = "总人数", index = 4)
        private String allPeopleNum;
    }

    @Data
    public static class HotelReport extends BaseRowModel{
        /**
         * 酒店名
         */
        @ExcelProperty(value = "酒店名", index = 0)
        private String hotelName;

        /**
         * 上报时间
         */
        @ExcelProperty(value = "上报时间", index = 1)
        private String reportTime;

        /**
         * 接待人数
         */
        @ExcelProperty(value = "接待人数", index = 2)
        private String allPeopleNum;

        /**
         * 营业额
         */
        @ExcelProperty(value = "营业额", index = 3)
        private String income;

        /**
         * 空房间数
         */
        @ExcelProperty(value = "空房间数", index = 4)
        private String emptyRoom;

    }

    @Data
    public static class TravelReport extends BaseRowModel{
        /**
         * 旅行社名
         */
        @ExcelProperty(value = "旅行社名", index = 0)
        private String travelName;

        /**
         * 上报时间
         */
        @ExcelProperty(value = "上报时间", index = 1)
        private String reportTime;

        /**
         * 路线
         */
        @ExcelProperty(value = "路线", index = 2)
        private String line;

        /**
         * 团队人数
         */
        @ExcelProperty(value = "团队人数", index = 3)
        private String peopleNum;

    }

    @Data
    public static class OtherReport extends BaseRowModel{
        /**
         * 名称
         */
        @ExcelProperty(value = "名称", index = 0)
        private String name;

        /**
         * 上报时间
         */
        @ExcelProperty(value = "上报时间", index = 1)
        private String reportTime;

        /**
         * 营业额
         */
        @ExcelProperty(value = "营业额", index = 2)
        private String income;

    }

    @Data
    public static class TravelLineReport extends BaseRowModel{
        /**
         * 路线
         */
        @ExcelProperty(value = "路线", index = 0)
        private String line;

        /**
         * 人数
         */
        @ExcelProperty(value = "人数", index = 1)
        private String peopleNum;

        /**
         * 营业额
         */
        @ExcelProperty(value = "占比", index = 2)
        private String rate;

    }


}
