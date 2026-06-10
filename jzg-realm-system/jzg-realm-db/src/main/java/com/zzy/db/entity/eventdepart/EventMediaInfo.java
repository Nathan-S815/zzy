package com.zzy.db.entity.eventdepart;

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
 * @since 2020-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EventMediaInfo extends Model<EventMediaInfo> {

    private static final long serialVersionUID = 1L;

    private Integer eventId;

    private String pic1Path;

    private String pic2Path;

    private String pic3Path;

    private String videoPath;

    private String filePath;


    @Override
    protected Serializable pkVal() {
        return this.eventId;
    }

}
