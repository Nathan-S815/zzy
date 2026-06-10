package com.zzy.api.client.mongo;


import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Set;

public interface MongoBaseService<T> {


    void createCollection(T t);

    void insertOne(T t);

    void batchInsert(List<T> t);

    List<T> listAll(T t);

    void deleteOne(T t);

    void batchDelete(List<T> t);

    Set<String> getAllCollections();

    List<T> findAll(T t, Query query);


}
