package com.zzy.api.service.reportresources;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.core.utils.JsonUtil;
import com.zzy.db.dao.reportresources.TableViewShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceManageService {

    @Autowired
    private TableViewShareMapper tableViewShareMapper;


    public List<Map<String,Object>> findResourceTab() {
        return tableViewShareMapper.selectTabWithNum();
    }

    public List<Map<String,Object>> findResourceAllRowColumn(Map<String,Object> m) {
        return tableViewShareMapper.selectAllRowColumn(m);
    }

    public List<Map<String,Object>> findTableDetails(Map<String, Object> m) {
        return tableViewShareMapper.selectTableDetails(m);
    }

    public PageInfo<Map<String, Object>> findTableRecords(Map<String, Object> m, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String, Object>> list = tableViewShareMapper.selectTableRecords(m);
        PageInfo<Map<String, Object>> pi = new PageInfo<>(list);
        Map<String,Object> r = tableViewShareMapper.selectFieldCnsName(m);
        JSONObject jo = JsonUtil.strToJSONObject(String.valueOf(r.get("fieldCnsName")));
        List<Map<String, Object>> res = new ArrayList<>();
        for (Map<String, Object> map : pi.getList()) {
            r = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                r.put(jo.getString(entry.getKey()),entry.getValue());
            }
            res.add(r);
        }
        pi.setList(res);
        return pi;
    }

    public Map<String, Object> findFeildNumWithTableNum(Map<String, Object> m) {
        return tableViewShareMapper.selectFeildNumWithTableNum(m);
    }


    /**
     * 返回对应表的字段中文对应名
     * @param m
     * @return
     */
    public Map<String, Object> findFieldCnsName(Map<String, Object> m) {
        return tableViewShareMapper.selectFieldCnsName(m);
    }

    public int getTableTypeCount() {
        return tableViewShareMapper.selectTableTypeCount();
    }
}
