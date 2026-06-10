package com.nuwa.infrastructure.ticket.service.geo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 景区坐标数据
 *
 * @author hy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScenicGpsInfo {

    /**
     * 景区id
     */
    private String id;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;
}
