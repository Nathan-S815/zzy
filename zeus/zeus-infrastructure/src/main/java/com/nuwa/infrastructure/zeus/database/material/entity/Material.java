package com.nuwa.infrastructure.zeus.database.material.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Material对象")
public class Material extends Model<Material> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户ID")
    private Long mchId;

    @ApiModelProperty("资源分类ID")
    private Long typeId;

    @ApiModelProperty("所属资源ID")
    private Long targetId;

    @ApiModelProperty("文件类型  1:文章 2:图片 3:视频,4其他")
    private Integer fileType;

    @ApiModelProperty("文件大小")
    private Long fileSize;

    @ApiModelProperty("文件key名称")
    private String ossKey;

    @ApiModelProperty("访问地址url")
    private String url;

    @ApiModelProperty("视频封面图片")
    private String coverImgUrl;

    @ApiModelProperty("封面图片id")
    private String coverImgId;

    @ApiModelProperty("文件名称")
    private String realName;

    @ApiModelProperty("oss渠道类型 [alioss,minio]")
    private String ossChannel;

    @ApiModelProperty("使用次数")
    private Long usedTimes;

    @ApiModelProperty("资源类型")
    private Integer targetType;

    @ApiModelProperty("扩展参数")
    private String extend;

    @ApiModelProperty("oss地址")
    private String endPoint;

    @ApiModelProperty("桶名称")
    private String bucketName;

    @ApiModelProperty("状态 0:等待审核 1:审核通过 2:审核失败 3:删除")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String TYPE_ID = "type_id";

    public static final String TARGET_ID = "target_id";

    public static final String FILE_TYPE = "file_type";

    public static final String FILE_SIZE = "file_size";

    public static final String OSS_KEY = "oss_key";

    public static final String URL = "url";

    public static final String COVER_IMG_URL = "cover_img_url";

    public static final String COVER_IMG_ID = "cover_img_id";

    public static final String REAL_NAME = "real_name";

    public static final String OSS_CHANNEL = "oss_channel";

    public static final String USED_TIMES = "used_times";

    public static final String TARGET_TYPE = "target_type";

    public static final String EXTEND = "extend";

    public static final String END_POINT = "end_point";

    public static final String BUCKET_NAME = "bucket_name";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

}
