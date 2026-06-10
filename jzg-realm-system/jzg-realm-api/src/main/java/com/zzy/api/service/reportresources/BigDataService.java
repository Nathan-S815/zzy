package com.zzy.api.service.reportresources;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.zzy.core.utils.IOFileUtil;
import com.zzy.core.utils.JsonUtil;
import com.zzy.core.utils.NumberMathUtil;
import com.zzy.db.dao.reportresources.BigDataReportsContentMapper;
import com.zzy.db.dao.reportresources.BigDataReportsMapper;
import com.zzy.db.entity.eventdepart.reportresources.BigDataReports;
import com.zzy.db.entity.eventdepart.reportresources.BigDataReportsContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

//@Service
@Slf4j
public class BigDataService {


//    @Autowired
    private Object touristProtraitMapper;


//    @Autowired
    private Object historyPassengerAnalysisMapper;

//    @Autowired
    private Object passengerHwSourceRegionMapper;

//    @Autowired
    private BigDataReportsMapper bigDataReportsMapper;

//    @Autowired
    private BigDataReportsContentMapper bigDataReportsContentMapper;

//    @Autowired
    private Object aoneAreaPortraitChildPeriodMapper;

//    @Autowired
    private Object aonePassengerHwSourceRegionMapper;

//    @Autowired
    private Object meituanTouristConsumptionAllMapper;


//    @Autowired
    private Object aoneAreaPortraitBaseValuesMapper;


