package com.zzy.api.controller.hotmap;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class JschCommand {
    private Session session = null;
  private Channel channel = null;

  private String sftpHost = "<PUBLIC_HOST>";
  private int sftpPort = 22;
  private String sftpUserName = "root";
  private String sftpPassword = "zzy@admin2019";
  private int timeout = 30000;

  /**
   * 获取连接
   * @return
   */
          private ChannelExec getChannelExec() {
    try {
      if (channel != null && channel.isConnected()) {
        return (ChannelExec) channel;
      }
      JSch jSch = new JSch();
      if (session == null || !session.isConnected()) {
        session = jSch.getSession(sftpUserName, sftpHost, sftpPort);
        session.setPassword(sftpPassword);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.setTimeout(timeout);
        session.connect();
      }
      channel = session.openChannel("exec");
    } catch (Exception e) {
      if (session != null) {
        session.disconnect();
        session = null;
      }
      channel = null;
    }
    return channel == null ? null : (ChannelExec) channel;
  }


  /**
   * 关闭连接
   */
          private void closeChannel() {
    try {
      if (channel != null) {
        channel.disconnect();
        channel = null;
      }
      if (session != null) {
        session.disconnect();
        session = null;
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }


  /**
   * 执行服务器端命令
   */
          public boolean executeCommand(String command) {
    boolean flag = false;
    ChannelExec channelExec = getChannelExec();
    if (channelExec == null) {
      return false;
    }
    try {
      channelExec.setInputStream(null);
      channelExec.setErrStream(System.err);
      channelExec.setCommand(command);

      InputStream in = channelExec.getInputStream();
      channelExec.connect();

      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
      String line = null;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }


      reader.close();
      closeChannel();

      flag = true;
    } catch (Exception e) {
      System.out.println(e);
      flag = false;
    }
    return flag;
  }

   /*  public static void main(String[] args) {
           JschCommand jschCommand = new JschCommand();
          *//* String rtsp="rtsp://admin:jgj773@118.117.156.194:20416/cam/realmonitor?channel=8&subtype=1";
           String sn="jzj6";
           System.out.println(jschCommand.executeCommand("ffmpeg -rtsp_transport tcp -i \""+rtsp+"\" -vcodec libx264 -vprofile baseline -acodec aac -ar 44100 -strict -2 -ac 1 -f flv -s 480x720 -q 10 rtmp://112.44.67.32:1936/stream/"+sn));*//*
       System.out.println(jschCommand.executeCommand("ps -ef|grep  ffmpeg|grep -v grep|cut -c 9-15|xargs kill -9"));

 }*/

}
