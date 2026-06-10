package com.zzy.db.entity.base;

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
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WeatherInfo extends Model<WeatherInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String temp;

    private String weatherIndex;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
