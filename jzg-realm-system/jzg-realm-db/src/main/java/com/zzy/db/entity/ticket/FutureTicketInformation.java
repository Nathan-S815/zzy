package com.zzy.db.entity.ticket;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 景区未来n日余票信息表
 * </p>
 *
 * @author zzy
 * @since 2020-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FutureTicketInformation extends Model<FutureTicketInformation> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * api数据Id
     */
    private Integer apiId;

    private String name;

    /**
     * api记录时间
     */
    private LocalDateTime recordDate;

    /**
     * 余票数量
     */
    private Long restNumber;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
