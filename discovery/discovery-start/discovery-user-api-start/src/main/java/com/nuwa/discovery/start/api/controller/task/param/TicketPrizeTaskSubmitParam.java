package com.nuwa.discovery.start.api.controller.task.param;

import com.nuwa.discovery.start.api.controller.dto.TicketInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "门票任务认领参数")
public class TicketPrizeTaskSubmitParam {

    @NotBlank(message = "游玩日期不能为空")
    private String visitDate;

    @NotBlank(message = "联系人不能为空")
    private String linkName;

    @NotBlank(message = "联系人手机号不能为空")
    private String linkMobile;

    @NotBlank(message = "联系人证件号不能为空")
    private String linkIdCard;

    @NotBlank(message = "备注信息")
    private String introduceText;

    private Boolean togetherPeople;

    private List<TicketInfoDTO> ticketList;
}
