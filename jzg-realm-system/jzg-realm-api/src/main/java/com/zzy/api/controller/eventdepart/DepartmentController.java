package com.zzy.api.controller.eventdepart;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.api.client.dto.GroupChatUser;
import com.zzy.api.client.dto.RoomChatter;
import com.zzy.api.client.dto.RoomStatus;
import com.zzy.api.client.dto.UserChatInfo;
import com.zzy.api.client.mongo.MongoApiClient;
import com.zzy.api.common.constant.MemberParaEnum;
import com.zzy.api.dto.DepartmentAddPara;
import com.zzy.api.dto.DepartmentMemberAddPara;
import com.zzy.api.service.eventdepart.*;
import com.zzy.api.ws.ScoketClient;
import com.zzy.core.dto.R;
import com.zzy.core.utils.AuthUtil;
import com.zzy.core.utils.PhoneMsgUtil;
import com.zzy.db.dto.DepartmentInfoMemberInfo;
import com.zzy.db.entity.eventdepart.*;
import com.zzy.security.annotations.RequiredPermission;
import com.zzy.security.common.JwtUtil;
import com.zzy.security.dto.CustomUser;
import com.zzy.security.lib.entity.UserInfo;
import com.zzy.security.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 负责人在h5端登录只能作为普通部员登录，在pc端需要独立的账户去登录
 */
@CrossOrigin
@Api(tags = "部门相关接口")
@RestController
@RequestMapping("/depart")
@Slf4j
public class DepartmentController {


    @Autowired
    private IDepartmentInfoService departmentInfoService;

    @Autowired
    private IDepartmentMemberService departmentMemberService;

    @Autowired
    private IEventInfoService eventInfoService;

    @Autowired
    private IEventDepartMemberService eventDepartMemberService;

    @Autowired
    private UsersService usersService;


    @Autowired
    private IMsgNotifyMemberTaskService msgNotifyMemberTaskService;


    @ApiOperation(value = "所有部门列表",notes = "返回部门名称和Id")
    @GetMapping("/getAllDepartments")
    public R getAllDepartments(){
        return R.ok(departmentInfoService.getAllDepartments());
    }

    @ApiOperation(value = "部门分页列表",notes = "返回部门信息")
    @GetMapping("/getPageDepartments")
    public R getPageDepartments(Integer pageSize,Integer pageNo, String departName){
        if(pageNo==null) pageNo=1;
        if(pageSize==null) pageSize = 10;
        Map<String,Object> para = new HashMap<>();
        para.put("departName", departName);
        return R.ok(departmentInfoService.getPageDepartments(pageNo,pageSize,para));
    }

