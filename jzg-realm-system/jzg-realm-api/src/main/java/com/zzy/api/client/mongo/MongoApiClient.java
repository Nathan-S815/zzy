package com.zzy.api.client.mongo;


import com.alibaba.fastjson.JSON;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public class MongoApiClient<T> extends MongoBaseApi<T> {


    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void createCollection(T t) {
        if(mongoTemplate.collectionExists(t.getClass())){
           throw new RuntimeException("集合已存在");
        }
        mongoTemplate.createCollection(t.getClass());
    }

    @Override
    public void insertOne(T t) {
        if(!mongoTemplate.collectionExists(t.getClass())){
            mongoTemplate.createCollection(t.getClass());
        }
        mongoTemplate.save(t);
    }

    @Override
    public void batchInsert(List<T> t) {
        mongoTemplate.insertAll(t);
    }

    @Override
    public List<T> listAll(T t) {
        return (List<T>) mongoTemplate.findAll(t.getClass());
    }

    @Override
    public void deleteOne(T t) {
        mongoTemplate.remove(t.getClass());
    }

    @Override
    public void batchDelete(List<T> t) {
        Query q =  new BasicQuery(Document.parse(JSON.toJSONString(t)));
        mongoTemplate.findAllAndRemove(q,t.get(0).getClass());
    }

    @Override
    public Set<String> getAllCollections(){
        return mongoTemplate.getCollectionNames();
    }

    @Override
    public List<T> findAll(T t, Query query) {
        if(!mongoTemplate.collectionExists(t.getClass())){
            return Collections.EMPTY_LIST;
        }
        return (List<T>) mongoTemplate.find(query,t.getClass());
    }

}
