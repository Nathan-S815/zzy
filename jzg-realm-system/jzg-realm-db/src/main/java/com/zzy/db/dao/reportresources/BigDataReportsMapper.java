package com.zzy.db.dao.reportresources;


import com.zzy.db.entity.eventdepart.reportresources.BigDataReports;

import java.util.List;
import java.util.Map;

public interface BigDataReportsMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int insert(BigDataReports record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(BigDataReports record);

    /**
     *
     * @mbg.generated
     */
    BigDataReports selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BigDataReports record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BigDataReports record);

    int insertBean(BigDataReports bd1);

    BigDataReports selectByUniqKey(Map<String, Object> m);

    List<Map<String, Object>> selectListByMap(Map<String, Object> m);

    Map<String, Object> selectReportById(int parseInt);

    List<Map<String, Object>> selectProInnerCity(Map<String, Object> m);

    List<Map<String, Object>> selectProvincePassenger(Map<String, Object> m);

    List<Map<String, Object>> selectCityPassenger(Map<String, Object> m);

    List<Map<String, Object>> selectCityPassenger2(Map<String, Object> m);

    List<Map<String, Object>> selectPtTouristMonitorNwblx(Map<String, Object> m);

    /**
     * 公安省内外游客占比
     * @param para
     * @return
     */
    Map<String, Object> selectProInnnerOutPercent(Map<String, Object> para);

    List<Map<String, Object>> selectNwbNum(Map<String, Object> para);

    List<Map<String, Object>> selectPtTouristMonitorNeibin(Map<String, Object> para);

    List<Map<String, Object>> selectProvincePassenger2(Map<String, Object> m);

    List<Map<String, Object>> selectProInnerCity2(Map<String, Object> m);

    List<Map<String,Object>> selectGongAnProInnnerTop5(Map<String, Object> para);
}