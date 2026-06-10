package com.zzy.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class MinioUtil {

    public enum FileType{
        pic,
        video,
        other,
        exe,
        jar,
        zip,
        rar;
    }


    private static final String accessKey = "minio+2020";
    private static final String secretAccessKey = "minio*2020";
    private static final String endPoint = "http://112.44.67.32:8803";
    private static final String bucketName = "jzg-fs";



    public static void main(String[] args) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidPortException, BucketPolicyTooLargeException, InvalidResponseException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InvalidEndpointException, InternalException {
        MinioClient minioClient = getClient();
        System.out.println(JSON.toJSONString(minioClient.listBuckets()));
    }


    /**
     * true:null或空文件
     * @param file
     * @return
     */
    public static boolean isNullEmpty(MultipartFile file){
        return file==null||file.isEmpty();
    }

    public static void removeObject(String objectName) throws Exception {
        MinioClient minioClient = getClient();
        minioClient.removeObject(bucketName,objectName);
    }

    /**
     * 上传文件并返回该文件的网络地址
     * @param file
     * @return
     * @throws Exception
     */
    public static String uploadFile(MultipartFile file) throws Exception{
        String pathFileName = generateFilePathName(file);
        return uploadFile(file,pathFileName);
    }


    public static String generateFilePathName(MultipartFile file) {
        String fileOrigniName = file.getOriginalFilename();
        String suffix = fileOrigniName.substring(fileOrigniName.lastIndexOf("."));
        StringBuilder pre = new StringBuilder();
        String aped = fileOrigniName.substring(fileOrigniName.lastIndexOf("."));
        pre.append(MD5.create().digestHex(UUID.randomUUID(true).toString(true)))
                .append(DateUtil.format(new Date(), "yyyyMMddHHmmssSSS")).append(aped);
        return DigestUtil.md5Hex(pre.toString())+suffix;
    }



    /**
     * 上传文件并返回该文件的网络地址
     * @param file
     * @param pathFileName :  路径/文件名.jpg(png|mp4|exe)
     * @return
     * @throws Exception
     */
    public static String uploadFile(MultipartFile file, String pathFileName) throws Exception{
        MinioClient minioClient = getClient();
        Assert.notNull(minioClient);
        minioClient.putObject(bucketName,pathFileName,file.getInputStream(),file.getSize(),null,null,file.getContentType());
        return new StringBuilder(endPoint).append("/").append(bucketName)
                .append("/").append(pathFileName).toString();
    }

    public static String uploadFile(String fileType, MultipartFile file, String pathFileName) throws Exception{
        MinioClient minioClient = getClient();
        Assert.notNull(minioClient);
        minioClient.putObject(bucketName,fileType+"/"+pathFileName,file.getInputStream(),file.getSize(),null,null,file.getContentType());
        return new StringBuilder(endPoint).append("/").append(bucketName)
                .append("/").append(pathFileName).toString();
    }


    public static MinioClient getClient(){
        try {
            MinioClient minioClient = new MinioClient(endPoint, accessKey, secretAccessKey);
            return minioClient;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
