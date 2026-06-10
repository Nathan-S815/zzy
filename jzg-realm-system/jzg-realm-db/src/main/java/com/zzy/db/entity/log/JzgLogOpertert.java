package com.zzy.db.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zzy
 * @since 2020-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JzgLogOpertert extends Model<JzgLogOpertert> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String openUser;

    private String logType;

    private String operType;

    private String logPlatfrom;

    private String operContent;

    private String remoteIp;

    private String requestMethod;

    private String requestParam;

    private String consumeTime;

    private String responseTesult;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
