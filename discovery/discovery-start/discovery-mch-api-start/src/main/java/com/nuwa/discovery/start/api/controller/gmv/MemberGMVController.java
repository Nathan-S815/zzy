package com.nuwa.discovery.start.api.controller.gmv;


import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.discovery.common.exception.FileUploadException;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberGmvRecordBase;
import com.nuwa.infrastructure.discovery.database.member.entity.MemberGmvRecordDouyin;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
import com.nuwa.infrastructure.discovery.database.user.service.MemberAccountBindService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberGMVService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberGmvRecordBaseService;
import com.nuwa.infrastructure.discovery.enums.ErrorEnum;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping({"/gmv"})
@Api(tags = {"GMV相关"})
public class MemberGMVController {
    @Autowired
    private MemberGMVService memberGMVService;

    @Autowired
    private MemberAccountBindService memberAccountBindService;

    @Autowired
    private MemberGmvRecordBaseService memberGmvRecordBaseService;

    @ApiOperation("导入达人GMV数据")
    @PostMapping({"/import"})
    public SingleResponse<?> importMemberGVMRecord(@RequestParam("file") MultipartFile file) {
        List<MemberGmvRecordDouyin> memberGmvRecordDouyinList;
        try {
            List<Map<String, Object>> dataList = readerExcel(file);
            if (CollectionUtils.isEmpty(dataList))
                return SingleResponse.buildSuccess();
            checkExcelData(dataList);
            memberGmvRecordDouyinList = toMemberGmvRecordDouyinList(dataList);
        } catch (IOException e) {
            e.printStackTrace();
            return SingleResponse.buildFailure(ErrorEnum.FILE_UPLOAD_FAILED.getErrCode(), "文件读取失败");
        } catch (DataFormatException e) {
            e.printStackTrace();
            return SingleResponse.buildFailure(ErrorEnum.FILE_UPLOAD_FAILED.getErrCode(), e.getMessage());
        }
        MemberGmvRecordBase memberGmvRecordBase = new MemberGmvRecordBase();
        memberGmvRecordBase.setCount(Integer.valueOf(memberGmvRecordDouyinList.size()));
        //设置type为1 抖音 目前只有该类型
        memberGmvRecordBase.setThirdPartyType(Integer.valueOf(1));
        memberGmvRecordBaseService.addMemberGmvRecordBase(memberGmvRecordBase);
        for (MemberGmvRecordDouyin memberGmvRecordDouyin : memberGmvRecordDouyinList) {
            memberGmvRecordDouyin.setBaseId(memberGmvRecordBase.getId());
        }
        memberGMVService.addMemberGmvRecordBatch(memberGmvRecordDouyinList);
        return SingleResponse.buildSuccess();
    }


    @ApiOperation("删除gmv记录")
    @PostMapping({"/del/{id}"})
    public SingleResponse<?> delMemberGVMRecord(@PathVariable("id") Long id) {
        memberGMVService.delMemberGMVRecord(id);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation("查询gmv分页记录")
    @GetMapping({"/page"})
    public SingleResponse<?> getMemberGVMRecordPage(Long pageSize, Long pageNum, Long baseId, Long userId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("baseId", baseId);
        param.put("userId", userId);
        IPage<MemberGmvRecordDouyin> memberGmvRecordPage = memberGMVService.getMemberGmvRecordPage(pageSize, pageNum, param);
        return SingleResponse.of(memberGmvRecordPage);
    }

    @ApiOperation("删除gmv base记录")
    @PostMapping({"/base/del/{id}"})
    public SingleResponse<?> delMemberGVMRecordBase(@PathVariable("id") Long id) {
        memberGmvRecordBaseService.delMemberGmvRecordBase(id);
        memberGMVService.delMemberGMVRecordByBaseId(id);
        return SingleResponse.buildSuccess();
    }



    @ApiOperation("查询gmv base分页记录")
    @GetMapping({"/base/page"})
    public SingleResponse<?> getMemberGMVRecordBasePage(Long pageSize, Long pageNum) {
        IPage<MemberGmvRecordBase> memberGmvRecordBasePage = this.memberGmvRecordBaseService.getMemberGmvRecordBasePage(pageSize, pageNum);
        return SingleResponse.of(memberGmvRecordBasePage);
    }



    private List<Map<String, Object>> readerExcel(MultipartFile file) throws IOException {
        if (file.isEmpty())
            throw new FileUploadException(ErrorEnum.FILE_UPLOAD_FAILED, "上传文件不能为空");
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<Map<String, Object>> maps = reader.readAll();
        return maps;
    }

    private void checkExcelData(List<Map<String, Object>> dataList) throws DataFormatException {
        for (int i = 0; i < dataList.size(); i++) {
            Set<Map.Entry<String, Object>> entries = ((Map<String, Object>)dataList.get(i)).entrySet();
            int j = i + 1;
            for (Map.Entry<String, Object> entry : entries) {
                if (ObjectUtil.isEmpty(entry.getValue()))
                    throw new DataFormatException("第" + j + "行数据中存在空值");
            }
        }
    }


    private List<MemberGmvRecordDouyin> toMemberGmvRecordDouyinList(List<Map<String, Object>> dataList) throws DataFormatException {
        List<String> uidList = new ArrayList<>();
        for (Map<String, Object> map : dataList)
            uidList.add(Convert.toStr(map.get("订单归属人抖音号")));
        QueryWrapper<MemberAccountBind> queryWrapper = new QueryWrapper();
        queryWrapper.in("account_id", uidList);
        List<MemberAccountBind> memberAccountBindList = this.memberAccountBindService.list((Wrapper)queryWrapper);
        Map<String, Integer> accountIdMap = (Map<String, Integer>)memberAccountBindList.stream().collect(Collectors.toMap(MemberAccountBind::getAccountId, MemberAccountBind::getUserId, (key1, key2) -> key2));
        List<MemberGmvRecordDouyin> result = new ArrayList<>();
        int j = 0;
        for (Map<String, Object> map : dataList) {
            j++;
            MemberGmvRecordDouyin memberGmvRecordDouyin = new MemberGmvRecordDouyin();
            memberGmvRecordDouyin.setPrice(Convert.toInt(Double.valueOf(Convert.toDouble(map.get("券售卖金额")).doubleValue() * 100.0D)));
            memberGmvRecordDouyin.setThirdPartyNike(Convert.toStr(map.get("订单归属人昵称")));
            memberGmvRecordDouyin.setThirdPartyOrderId(Convert.toStr(map.get("所属订单id")));
            memberGmvRecordDouyin.setThirdPartyCommodityId(Convert.toStr(map.get("商品id")));
            memberGmvRecordDouyin.setPaymentTime(Convert.toDate(Convert.toStr(map.get("支付时间"))));
            memberGmvRecordDouyin.setThirdPartyAccount(Convert.toStr(map.get("订单归属人抖音号")));
            Integer userId = accountIdMap.get(Convert.toStr(map.get("订单归属人抖音号")));
            if (userId == null)
                throw new DataFormatException("找不到第" + j +"行数据中订单归属人抖音号对应平台id");
            memberGmvRecordDouyin.setUserId(userId);
            result.add(memberGmvRecordDouyin);
        }
        return result;
    }
}
