package com.zzy.db.entity.eventdepart;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EventPunish extends Model<EventPunish> {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer eventId;

    private Integer exeuteDepartId;

    private String punishRemark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