    public Map<String, Object> findBigDataReportPortraitConsume(Map<String, Object> para) {
        Map<String, Object> res = null; //touristProtraitMapper.selectBigDataReportPortraitConsume(para);
        if (res == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> m =  null; //aoneAreaPortraitChildPeriodMapper.selectChildPeriodAndStayTime(Integer.parseInt(String.valueOf(para.get("dateLog"))));
        List<Map<String, Object>> list =  null; //aoneAreaPortraitBaseValuesMapper.selectJiguangBasePortraitByTime(Integer.parseInt(String.valueOf(para.get("dateLog"))));
        res.put("childPeriodWithStayTime", m);
        res.put("jiguangPortrait", list);
        String dateLog = String.valueOf(para.get("dateLog"));
        Date st = new Date();
        Map<String, Object> m2 = new HashMap<>();
        String sub = String.valueOf(para.get("subject"));
        if (sub.equals("春节")) {
            dateLog = dateLog.substring(0, 4);
            para.put("startDate", Integer.parseInt(dateLog.concat("0125")));
            para.put("endDate", Integer.parseInt(dateLog.concat("0201")));
        } else if (sub.equals("五一")) {
            dateLog = dateLog.substring(0, 4);
            para.put("startDate", Integer.parseInt(dateLog.concat("0501")));
            para.put("endDate", Integer.parseInt(dateLog.concat("0503")));
        } else if (sub.equals("国庆")) {
            dateLog = dateLog.substring(0, 4);
            para.put("startDate", Integer.parseInt(dateLog.concat("1001")));
            para.put("endDate", Integer.parseInt(dateLog.concat("1007")));
        } else {
            if (dateLog.length() == 6) {
                st = DateUtil.parse(dateLog, "yyyyMM");
                para.put("startDate", Integer.parseInt(DateUtil.format(DateUtil.beginOfMonth(st), "yyyyMMdd")));
                para.put("endDate", Integer.parseInt(DateUtil.format(DateUtil.endOfMonth(st), "yyyyMMdd")));
            } else {
                para.put("startDate", Integer.parseInt(DateUtil.format(DateUtil.beginOfYear(st), "yyyyMMdd")));
                para.put("endDate", Integer.parseInt(DateUtil.format(DateUtil.endOfYear(st), "yyyyMMdd")));
            }
        }
        List<Map<String, Object>> r =  null; //meituanTouristConsumptionAllMapper.selectConsumeAnalysisByTime(para);
        res.put("consumeptionLine", r);
        return res;
    }

    public Map<String, Object> findBigDataReportPlaceNum() {
        return  null; //ptBaseScenicMapper.selectBigDataReportPlaceNum();
    }


    /**
     * @param m: key(no,dateLog)
     * @return
     */
    public List<Map<String, Object>> findBigDataReportScenicPassenge(Map<String, Object> m) {
        return  null; //historyPassengerAnalysisMapper.selectBigDataReportScenicPassenger(m);
    }

    public Map<String, Object> findBigDataReportProvinceCity(Map<String, Object> para) {
        return  null; //touristProtraitMapper.selectBigDataReportProvinceCity(para);
    }

    public int createBigDataReport(Map<String, String> m) {
        BigDataReports bd1 = new BigDataReports();
        bd1.setPlaceNum(m.get("placeNums"));
        bd1.setReportName(m.get("reportName"));
        bd1.setCreateTime(new Date());
        bd1.setScenicPassenger(m.get("scenicPassenger"));
        bd1.setReportCycle(m.get("queryDate"));
        bd1.setReportSubject(getSubJectNo(m.get("reportSubject")));
        if (bd1.getReportSubject() == null) {
            return 0;
        }
        bd1.setCreateUser("-");
        bd1.setReportState(0);
        bd1.setIsDelete(0);
        try {
            bigDataReportsMapper.insertBean(bd1);
            try {
                if (bd1.getId() != null && bd1.getId() > 0) {
                    BigDataReportsContent bd2 = new BigDataReportsContent();
                    bd2.setPtBigDataReportsId(bd1.getId());
                    StringBuilder sb = new StringBuilder("province=").append(m.get("province")).append("&")
                            .append("city=").append(m.get("city")).append("&")
                            .append("portraitConsume=").append(m.get("portraitConsume")).append("&")
                            .append("commentDistribute=").append(m.get("commentDistribute")).append("&")
                            .append("areaPassenger=").append(m.get("areaPassenger")).append("&")
                            .append("gongAn=").append(m.get("gongAn")).append("&");
                    bd2.setReportContentPart(sb.toString());
                    return bigDataReportsContentMapper.insert(bd2);
                }
            } catch (Exception e) {
                e.printStackTrace();
                bigDataReportsMapper.deleteByPrimaryKey(bd1.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return 0;
    }


    public static Integer getSubJectNo(String reportSubject) {
        if (reportSubject.startsWith("年")) {
            return 1;
        } else if (reportSubject.startsWith("月")) {
            return 2;
        } else if (reportSubject.startsWith("春")) {
            return 3;
        } else if (reportSubject.startsWith("五")) {
            return 4;
        } else if (reportSubject.startsWith("国")) {
            return 5;
        }
        return null;
    }


    public BigDataReports findBigDataReportsByUniqKey(Map<String, Object> m) {
        return bigDataReportsMapper.selectByUniqKey(m);
    }

    public PageInfo<Map<String, Object>> findBigDataList(int pageNo, int pageSize, Map<String, Object> m) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = bigDataReportsMapper.selectListByMap(m);
        return new PageInfo<>(list);
    }

    /**
     * 日期范围内景区客流量统计
     *
     * @param m
     * @return
     */
    public List<Map<String, Object>> findScenicVolumeStatistics(Map<String, Object> m) {
        return  null; //historyPassengerAnalysisMapper.selectScenicVolumeStatistics(m);
    }

    public JSONObject findBigDataReportCommentDistribute(String date) {
        String res = HttpUtil.get("http://<PUBLIC_HOST>:8083/evaluate/getCommentSourceAttribute?areaName=%E6%99%AE%E9%99%80");
        JSONObject jo = JsonUtil.strToJSONObject(res).getJSONObject("data");
        if (date.length() == 4) {
            date = date + "-01";
        }
        res = HttpUtil.get("http://<PUBLIC_HOST>:8083/evaluate/getNewCommentNum?areaName=%E6%99%AE%E9%99%80&queryTime=" + date);
        jo.put("newCommentsNum", JsonUtil.strToJSONObject(res).getJSONObject("data").getInteger("newCommentNum"));
        res = HttpUtil.get("http://<PUBLIC_HOST>:8083/evaluate/getHighCommentNum?areaName=%E6%99%AE%E9%99%80");
        int high = JsonUtil.strToJSONObject(res).getJSONObject("data").getInteger("highCommentNum");
        int all = jo.getInteger("allCount");
        jo.put("highRatio", NumberMathUtil.round(high * 1.0 / all, 4));
        return jo;
    }

    public Map<String, Object> findBigDataReportById(String reportId) {
        return bigDataReportsMapper.selectReportById(Integer.parseInt(reportId));
    }

    public Map<String, Object> findProvinceCityPassenger(int date) {
        Date time = new Date();
        String d = String.valueOf(date);
        Map<String, Object> m = new HashMap<>();
        if (d.length() == 6) {
            time = DateUtil.parse(d, "yyyyMM");
            m.put("startTime", Integer.parseInt(DateUtil.format(DateUtil.beginOfMonth(time), "yyyyMMdd")));
            m.put("endTime", Integer.parseInt(DateUtil.format(DateUtil.endOfMonth(time), "yyyyMMdd")));
        } else {
            time = DateUtil.parse(d, "yyyy");
            m.put("startTime", Integer.parseInt(DateUtil.format(DateUtil.beginOfYear(time), "yyyyMMdd")));
            m.put("endTime", Integer.parseInt(DateUtil.format(DateUtil.endOfYear(time), "yyyyMMdd")));
        }
        m.put("provinceName", "浙江省");
        List<Map<String, Object>> proInnerCity = bigDataReportsMapper.selectProInnerCity(m);
        List<Map<String, Object>> proPass = bigDataReportsMapper.selectProvincePassenger(m);
        List<Map<String, Object>> cityPass = bigDataReportsMapper.selectCityPassenger(m);
        m = new HashMap<>();
        m.put("proInnerCity", proInnerCity);
        m.put("proPass", proPass);
        m.put("cityPass", cityPass);
        return m;
    }

    public int deleteReportById(String id) {
        int pk = Integer.parseInt(id);
        BigDataReports b = new BigDataReports();
        b.setId(pk);
        b.setUpdateTime(new Date());
        b.setIsDelete(1);
        return bigDataReportsMapper.updateByPrimaryKeySelective(b);
    }

    public int PubReportById(String id, String pubType) {
        BigDataReports b = new BigDataReports();
        b.setId(Integer.parseInt(id));
        b.setReportState(Integer.parseInt(pubType));
        b.setUpdateTime(new Date());
        return bigDataReportsMapper.updateByPrimaryKeySelective(b);
    }

    public static String[] getPdfParamOrderArray() {
        String[] ss = {"wenlvResourcesOverview", "scenicPassengers",
                "innerProvinceHotSpot", "outProvinceHotSpot",
                "cityPassengerRank","inOutProvincePercent",
                "foreignerTouristTop5","outProvinceTop5","nwbPassenger",
                "carFlowStatistic", "cityCarFlowProportion",
                "touristBasicPortrait", "childPeriodDistribute",
                "playTime", "consumptionTouristPortrait",
                "touristPlaceProportion", "touristConsumption",
                "scenicConsumption", "cateringConsumption",
                "hotelConsumption", "internetComment"
        };
        return ss;
    }

    public void exportPdf(JSONObject para, HttpServletResponse response) throws IOException, DocumentException {
        List<Paragraph> list = new ArrayList<>();
        Paragraph paragraph = new Paragraph();
        String tmp = null;
        Image img = null;
        MultipartFile file = null;
        int c = 0;
        String[] keys = getPdfParamOrderArray();
        for(int i=0;i<keys.length;i++){
            tmp = para.getString(keys[i]);
            if (StrUtil.isBlankOrUndefined(tmp)) {
                log.info("pdf空值,对应key:{}",keys[i]);
                continue;
            }
            if (tmp.startsWith("data:image")) {
                file = IOFileUtil.base64ToMultipart(tmp);
                img = Image.getInstance(file.getBytes());
                img.setAlignment(Image.MIDDLE);
                img.scalePercent(getPercent2(img.getHeight(), img.getWidth()));
                paragraph.add(img);
            }else{
                paragraph.add(tmp);
            }
        }
        list.add(paragraph);
        String pathFullName = "大数据报告" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + ".pdf";
        File f = IOFileUtil.createPdf(list, pathFullName);
        exportResp(f, pathFullName, response);
    }

    public int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }


    private void exportResp(File f, String pathFullName, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/pdf;charset=UTF-8"); //设置字符集和文件后缀名
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(pathFullName, "UTF-8")); // 设置文件名称
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(f);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("Download  successfully!");
        } catch (Exception e) {
            System.out.println("Download  failed!");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Map<String, Object> findBigDataReportPoliceData(Map<String, Object> para) {
        String res = String.valueOf(para.get("dateLog"));
        int dateLog = Integer.parseInt(res);
        DateTime dt = DateUtil.parse(res,"yyyyMM");
        para.put("startTime", DateUtil.beginOfMonth(dt));
        para.put("endTime", DateUtil.endOfMonth(dt));
        para.put("nwblx", "外宾");
        List<Map<String,Object>> l = bigDataReportsMapper.selectPtTouristMonitorNwblx(para);
        Map<String,Object>  m = new HashMap<>();
        m.put("foreignerTouristTop5", l);
        para.put("nwblx", "内宾");
        List<Map<String,Object>> l2  = bigDataReportsMapper.selectPtTouristMonitorNeibin(para);
        for (Map<String, Object> ob : l2) {
            if(ob.get("areaName").equals("黑龙")){
                ob.put("areaName", "黑龙江");
                break;
            }
        }
        m.put("outProvinceTouristTop5", l2);
        List<Map<String,Object>> proNum = bigDataReportsMapper.selectGongAnProInnnerTop5(para);
        m.put("innerProvinceTpo5", proNum);
        List<Map<String, Object>> nwbNum = bigDataReportsMapper.selectNwbNum(para);
        m.put("nwbNum", nwbNum);
        return m;
    }
}