    @ApiOperation(value = "部员分页列表",notes = "返回部员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageSize",value="分页条数", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="pageNo",value="页码", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="orderPara",value="排序参数(gender,memberName等后端返回的字段)", required=false,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="orderType",value="排序规则(1:升序,2:降序)", required=false,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="keyWord",value="部门名称,部员姓名，手机号查找", required=false,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="departId",value="部门Id", required=false,paramType = "query",  dataType="string"),
    })
    @GetMapping("/getPageDepartmentMembers")
    public R getPageDepartmentMembers(Integer pageSize, Integer pageNo,Integer departId, HttpServletRequest request){
        if(pageNo==null) pageNo=1;
        if(pageSize==null) pageSize = 10;
        String op = request.getParameter("orderPara");
        String ot = request.getParameter("orderType");
        String keyWord = request.getParameter("keyWord");
        Map<String,Object> para = new HashMap<>();
        if(departId!=null){
            para.put("departId", departId);
        }
        if(StrUtil.isNotBlank(op)){
            if(EnumUtil.contains(MemberParaEnum.class,op)){
                para.put("orderPara", MemberParaEnum.valueOf(op).getFields());
            }
        }
        if(StrUtil.isNotBlank(ot)){
            if(ot.equals("1")){
                para.put("orderType", "ASC");
            }else{
                para.put("orderType", "DESC");
            }

        }
        para.put("keyWord", keyWord);
        return R.ok(departmentMemberService.getPageDepartmentMembers(pageNo,pageSize,para));
    }


    @ApiOperation(value = "某部门下的所有部员(部门Id多个用逗号分隔)",notes = "返回姓名和Id")
    @GetMapping("/getAllmemberByDepartId")
    public R getAllmemberByDepartId(String departmentIds){
        if(StrUtil.isBlankOrUndefined(departmentIds)) return R.nullValueError();
        String[] ids = departmentIds.replace("[", "").replace("]", "").split(",");
        List<Integer> idss = Arrays.asList(ids).stream().map(o->Integer.parseInt(o)).collect(Collectors.toList());
        List<Map<String,Object>> l = departmentMemberService.listMaps(
                new QueryWrapper<DepartmentMember>()
                .select("id","name","title","department_id as departmentId")
                .in("department_id", idss)
        );
        return R.ok(l);
    }



    @ApiOperation(value = "创建部门",notes = "部门名称不能为空")
    @PostMapping("/createDepartment")
    public R createDepartment(DepartmentAddPara para){
        if(StrUtil.isBlankOrUndefined(para.getName())){
            return R.nullValueError();
        }
        QueryWrapper qw = new QueryWrapper<DepartmentAddPara>().eq("name",para.getName());
        if(departmentInfoService.getOne(qw)!=null){
            return R.error("已存在同名部门");
        }
        DepartmentInfo di = new DepartmentInfo();
        BeanUtils.copyProperties(para, di);
        di.setCreateTime(new Date()).setIsDelete(0);
        return di.insert()?R.success():R.fail();
    }


    @ApiOperation(value = "添加部员",notes = "姓名和部门ID不能为空")
    @PostMapping("/createDepartmentMember")
    public R createDepartmentMember(DepartmentMemberAddPara para){
        if(StrUtil.isBlankOrUndefined(para.getName()) || para.getDepartmentId()==null){
            return R.nullValueError();
        }
        DepartmentMember dm = new DepartmentMember();
        dm.setBirth(TypeUtils.castToDate(para.getBirth())).setDepartmentId(para.getDepartmentId()).setEmail(para.getEmail())
                .setGender(para.getGender()).setName(para.getName()).setCreateTime(new Date()).setIsLoginEnable(1)
                .setPhoneNumber(para.getPhoneNumber()).setTitle(para.getTitle()).setIsDelete(0)
        .setHeadIcon(para.getHeadIcon())
        ;
        boolean r = dm.insert();
        if(r){
            UserInfo ui = new UserInfo();
            ui.setCreateTime(new Date());
            ui.setIsDelete(0);
            ui.setIsEnable(1);
            ui.setUserName(dm.getDepartmentId().toString()+dm.getId());
            ui.setPassWord(AuthUtil.getSaltedPwd(DigestUtil.md5Hex("123456")));
            r = usersService.saveUserWithRole(ui,3);
            r = departmentMemberService.lambdaUpdate()
                    .set(DepartmentMember::getLoginName,ui.getUserName())
                    .set(DepartmentMember::getUserId,ui.getId())
                    .eq(DepartmentMember::getId,dm.getId()).update();
        }
        return r?R.success():R.fail();
    }




    @RequiredPermission(hasRoleCode = "ADMIN")
    @ApiOperation(value = "设置部门负责人(设置的同时则代表为该部员创建登录账户)",notes = "部员和部门ID不能为空")
    @PostMapping("/setDepartmentLeader")
    public R setDepartmentLeader(Integer departId, Integer memberId,String pwd){
        if(departId==null || memberId==null || StrUtil.isBlankOrUndefined(pwd)){
            return R.nullValueError();
        }
        DepartmentInfo di =departmentInfoService.getOne(new QueryWrapper<DepartmentInfo>().eq("id",departId));
        if(di==null){
            return R.error("部门不存在");
        }
        if(memberId.equals(di.getLeader())){
            return R.error("该部员已是该部门负责人，请勿重复设置");
        }
        usersService.deleteUserInfo(di.getLeaderUserId());
        eventDepartMemberService.lambdaUpdate().set(EventDepartMember::getDepartMemberId,memberId)
                .eq(EventDepartMember::getDepartMemberId,di.getLeader()).update();
        DepartmentMember dm = departmentMemberService.lambdaQuery().eq(DepartmentMember::getId,memberId).one();
        String userName = dm.getName()+memberId;
        if(usersService.findUserByName(userName)!=null){
            userName = userName+ RandomUtil.randomString("0123456789",3);
        }
        di = new DepartmentInfo();
        di.setId(departId);
        di.setLeader(memberId);
        di.setUpdateTime(new Date());
        di.setLeaderLoginName(userName);
        boolean r = departmentInfoService.leaderSetWithLoginAccount(di,departId,memberId,userName,pwd);
        return r?R.success():R.fail();
    }


