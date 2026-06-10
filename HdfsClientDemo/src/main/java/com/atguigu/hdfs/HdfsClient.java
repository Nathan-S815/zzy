package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @Author: WangXh
 * @DateTime: 2022/12/29
 * @Description: TODO
 */
public class HdfsClient {

    private FileSystem fs;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        // FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration);
        fs = FileSystem.get(new URI("hdfs://hadoop102:8020"), configuration, "atguigu");
    }

    @After
    public void close() throws IOException {
        // 3 关闭资源
        fs.close();
    }

    @Test
    public void testMkdirs() throws IOException, URISyntaxException, InterruptedException {
        // 2 创建目录
        fs.mkdirs(new Path("/xiyou/huaguoshan1/"));
    }

    @Test
    public void testPut() throws IOException {
        //
        fs.copyFromLocalFile(true, true, new Path("E:\\sunwukong.txt"), new Path("/xiyou/huaguoshan"));
    }

    @Test
    public void testCopyToLocalFile() throws IOException,
            InterruptedException, URISyntaxException {

        fs.copyToLocalFile(false, new
                        Path("/xiyou/huaguoshan/sunwukong.txt"), new Path("E:/sunwukong2.txt"),
                true);
    }

    @Test
    public void testRename() throws IOException, InterruptedException,
            URISyntaxException {

        // 2 修改文件名称
        fs.rename(new Path("/xiyou/huaguoshan/sunwukong.txt"), new
                Path("/xiyou/huaguoshan/meihouwang.txt"));

    }

    @Test
    public void testDelete() throws IOException, InterruptedException,
            URISyntaxException {

        fs.delete(new Path("/xiyou"), true);

    }

    @Test
    public void file() throws IOException {
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getGroup());
            System.out.println(fileStatus.getLen());
            System.out.println(fileStatus.getModificationTime());
            System.out.println(fileStatus.getReplication());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPath().getName());
            // 获取块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            System.out.println(Arrays.toString(blockLocations));
        }
    }

    @Test
    public void testFile() throws IOException {

        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for (FileStatus status : listStatus) {
            if (status.isFile()) {
                System.out.println("文件:" + status.getPath().getName());
            } else {
                System.out.println("目录:" + status.getPath().getName());
            }
        }
    }
}
