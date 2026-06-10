package com.nuwa.infrastructure.ticket.database.complaint.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户投诉
 *
 * @author huyonghack@163.com
 * @since 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Complaint extends Model<Complaint> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户ID
     */
    private Long mchId;

    /**
     * AppID
     */
    private Long appId;

    /**
     * 投诉对象
     */
    private String target;

    /**
     * 地址
     */
    private String address;

    /**
     * 投诉内容
     */
    private String complaintContent;

    /**
     * 图片(多个逗号分隔)
     */
    @JsonSerialize(using = MaterialJson.class)
    private String pic;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 投诉人电话
     */
    private String tel;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 处理状态(101未处理 102已处理)Q_eq
     */
    private Integer auditStatus;

    /**
     * 处理结果(101调解成功 102调解失败 103不予受理 104和解 105其他)IMQ_eq
     */
    private String auditResult;

    /**
     * 处理时间IM
     */
    private Date auditTime;

    /**
     * 处理人IM
     */
    private String auditUserName;

    /**
     * 处理人IDIM
     */
    private String auditUserId;

    /**
     * 处理备注IM
     */
    private String auditRemark;

    /**
     * 创建时间IM
     */
    private Date createTime;

    /**
     * 更新时间IM
     */
    private Date updateTime;

    /**
     * 删除标志[1删除;0正常]IM
     */
    private Integer deleteFlag;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String APP_ID = "app_id";

    public static final String TARGET = "target";

    public static final String ADDRESS = "address";

    public static final String COMPLAINT_CONTENT = "complaint_content";

    public static final String PIC = "pic";

    public static final String CONTACT = "contact";

    public static final String TEL = "tel";

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String AUDIT_STATUS = "audit_status";

    public static final String AUDIT_RESULT = "audit_result";

    public static final String AUDIT_TIME = "audit_time";

    public static final String AUDIT_USER_NAME = "audit_user_name";

    public static final String AUDIT_USER_ID = "audit_user_id";

    public static final String AUDIT_REMARK = "audit_remark";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETE_FLAG = "delete_flag";

}
