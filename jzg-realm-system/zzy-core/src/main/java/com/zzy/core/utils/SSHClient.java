package com.zzy.core.utils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Date;


@Slf4j
public class SSHClient {

    private static final String host = "172.20.18.18";
    private static final String user = "root";
    private static final String psd = "1234@qwer";


    public static void main(String[] args) {
        pubFile("2020-03-24-14-41-53652dist.zip");
    }

    public static void pubFile(String fileName){
        try {
            Connection connection = new Connection(host);// 创建一个连接实例
            connection.connect();// Now connect
            boolean isAuthenticated = connection.authenticateWithPassword(user, psd);// Authenticate
            if (isAuthenticated == false)throw new IOException("user and password error");
            Session sess = connection.openSession();// Create a session
            log.debug("start exec command.......");
            sess.requestPTY("bash");
            sess.startShell();
            InputStream stdout = new StreamGobbler(sess.getStdout());
            InputStream stderr = new StreamGobbler(sess.getStderr());
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stderr));
            PrintWriter out = new PrintWriter(sess.getStdin());
            String distTmpBeforePath = "/mnt/jzg-zzy-zhly/jzg-front/pc";
            out.println("cd "+distTmpBeforePath+"/dist-tmp");
            out.println("rm -rf dist.zip 1.txt dist/");
            out.println("cd /mnt/minio/data/jzg-fs/pcPage");
            out.println("cp "+fileName+" "+distTmpBeforePath+"/dist-tmp/dist.zip");
            out.println("cd "+distTmpBeforePath+"/dist-tmp");
            out.println("unzip dist.zip");
            out.println("cd " +distTmpBeforePath+"/dist-tmp");
            out.println("mv dist/ "+distTmpBeforePath+"/dist-new");
            out.println("cd "+distTmpBeforePath);
            out.println("mv dist/ dist-tmp");
            out.println("mv dist-new/ dist");
            out.println("exit");
            out.close();
            sess.waitForCondition(ChannelCondition.CLOSED|ChannelCondition.EOF | ChannelCondition.EXIT_STATUS,30000);
            log.debug("下面是从stdout输出:");
            while (true) {
                String line = stdoutReader.readLine();
                if (line == null)break;
                log.debug(line);
            }
            log.debug("下面是从stderr输出:");
            while (true) {
                String line = stderrReader.readLine();
                if (line == null)break;
                log.debug(line);
            }
            log.debug("ExitCode: " + sess.getExitStatus());
            sess.close();/* Close this session */
            connection.close();/* Close the connection */
            return;
        } catch (IOException e) {
            log.error("sshClient异常",e);
            return;
        }
    }

    public static void pubLawH5File(String fileName) {
        try {
            Connection connection = new Connection(host);// 创建一个连接实例
            connection.connect();// Now connect
            boolean isAuthenticated = connection.authenticateWithPassword(user, psd);// Authenticate
            if (isAuthenticated == false)throw new IOException("user and password error");
            Session sess = connection.openSession();// Create a session
            log.debug("start exec command.......");
            sess.requestPTY("bash");
            sess.startShell();
            InputStream stdout = new StreamGobbler(sess.getStdout());
            InputStream stderr = new StreamGobbler(sess.getStderr());
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stderr));
            PrintWriter out = new PrintWriter(sess.getStdin());
            String distTmpBeforePath = "/mnt/jzg-zzy-zhly/jzg-front/pc";
            out.println("cd "+distTmpBeforePath+"/dist-tmp");
            out.println("rm -rf dist.zip 1.txt dist/");
            out.println("cd /mnt/minio/data/jzg-fs/pcPage");
            out.println("cp "+fileName+" "+distTmpBeforePath+"/dist-tmp/dist.zip");
            out.println("cd "+distTmpBeforePath+"/dist-tmp");
            out.println("unzip dist.zip");
            out.println("cd " +distTmpBeforePath+"/dist-tmp");
            out.println("mv dist/ "+distTmpBeforePath+"/dist-new");
            out.println("cd "+distTmpBeforePath);
            out.println("mv dist/ dist-tmp");
            out.println("mv dist-new/ dist");
            out.println("exit");
            out.close();
            sess.waitForCondition(ChannelCondition.CLOSED|ChannelCondition.EOF | ChannelCondition.EXIT_STATUS,30000);
            log.debug("下面是从stdout输出:");
            while (true) {
                String line = stdoutReader.readLine();
                if (line == null)break;
                log.debug(line);
            }
            log.debug("下面是从stderr输出:");
            while (true) {
                String line = stderrReader.readLine();
                if (line == null)break;
                log.debug(line);
            }
            log.debug("ExitCode: " + sess.getExitStatus());
            sess.close();/* Close this session */
            connection.close();/* Close the connection */
            return;
        } catch (IOException e) {
            log.error("sshClient异常",e);
            return;
        }
    }

    public static void pubLawPc(String fileName) {
        try {
            Connection connection = new Connection(host);// 创建一个连接实例
            connection.connect();// Now connect
            boolean isAuthenticated = connection.authenticateWithPassword(user, psd);// Authenticate
            if (isAuthenticated == false)throw new IOException("user and password error");
            Session sess = connection.openSession();// Create a session
            log.debug("start exec command.......");
            sess.requestPTY("bash");
            sess.startShell();
            InputStream stdout = new StreamGobbler(sess.getStdout());
            InputStream stderr = new StreamGobbler(sess.getStderr());
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stderr));
            PrintWriter out = new PrintWriter(sess.getStdin());
            String distTmpBeforePath = "/mnt/jzg-zzy-zhly/jzg-front/pc";
            out.println("cd "+distTmpBeforePath+"/dist-tmp");
            out.println("rm -rf dist.zip 1.txt dist/");
            out.println("cd /mnt/minio/data/jzg-fs/pcPage");
            out.println("cp "+fileName+" "+distTmpBeforePath+"/dist-tmp/dist.zip");
            out.println("cd "+distTmpBeforePath+"/dist-tmp");
            out.println("unzip dist.zip");
            out.println("cd " +distTmpBeforePath+"/dist-tmp");
            out.println("mv dist/ "+distTmpBeforePath+"/dist-new");
            out.println("cd "+distTmpBeforePath);
            out.println("mv dist/ dist-tmp");
            out.println("mv dist-new/ dist");
            out.println("exit");
            out.close();
            sess.waitForCondition(ChannelCondition.CLOSED|ChannelCondition.EOF | ChannelCondition.EXIT_STATUS,30000);
            log.debug("下面是从stdout输出:");
            while (true) {
                String line = stdoutReader.readLine();
                if (line == null)break;
                log.debug(line);
            }
            log.debug("下面是从stderr输出:");
            while (true) {
                String line = stderrReader.readLine();
                if (line == null)break;
                log.debug(line);
            }
            log.debug("ExitCode: " + sess.getExitStatus());
            sess.close();/* Close this session */
            connection.close();/* Close the connection */
            return;
        } catch (IOException e) {
            log.error("sshClient异常",e);
            return;
        }
    }
}
