package com.zzy;

import com.zzy.api.ApiMainRun;
//import com.zzy.api.client.mongo.MongoApiClient;
//import com.zzy.security.dto.UserDepartInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiMainRun.class)
public class TestUnitApi {
//
//    @Autowired
//    MongoApiClient<UserDepartInfo> mongoApiClient;
//
//    int i = 1;
//
//    @Test
//    public void test(){
////        mongoApiClient.createCollection(new UserDepartInfo());
////        System.out.println(mongoApiClient.getAllCollections());
//        for (int j = 0; j < 10; j++) {
//            UserDepartInfo info = new UserDepartInfo();
//            info.setUserId(1);
//            info.setDepartmentName("dawdawd");
//            info.setName("hello");
//            info.setTitle("职称"+i);
//            mongoApiClient.insertOne(info);
//            System.out.println(mongoApiClient.listAll(info).size());
//            i++;
//        }
//
//    }
//
}
