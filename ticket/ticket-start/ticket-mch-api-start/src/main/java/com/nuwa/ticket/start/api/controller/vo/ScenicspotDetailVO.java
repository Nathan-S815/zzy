package com.nuwa.ticket.start.api.controller.vo;

import com.nuwa.ticket.start.api.controller.dto.ScenicspotBaseInfoDTO;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotLabelDTO;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotMaterialDTO;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotTypeDTO;
import lombok.Data;

import java.util.List;

/**
 * @author hy
 */
@Data
public class ScenicspotDetailVO {
    private ScenicspotBaseInfoDTO baseInfo;
    private List<ScenicspotMaterialDTO> materials;
}
