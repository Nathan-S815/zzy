package com.nuwa.infrastructure.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.infrastructure.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * POI分页查询VO
 *
 * @author nanHuang @南皇
 * @version com.nuwa.infrastructure.hotel.database.hotelPoi.vo:HotelPoiPageVO.java,v1.0.0 2022-08-01 14:39:53
 * nanHuang Exp $
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户分页查询对象")
public class AttractUserPageVO extends Model<AttractUserPageVO> {
    private static final long    serialVersionUID = 1L;
    @ApiModelProperty("用户id")
    private              Long    userId;
    @ApiModelProperty("用户名")
    private              String  username;
    @ApiModelProperty("邮箱")
    private              String  email;
    @ApiModelProperty("商户名称（酒店、景区、旅行社）")
    private              String  mchName;
    @ApiModelProperty("省份名")
    private              String  province;
    @ApiModelProperty("省份编号")
    private              String  provinceId;
    @ApiModelProperty("城市名")
    private              String  city;
    @ApiModelProperty("城市编码")
    private              String  cityId;
    @ApiModelProperty("地区名")
    private              String  area;
    @ApiModelProperty("地区编码")
    private              String  areaId;
    @ApiModelProperty("详细地址")
    private              String  address;
    @ApiModelProperty("电话")
    private              String  tel;
    @ApiModelProperty("联系人姓名")
    private              String  linkName;
    @ApiModelProperty("联系人电话")
    private              String  linkPhone;
    @ApiModelProperty("营业执照图片id")
    @JsonSerialize(using = MaterialJson.class)
    private              String  licenseImageId;
    @ApiModelProperty("统一社会信用代码")
    private              String  socialCreditCode;
    @ApiModelProperty("账号类型 0-文旅局 1-景区 2-酒店 3-旅行社")
    private              Integer accountType;
    @ApiModelProperty("审核状态 0-未审核 1-审核通过 2-审核拒绝  3-禁用")
    private              Integer reviewStatus;
    @ApiModelProperty("申请时间")
    private              Date    createTime;
    public static AttractUserPageVO toVO(AttractUser attractUser) {
        AttractUserPageVO vo = new AttractUserPageVO();

        BeanUtils.copyProperties(attractUser, vo);

        return vo;
    }
}
