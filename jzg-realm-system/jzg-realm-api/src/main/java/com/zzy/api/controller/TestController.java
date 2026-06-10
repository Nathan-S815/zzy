package com.zzy.api.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zzy.core.dto.R;
import com.zzy.core.utils.MinioUtil;
import com.zzy.core.utils.SSHClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/test/public")
public class TestController {


    static volatile Map<String, String> m = new ConcurrentHashMap<>();



    @PostMapping("/html-publish")
    @ApiOperation(value = "结果返回之前勿重复点击")
    public R upload(MultipartFile file, HttpServletResponse response, String password) {
        try {
            if(!m.isEmpty()){
                return R.error("请等待上一个操作执行完毕再操作");
            }
            if (file.isEmpty()) {
                return R.ok("文件不能为空");
            }
            if (StrUtil.isBlankOrUndefined(password)) {
                return R.error();
            }
            String pwdd = DateUtil.format(new Date(), "yyyyMMddHH");
            if (!pwdd.equals(password)) {
                return R.error("密码错误");
            }
            if (!file.getOriginalFilename().endsWith(".zip")) {
                return R.ok("只接受zip压缩文件");
            }
            try {
                String fullName = DateUtil.format(new Date(), "yyyy-MM-dd-HH-mm") + "dist.zip";
                if (m.containsKey(fullName)) {
                    return R.ok("已有文件正在上传，请等待上传文件结束再点击");
                }
                m.put(fullName, "1");
                String res = MinioUtil.uploadFile("pcPage", file, fullName);
                m.put(fullName, "2");
                Thread.sleep(3000L);
                if (res != null && res.length() > 1) {
                    System.out.println("地址:" + res);
                    System.out.println("准备执行cmd");
                    SSHClient.pubFile(fullName);
                }else{
                    return R.error("上传失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            m.clear();
            return R.ok("上传成功");
        } catch (Exception e) {
            return R.error();
        }

    }



}






















