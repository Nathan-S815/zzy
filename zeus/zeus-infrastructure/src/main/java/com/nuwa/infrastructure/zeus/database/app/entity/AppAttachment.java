package com.nuwa.infrastructure.zeus.database.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@ApiModel(value = "AppAttachment对象")
public class AppAttachment extends Model<AppAttachment> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("所属应用id")
    private Long appId;

    @ApiModelProperty("附件名称")
    private String attachmentName;

    @ApiModelProperty("附件文件id")
    private Long attachmentFileId;


    public static final String ID = "id";

    public static final String APP_ID = "app_id";

    public static final String ATTACHMENT_NAME = "attachment_name";

    public static final String ATTACHMENT_FILE_ID = "attachment_file_id";

}
