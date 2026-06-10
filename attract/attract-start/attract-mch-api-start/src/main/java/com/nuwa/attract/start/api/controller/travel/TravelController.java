package com.nuwa.attract.start.api.controller.travel;


import cn.hutool.core.lang.Assert;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.attract.command.MaterialUploadImageCmdExe;
import com.nuwa.attract.start.api.controller.travel.param.*;
import com.nuwa.attract.start.api.controller.travel.vo.AddInvoiceParam;
import com.nuwa.attract.start.api.controller.travel.vo.TravelTeamInfoVO;
import com.nuwa.client.attract.co.MaterialUploadCO;
import com.nuwa.client.attract.dto.clientobject.MaterialUploadCmd;
import com.nuwa.client.attract.dto.clientobject.travel.qry.TravelTeamPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.invoice.entity.Invoice;
import com.nuwa.infrastructure.attract.database.invoice.mapper.InvoiceMapper;
import com.nuwa.infrastructure.attract.database.invoice.service.InvoiceService;
import com.nuwa.infrastructure.attract.database.teamuserref.entity.TeamUserRef;
import com.nuwa.infrastructure.attract.database.teamuserref.mapper.TeamUserRefMapper;
import com.nuwa.infrastructure.attract.database.teamuserref.service.TeamUserRefService;
import com.nuwa.infrastructure.attract.database.travel.entity.*;
import com.nuwa.infrastructure.attract.database.travel.mapper.CustomerMapper;
import com.nuwa.infrastructure.attract.database.travel.mapper.TeamCustomerRefMapper;
import com.nuwa.infrastructure.attract.database.travel.mapper.TeamUserCustomerRefMapper;
import com.nuwa.infrastructure.attract.database.travel.param.TravelTeamPageParam;
import com.nuwa.infrastructure.attract.database.travel.service.CustomerService;
import com.nuwa.infrastructure.attract.database.travel.service.TeamCustomerRefService;
import com.nuwa.infrastructure.attract.database.travel.service.TeamUserCustomerRefService;
import com.nuwa.infrastructure.attract.database.travel.service.TravelTeamService;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.infrastructure.attract.database.user.service.AttractUserService;
import com.nuwa.infrastructure.enums.MaterialFileTypeEnum;
import com.nuwa.infrastructure.enums.MaterialTargetEnum;
import com.nuwa.infrastructure.enums.TeamCustomerStatusEnum;
import com.nuwa.infrastructure.enums.TeamStatusEnum;
import com.nuwa.infrastructure.vo.TeamCustomerPageQueryVO;
import com.nuwa.infrastructure.vo.TravelTeamPageVO;
import com.nuwa.infrastructure.vo.TravelTeamVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.travel:TravelController.java,v1.0.0 2022-09-14 10:07:46 nanHuang Exp $
 */
@Api(tags = {"旅行社端-团队申报"})
@Slf4j
@RestController
@RequestMapping("/travelTeam")
@CrossOrigin
public class TravelController {
    private static Logger logger = LoggerFactory.getLogger(TravelController.class);
    @Resource
    private AttractUserService attractUserService;
    @Resource
    private TravelTeamService travelTeamService;
    @Resource
    private CustomerService customerService;
    @Resource
    private TeamUserCustomerRefService teamUserCustomerRefService;
    @Resource
    private TeamCustomerRefService teamCustomerRefService;
    @Resource
    private TeamCustomerRefMapper teamCustomerRefMapper;
    @Value("${upload.native.path}")
    private String uploadPath;
    @Resource
    private MaterialUploadImageCmdExe materialUploadImageCmdExe;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TeamUserCustomerRefMapper teamUserCustomerRefMapper;
    @Autowired
    private TeamUserRefService teamUserRefService;
    @Autowired
    private TeamUserRefMapper teamUserRefMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private InvoiceService invoiceService;


    private static final String KEY_PREFIX = "index:ctustomerinfo:";

