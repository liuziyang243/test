package com.crscd.framework.util.text;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by cuishiqing on 2017/9/20.
 */
public class FtpUtil {
    //日志记录
    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
    //FTP客户端
    private FTPClient ftpClient;

    private String ftpAddress;
    private int port;
    private String userName;
    private String passWord;

    public FtpUtil(String ftpAddress, int port, String userName, String passWord) {
        this.ftpClient = new FTPClient();
        this.ftpAddress = ftpAddress;
        this.port = port;
        this.userName = userName;
        this.passWord = passWord;
    }

    /**
     * @param host     FTP地址
     * @param port     端口
     * @param userName 用户名
     * @param password 密码
     * @return
     * @see "Ftp Client Connect"
     */
    private boolean ftpConnect(String host, int port, String userName, String password) {
        try {
            ftpClient.connect(host, port);
            ftpClient.setControlEncoding("UTF-8");
            int reply = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                if (ftpClient.login(userName, password)) {
                    System.out.println("Successful login!");
                    return true;
                } else {
                    System.out.println("fail to login!");
                }
            }
        } catch (IOException e) {
            logger.error("Failure connection", e);
        }
        return false;
    }

    private boolean ftpConnect() {
        return ftpConnect(this.ftpAddress, this.port, this.userName, this.passWord);
    }

    /**
     * @see "FTP Client 断开连接"
     */
    private void ftpDisconnect() {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @param fileName 文件名称
     * @return
     * @see "删除文件"
     */
    public boolean ftpDelete(String fileName) {
        try {
            if (!ftpClient.isConnected()) {
                if (!ftpConnect()) {
                    return false;
                }
            }
            //return ftpClient.deleteFile(fileName);
            return ftpClient.deleteFile(new String(fileName.getBytes("gb2312"), "iso-8859-1"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            ftpDisconnect();
        }
    }

    /**
     * @return
     * @see "获取FTPServer上的文件信息列表"
     */
    public FTPFile[] getFileInfoList() {
        FTPFile[] ftpFiles = new FTPFile[0];
        try {
            if (!ftpClient.isConnected()) {
                if (!ftpConnect()) {
                    return ftpFiles;
                }
            }
            ftpFiles = ftpClient.listFiles();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ftpDisconnect();
        }
        return ftpFiles;
    }

}