//    @ApiOperation(value = "开通部员登录账号(默认密码:123456)",notes = "多个部员Id用逗号分隔(如果是部门负责人,只能操作本部门的部员)")
//    @PostMapping("/openDepartMemberLogin")
//    public R createDepartMemberLoginAccount(String memberIds){
//        if(memberIds==null || StrUtil.isBlankOrUndefined(memberIds=memberIds.replace("[", "").replace("]", ""))){
//            return R.nullValueError();
//        }
//        List<Integer> ids = Arrays.asList(memberIds.split(",")).stream().map((p -> Integer.parseInt(p))).collect(Collectors.toList());
//        List<DepartmentMember> list2 = departmentMemberService.list(new QueryWrapper<DepartmentMember>().in("id", ids));
//        UserInfo ui = null;
//        List<UserInfo> list = new ArrayList<>();
//        for (DepartmentMember member : list2) {
//            ui = new UserInfo();
//            ui.setCreateTime(new Date());
//            ui.setIsDelete(0);
//            ui.setIsEnable(1);
//            ui.setUserName(member.getDepartmentId().toString()+member.getId());
//            ui.setPassWord(AuthUtil.getSaltedPwd(DigestUtil.md5Hex("123456")));
//            list.add(ui);
//            member.setIsLoginEnable(1).setLoginName(ui.getUserName()).setUpdateTime(new Date());
//        }
//        boolean res = departmentMemberService.batchAddMemberLoginUsers(list,3);
//        if(res){
//            list2 = list2.stream().filter(o->o.getIsLoginEnable().equals(0)).collect(Collectors.toList());
//            res = departmentMemberService.updateBatchById(list2);
//        }
//        return res?R.success():R.fail();
//    }




    /**------------------------------------------------------------------------------------------------------------------------/
     */


    @PostMapping("/updateDepartmentInfo")
    @ApiOperation(value = "修改部门信息")
    public R updateDepartmentInfo(DepartmentAddPara para,Integer id){
        DepartmentInfo di = new DepartmentInfo();
        BeanUtils.copyProperties(para, di);
        di.setId(id);
        di.setUpdateTime(new Date());
        boolean b = di.updateById();
        return R.ok(b);
    }

    @GetMapping("/getDepartmentInfoById")
    @ApiOperation(value = "根据id获取部门信息")
    public R getDepartmentInfoById(Integer id){
        return R.ok(departmentInfoService.getById(id));
    }

    @DeleteMapping("/deleteDepartmentInfoById")
    @ApiOperation(value = "根据id删除部门信息")
    public R deleteDepartmentInfoById(Integer id){
        DepartmentInfo departmentInfo = departmentInfoService.getById(id);
        boolean b = departmentInfo.deleteById();
        if(b){
            return R.ok("删除成功");
        }
        return R.ok("删除失败");
    }

    @PostMapping("/updateDepartmentMember")
    @ApiOperation(value = "修改部门成员信息",notes = "姓名和部门ID不能为空")
    public R updateDepartmentMember(DepartmentMemberAddPara para,Integer id){
        if(StrUtil.isBlankOrUndefined(para.getName()) || para.getDepartmentId()==null){
            return R.nullValueError();
        }
        DepartmentMember dm = new DepartmentMember();
        dm.setBirth(TypeUtils.castToDate(para.getBirth())).setDepartmentId(para.getDepartmentId()).setEmail(para.getEmail())
                .setGender(para.getGender()).setName(para.getName())
                .setHeadIcon(para.getHeadIcon())
                .setPhoneNumber(para.getPhoneNumber()).setTitle(para.getTitle()).setId(id).setUpdateTime(new Date());
        boolean b = dm.updateById();
        if(b){
            return R.ok("修改成功");
        }
        return R.ok("修改失败");
    }

    @GetMapping("/getDepartmentMemberById")
    @ApiOperation(value = "根据id获取部门人员信息")
    public R getDepartmentMemberById(Integer id){
          return R.ok(departmentMemberService.getById(id));
    }


    @DeleteMapping("/deleteDepartmentMemberById")
    @ApiOperation(value = "根据id删除部门人员信息")
    public R deleteDepartmentMemberById(Integer id){
        if(id==null){
            return R.nullValueError();
        }
        boolean b = departmentMemberService.removeById(id);
        if(b){
            return R.success();
        }
        return R.fail();
    }




    @GetMapping("/sendMsgToMember")
    @ApiOperation(value = "短信通知本部门成员，队长任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="对应的事件Id", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="memberIds",value="部门成员Id(多个用逗号分隔)", required=true,paramType = "query",  dataType="string"),
    })
    public R sendMsgToMember(Integer eventId, String memberIds){
        if(eventId==null || memberIds==null || StrUtil.isBlankOrUndefined(memberIds = memberIds.replace("[", "").replace("]", ""))){
            return R.nullValueError();
        }
        String[] ids = memberIds.split(",");
        List<DepartmentMember> list = departmentMemberService.listByIds(Arrays.asList(ids));
        if(list==null || list.isEmpty()){
            return R.fail();
        }
        list = list.stream().filter(o->StrUtil.isNotBlank(o.getPhoneNumber())&&o.getPhoneNumber().length()>=11).collect(Collectors.toList());
        List<String> phones = list.stream().map(DepartmentMember::getPhoneNumber).collect(Collectors.toList());
        if(phones==null || phones.isEmpty()){
            return R.error("成员手机号未正确设置");
        }
        List<MsgNotifyMemberTask> ms = new ArrayList<>();
        list.forEach(o->ms.add(new MsgNotifyMemberTask().setCreateTime(LocalDateTime.now()).setDepartmemberId(o.getId()).setEventId(eventId).setNotifyTime(LocalDateTime.now())));
        if(msgNotifyMemberTaskService.batchInsert(ms)){
            log.info("发送短信:{}", phones);
            //"登录【http://<PUBLIC_HOST>:8181/】 "
            PhoneMsgUtil.sendGroupMsg("【收到最新的部门任务指派】请登录 http://<PUBLIC_HOST>:8181/ 查看", phones);
        }else{
            return R.fail();
        }
        return R.ok("成功发送正确手机号的成员:"+list.stream().map(DepartmentMember::getName).collect(Collectors.toList()));
    }



    @Autowired
    private ScoketClient webScoketClient;

    @Autowired
    MongoApiClient<UserChatInfo> userChatMongo;


    @GetMapping("openChatGroup")
    @ApiOperation(value = "创建讨论组(返回对应的websocket地址)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="对应的事件Id", required=true,paramType = "query",  dataType="string"),
            @ApiImplicitParam(name="userId",value="当前登录用户Id", required=true,paramType = "query",  dataType="string"),
    })
    public R openChatGroup(Integer eventId, Integer userId, HttpServletRequest request){
        if(eventId==null || userId==null){
            return R.nullValueError();
        }
        if(eventInfoService.getById(eventId).getIsSingle().equals(1)){
            return R.error("单部门任务无法创建");
        }
        DepartmentInfo di = departmentInfoService.lambdaQuery().eq(DepartmentInfo::getLeaderUserId,userId).one();
        if(di==null){
            return R.error("非事件指定部门");
        }
        EventDepartMember edm = eventDepartMemberService.lambdaQuery()
                .eq(EventDepartMember::getEventId,eventId)
                .eq(EventDepartMember::getDepartMemberId,di.getLeader()).one();
        if(edm==null){
            return R.error("非事件指定部门");
        }
        //<PUBLIC_HOST>
        //192.168.110.226
        //112.44.67.32:8631
        StringBuilder sb = new StringBuilder("ws://112.44.67.32:8631/wsChat/");
        sb.append(eventId.toString()).append("/")
                .append(DigestUtil.md5Hex16(userId.toString()))
                ;
        JSONObject jo = new JSONObject();
        jo.put("wsUrl", sb.toString());
        RoomStatus rs = new RoomStatus();
        List<EventDepartMember> edms = eventDepartMemberService.lambdaQuery()
                .eq(EventDepartMember::getEventId,eventId).list();
        List<DepartmentInfoMemberInfo> dis = departmentInfoService.getLeaderInfoByMemberId(edms.stream().map(o->o.getDepartMemberId()).collect(Collectors.toSet()));
        List<RoomChatter> aliveChatter = new ArrayList<>();
        for (DepartmentInfoMemberInfo member : dis) {
            aliveChatter.add(RoomChatter.build(member.getMemberName(),
                    member.getLeaderLoginName(),member.getLeaderUserId(),member.getHeadIcon(),false));
        }
        rs.setLiveCount(0);
        rs.setLiveChatter(aliveChatter);
        jo.put("roomStatus", rs);
        return R.ok(jo);
    }




    @GetMapping("/sendChatMsg")
    @ApiOperation(value = "发送讨论组聊天信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="eventId",value="对应的事件Id", required=true,  dataType="string"),
            @ApiImplicitParam(name="message",value="聊天内容", required=true,  dataType="string"),
            @ApiImplicitParam(name="userId",value="用户Id", required=true,  dataType="string"),
    })
    public R sendChatMsg(Integer eventId, String message, Integer userId,HttpServletRequest request){
        if(eventId==null || userId==null || StrUtil.isBlankOrUndefined(message)){
            return R.nullValueError();
        }
        UserInfo ui = usersService.selectUserInfo(userId);
        DepartmentInfo di = departmentInfoService.lambdaQuery().eq(DepartmentInfo::getLeaderUserId,userId).one();
        UserChatInfo uc = new UserChatInfo();
        GroupChatUser gc = null;
        if(di==null){
            gc = GroupChatUser.build(eventId.toString(), ui.getUserName());
            uc.setHeadIcon(ui.getHeadIcon());
        }else{
            DepartmentMember dm = departmentMemberService.getById(di.getLeader());
            gc = GroupChatUser.build(eventId.toString(), dm.getName());
            uc.setHeadIcon(dm.getHeadIcon());
        }
        uc.setUserName(gc.getUserName()).setGroupName(gc.getGroupName()).setMsgContent(message).setSendTime(DateUtil.now());
        webScoketClient.sendChatInfo(uc);
        return R.success();
    }



//    @GetMapping("/recieveChatMsg")
//    public R recieveChatMsg(String userName,String topic){
//        if(StrUtil.isBlankOrUndefined(userName) || StrUtil.isBlankOrUndefined(topic)){
//            return R.nullValueError();
//        }
//        Query q = new Query();
//        Criteria criteria = new Criteria();
//        criteria.and("groupName").is(topic);
//        q.addCriteria(criteria);
//        List<UserChatInfo> list = userChatMongo.findAll(new UserChatInfo(),q);
//        return R.ok(list);
//    }














}