    /**
     * 旅行社团队列表
     * @param teamId
     * @param leadName
     * @param refMch
     * @param teamStatus
     * @param pageSize
     * @param pageNum
     * @param beginDate
     * @param endDate
     * @param userAware
     * @return
     */
    @ApiOperation(value = "列表")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public SingleResponse<IPage<TravelTeamVO>> page(Long teamId,String leadName,String refMch,String teamStatus,
                                                    @RequestParam(defaultValue = "10") Long pageSize,
                                                    @RequestParam(defaultValue = "1") Long pageNum,
                                                    String beginDate, String endDate ,UserAware userAware) {

        IPage<TravelTeamVO> page = travelTeamService.qryTravelTeamPage(teamId,leadName,refMch,teamStatus,pageSize,pageNum,beginDate,endDate,userAware);
        return SingleResponse.of(page);
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info/{teamId}", method = RequestMethod.GET)
    public SingleResponse<TravelTeamInfoVO> info(@PathVariable(value = "teamId") Long teamId, UserAware userAware)
            throws Exception {
        TravelTeam travelTeam = travelTeamService.getById(teamId);
        Assert.notNull(travelTeam, "该团队不存在");
        TravelTeamInfoVO result = new TravelTeamInfoVO();
        BeanUtils.copyProperties(travelTeam, result);
        List<TravelTeamTripInfo> travelTeamTripInfoList = teamUserCustomerRefService.getTravelTeamTripInfo(teamId);
        travelTeamTripInfoList.stream().forEach(item -> {
            if (item.getType() == 3) {
                item.setMchName(TeamStatusEnum.INDEPENDENT_TRAVEL.getMessage());
            }
        });
        QueryWrapper<Invoice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId);
        queryWrapper.eq("delete_flag","0");
        List<Invoice> invoiceList = invoiceMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(invoiceList)) {
            result.setInvoiceList(invoiceList);
        }
        result.setTravelTeamTripInfoList(travelTeamTripInfoList);
        return SingleResponse.of(result);
    }


    @ApiOperation(value = "提交景区酒店审核")
    @RequestMapping(value = "/apply/{teamId}", method = RequestMethod.GET)
    public SingleResponse<?> apply(@PathVariable(value = "teamId") Long teamId, UserAware userAware)
            throws Exception {
        TravelTeam travelTeam = travelTeamService.getById(teamId);
        Assert.notNull(travelTeam, "该团队不存在");
        travelTeam.setTeamStatus(TeamStatusEnum.MCH_AUDIT.getCode());
        travelTeam.setApplyTime(new Date());
        travelTeam.setLastUpdateById(userAware.getUserId());
        travelTeam.setLastUpdateByName(userAware.getMchName());
        travelTeam.setLastUpdateTime(new Date());
        travelTeam.updateById();
        QueryWrapper<TeamUserRef> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("team_id", teamId);
        List<TeamUserRef> teamUserRefList = teamUserRefMapper.selectList(queryWrapper);
        teamUserRefList.stream().forEach(item -> {
            item.setStatus(item.getType() != 3 ? TeamStatusEnum.MCH_AUDIT.getCode() : TeamStatusEnum.INDEPENDENT_TRAVEL.getCode());
        });
        teamUserRefService.updateBatchById(teamUserRefList);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<?> add(@RequestBody @Validated AddTravelTeamParam param, UserAware userAware)
            throws Exception {

        /*
         * 传入团队ID时,是新上传游客名单,需要先删除之前的数据
         *
         * */
        if (param.getTeamId() != null) {
            // 更新团队-游客关联表
            QueryWrapper<TeamCustomerRef> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("team_id", param.getTeamId());
            List<TeamCustomerRef> teamCustomerRefList = teamCustomerRefMapper.selectList(queryWrapper);
            teamCustomerRefList.stream().forEach(o -> o.setStatus(1));
            teamCustomerRefService.updateBatchById(teamCustomerRefList);

            // 更新团队-酒店-游客关联表
            QueryWrapper<TeamUserCustomerRef> queryUserCustomer = new QueryWrapper<>();
            queryUserCustomer.eq("team_id", param.getTeamId());
            List<TeamUserCustomerRef> userCustomerRefList = teamUserCustomerRefMapper.selectList(queryUserCustomer);
            userCustomerRefList.stream().forEach(item -> item.setStatus(1));
            teamUserCustomerRefService.updateBatchById(userCustomerRefList);
        }
        //先添加团队
        TravelTeam travelTeam = new TravelTeam();
        BeanUtils.copyProperties(param, travelTeam);
        travelTeam.setUserId(userAware.getUserId());
        travelTeam.setTeamStatus(TeamStatusEnum.WAIT_AUDIT.getCode());
        travelTeam.setTeamPerson(param.getTeamPerson());
        if (param.getTeamId() != null) {
            travelTeam.setTeamId(param.getTeamId());
            travelTeam.updateById();
        } else {
            travelTeam.insert();
        }
        initTeamRef(param, travelTeam);
        return SingleResponse.buildSuccess();
    }

    private void initTeamRef(AddTravelTeamParam param, TravelTeam travelTeam) {
        List<TeamCustomerRef> teamCustomerRefs = Lists.newArrayList();
        List<Customer> customerss = Lists.newArrayList();
        for (TravelTeamCustomerParam ctustomer : param.getTeamCustomerParams()) {
            Customer customers = new Customer();
            TeamCustomerRef teamCustomerRef = new TeamCustomerRef();
            // 判断用户是否已经存在,存在则不管，不存在则先存数据库，再放入缓存
            if (!redisTemplate.hasKey(KEY_PREFIX + ctustomer.getIdcard())) {
                customers.setReward(ctustomer.getIdcard().startsWith("330726") ? 1 : 0);
                customers.setIdcard(ctustomer.getIdcard());
                customers.setName(ctustomer.getName());
                customers.setStatus(TeamCustomerStatusEnum.NORMAL.getCode());
                customerss.add(customers);
            } else {
                teamCustomerRef.setCustomerId(Long.valueOf(redisTemplate.opsForValue().get(KEY_PREFIX + ctustomer.getIdcard())));
                teamCustomerRef.setTeamId(travelTeam.getTeamId());
                teamCustomerRef.setStatus(TeamCustomerStatusEnum.NORMAL.getCode());
                teamCustomerRefs.add(teamCustomerRef);
            }
        }
        if (!CollectionUtils.isEmpty(customerss)) {
            customerMapper.savaInfo(customerss);
        }
        for (Customer customer : customerss) {
            TeamCustomerRef teamCustomerRefer = new TeamCustomerRef();
            teamCustomerRefer.setCustomerId(customer.getCustomerId());
            teamCustomerRefer.setTeamId(travelTeam.getTeamId());
            teamCustomerRefs.add(teamCustomerRefer);
            this.redisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + customer.getIdcard(), String.valueOf(customer.getCustomerId()));
        }
        teamCustomerRefService.saveBatch(teamCustomerRefs);

        List<TeamUserRef> teamUserRefs = new ArrayList<>();
        List<Long> refId = param.getTravelTeamInfoList().stream().map(o -> o.getUserId()).collect(Collectors.toList());
        List<AttractUser> attractUserList = (List) attractUserService.listByIds(refId).stream().distinct().collect(Collectors.toList());
        String refMch = attractUserList.stream().map(AttractUser::getUsername).collect(Collectors.joining(","));
        travelTeam.setRefId(StringUtils.join(refId.toArray(), ","));
        travelTeam.setRefMch(refMch);

        for (TravelTeamInfo travelTeamInfo : param.getTravelTeamInfoList()) {
            TeamUserRef teamUserRef = new TeamUserRef();
            if (travelTeamInfo.getType() != 3) {
                teamUserRef.setUserId(travelTeamInfo.getUserId());
            }
            teamUserRef.setStatus(TeamStatusEnum.WAIT_AUDIT.getCode());
            teamUserRef.setTeamId(travelTeam.getTeamId());
            teamUserRef.setTravelDate(travelTeamInfo.getTravelDate());
            teamUserRef.setType(travelTeamInfo.getType());
            teamUserRef.setCreateTime(new Date());
            teamUserRef.setDeleteFlag(0);
            teamUserRefs.add(teamUserRef);
        }
        teamUserRefService.saveBatch(teamUserRefs);
        travelTeam.updateById();
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/del/{teamId}", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<?> del(@PathVariable(value = "teamId") Long teamId, UserAware userAware)
            throws Exception {
        TravelTeam travelTeam = travelTeamService.getById(teamId);
        Assert.notNull(travelTeam, "该团队不存在");
        travelTeam.deleteById();
        teamCustomerRefService.lambdaUpdate()
                .eq(TeamCustomerRef::getTeamId, teamId)
                .remove();
        teamUserCustomerRefService.lambdaUpdate()
                .eq(TeamUserCustomerRef::getTeamId, teamId)
                .remove();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "/edit/{teamId}", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public SingleResponse<?> edit(@PathVariable(value = "teamId") Long teamId,
                                  @RequestBody @Validated EditTravelTeamParam param, UserAware userAware)
            throws Exception {
        TravelTeam travelTeam = travelTeamService.getById(teamId);
        BeanUtils.copyProperties(param, travelTeam);
        Assert.notNull(travelTeam, "该团队不存在");

        if (!CollectionUtils.isEmpty(param.getTravelTeamInfoList())) {
            List<TeamUserRef> teamUserRefs = new ArrayList<>();
            for (TravelTeamInfo travelTeamInfo : param.getTravelTeamInfoList()) {
                TeamUserRef teamUserRef = new TeamUserRef();
                if (travelTeamInfo.getType() != 3) {
                    teamUserRef.setUserId(travelTeamInfo.getUserId());
                }

                teamUserRef.setStatus(TeamStatusEnum.WAIT_AUDIT.getCode());
                teamUserRef.setTeamId(travelTeam.getTeamId());
                teamUserRef.setTravelDate(travelTeamInfo.getTravelDate());
                teamUserRef.setType(travelTeamInfo.getType());
                teamUserRef.setCreateTime(new Date());
                teamUserRef.setDeleteFlag(0);
                teamUserRefs.add(teamUserRef);
            }
            teamUserRefService.saveBatch(teamUserRefs);

            List<Long> refId = param.getTravelTeamInfoList().stream().map(o -> o.getUserId()).collect(Collectors.toList());
            List<AttractUser> attractUserList = (List) attractUserService.listByIds(refId).stream().distinct().collect(Collectors.toList());
            String refMch = attractUserList.stream().map(AttractUser::getUsername).collect(Collectors.joining(","));
            travelTeam.setRefId(StringUtils.join(refId.toArray(), ","));
            travelTeam.setRefMch(refMch);
        }

        if (!CollectionUtils.isEmpty(param.getTeamUserRefIds())) {
            List<TeamUserRef> teamUserRefUpdate = new ArrayList<>();
            for (Long id : param.getTeamUserRefIds()) {
                TeamUserRef teamUserRef = new TeamUserRef();
                teamUserRef.setTeamUserRefId(id);
                teamUserRef.setDeleteFlag(1);
                teamUserRefUpdate.add(teamUserRef);
                travelTeam.getRefId().replace(String.valueOf(id)+",","");
            }

            List<AttractUser> attractUserList = attractUserService.listByIds(param.getTeamUserRefIds()).stream().distinct().collect(Collectors.toList());
            for (AttractUser attractUser : attractUserList) {
                travelTeam.getRefMch().replaceAll(attractUser.getMchName() + ",", "");
            }
            teamUserRefService.updateBatchById(teamUserRefUpdate);
        }
        travelTeam.updateById();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "查询团队景点/全部用户情况")
    @RequestMapping(value = "getTeamCustomerByTeamId", method = RequestMethod.GET)
    public SingleResponse<IPage<TeamCustomerPageQueryVO>> getTeamCustomerByTeamId(@Valid TeamCustomerPageParam param,
                                                                                  UserAware userAware) {
        Page<TeamCustomerPageQueryVO> page = new Page<>(param.getPage(), param.getLimit());

        if (param.getUserId() != null && param.getTravelDate() == null) {
            return SingleResponse.buildFailure("902", "游玩日期不能为空");
        }

//        IPage<TeamCustomerPageQueryVO> result = teamCustomerRefMapper.queryTeamCustomerPage(page, param.getTeamId(),
//                param.getUserId(), param.getTravelDate());

        // 1.查询团队游客关联
        IPage<TeamCustomerPageQueryVO> res = teamCustomerRefMapper.queryTeamCustomer(page, param.getTeamId());

        // 2.查询景区/酒店/游客关联表
        List<Long> teamUserCustomerRefList = teamUserCustomerRefMapper.qryTeamUserCustomerRef(param.getTeamId(), param.getTravelDate(), param.getUserId());
        List<Long> csa = res.getRecords().stream().map(o -> o.getCustomerId()).collect(Collectors.toList());
        // 3.匹配关联情况
        for (TeamCustomerPageQueryVO rr : res.getRecords()) {
            if (teamUserCustomerRefList.contains(rr.getCustomerId())) {
                rr.setParty(true);
            } else {
                rr.setParty(false);
            }
        }
        return SingleResponse.of(res);
    }

    @ApiOperation(value = "修改团队景点用户情况")
    @RequestMapping(value = "editTeamCustomerInfo", method = RequestMethod.POST)
    public SingleResponse<?> editTeamCustomerInfo(@RequestBody @Validated EditTeamCustomerInfoParam param,
                                                  UserAware userAware) {
        TravelTeam travelTeam = travelTeamService.getById(param.getTeamId());
        Assert.notNull(travelTeam, "该团队不存在");

        if (!CollectionUtils.isEmpty(param.getDecrementCustomerId())) {
            //减少景点关联
            teamUserCustomerRefService.lambdaUpdate()
                    .eq(TeamUserCustomerRef::getTeamId, param.getTeamId())
                    .eq(TeamUserCustomerRef::getTravelDate, param.getTravelDate())
                    .eq(TeamUserCustomerRef::getUserId, param.getUserId())
                    .in(TeamUserCustomerRef::getCustomerId, param.getDecrementCustomerId())
                    .remove();
        }

        if (CollectionUtils.isEmpty(param.getIncrementCustomerId())) {
            //新增关联
            List<TeamUserCustomerRef> teamUserCustomerRefs = param.getIncrementCustomerId().stream().map(customerId -> {
                TeamUserCustomerRef ref = new TeamUserCustomerRef();
                AttractUser attractUser = attractUserService.getById(param.getUserId());
                ref.setUserId(param.getUserId());
                ref.setTravelDate(param.getTravelDate());
                ref.setCustomerId(customerId);
                ref.setTeamId(travelTeam.getTeamId());
                ref.setType(attractUser.getAccountType());
                return ref;
            }).collect(Collectors.toList());
            teamUserCustomerRefService.saveBatch(teamUserCustomerRefs);
            // 修改可获取奖励人数
            travelTeam.setRewardPerson(param.getRewardPerson());
            travelTeamService.updateById(travelTeam);
        }

        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "申报文件上传")
    @PostMapping(value = "/upload")
    public SingleResponse<?> uploadImage(@Valid @ApiParam(value = "file", type = "MultipartFile") MultipartFile file,
                                         Long typeId) throws Exception {

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (!suffix.matches(".xlsx|.xls")) {
            log.error("上传失败，文件格式错误");
            return SingleResponse.buildFailure("-99", "文件格式错误，请上传.xls或者.xlsx格式文件");
        }
        MaterialUploadCmd cmd = new MaterialUploadCmd();
        MaterialUploadCO materialUploadCo = new MaterialUploadCO();
        materialUploadCo.setFile(file);
        materialUploadCo.setTypeId(typeId);
        materialUploadCo.setTargeType(MaterialTargetEnum.TRAVEL.getCode());
        cmd.setMaterialUploadCO(materialUploadCo);
        return materialUploadImageCmdExe.execute(cmd);
    }

    @ApiOperation(value = "提交文旅局审核")
    @RequestMapping(value = "/applyAdmin", method = RequestMethod.POST)
    public SingleResponse<?> applyAdmin(@RequestBody @Validated AddInvoiceParam addInvoiceParam, UserAware userAware)
            throws Exception {
        TravelTeam travelTeam = travelTeamService.getById(addInvoiceParam.getInvoiceList().get(0).getTeamId());
        Assert.notNull(travelTeam, "该团队不存在");
        for (Invoice invoice : addInvoiceParam.getInvoiceList()) {
           if (StringUtils.isBlank(invoice.getInvoiceUrl())){
               log.error("请上传发票图片");
               return SingleResponse.buildFailure("-99", "请上传发票图片");
           }
            if (invoice.getInvoiceId() == null) {
                invoice.setCreateTime(new Date());
                invoiceService.saveBatch(addInvoiceParam.getInvoiceList());
            }else {
                invoiceService.updateById(invoice);
            }
        }
        // 判断是否存在未审核成功商家
        QueryWrapper<TeamUserRef> queryWrapper = new QueryWrapper<TeamUserRef>()
                .eq("team_id", travelTeam.getTeamId())
                .eq("delete_flag", 0);
        List<TeamUserRef> teamUserRefList = teamUserRefMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(teamUserRefList)) {
            for (TeamUserRef teamUserRef : teamUserRefList) {
                if (teamUserRef.getStatus() == TeamStatusEnum.MCH_AUDIT.getCode() || teamUserRef.getStatus() == TeamStatusEnum.MCH_AUDIT_REJECT.getCode()) {
                    log.error("存在未审核, 或者审核失败的行程");
                    return SingleResponse.buildFailure("-99", "存在未审核, 或者审核失败的行程");
                }
            }
            // 修改商户审核状态
            teamUserRefList.stream().forEach(item -> item.setStatus(TeamStatusEnum.WAIT_OFFICIAL.getCode()));
            teamUserRefService.updateBatchById(teamUserRefList);
        }

        if (CollectionUtils.isNotEmpty(addInvoiceParam.getDeleteInvoiceIds())) {
            List<Invoice> invoiceList = new ArrayList<>();
            for (Long id : addInvoiceParam.getDeleteInvoiceIds()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(id);
                invoice.setDeleteFlag(1);
                invoiceList.add(invoice);
            }
            invoiceService.updateBatchById(invoiceList);
        }
        travelTeam.setTeamStatus(TeamStatusEnum.WAIT_OFFICIAL.getCode());
        travelTeamService.updateById(travelTeam);
        return SingleResponse.buildSuccess();
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @RequestMapping(value = "/downloadTemplete", method = RequestMethod.GET)
    @ApiOperation(value = "模板下载")
    public void downloadTemplete(HttpServletResponse response) {

        try {
            final String FILE_NAME = "人员名单";
            final String FILE_TYPE = ".xls";
            InputStream fis =Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_NAME + FILE_TYPE);
            if (fis == null) {
                throw new RuntimeException("文件不存在");
            }
            OutputStream outputStream = null;
            //输出流，通过输出流将文件写回浏览器
            outputStream = response.getOutputStream();
            response.setContentType("application/msexcel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + "人员名单.xls" + ";filename*=utf-8''" + URLEncoder.encode("人员名单.xls", "UTF-8"));
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

//            //关闭资源
            outputStream.close();
            fis.close();
        } catch (Exception e) {
            logger.error("下载excel发生错误", e);
        }
    }
}