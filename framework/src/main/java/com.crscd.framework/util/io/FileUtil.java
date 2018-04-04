package com.crscd.framework.util.io;

import com.crscd.framework.FrameworkConstant;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 文件操作工具类
 *
 * @author lzy
 * @since 1.0
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取指定目录下的全部文件名称
     */
    public static List<String> getFileNameList(String dirPath) {
        List<String> flist = new ArrayList<>();
        try {
            File file = new File(dirPath);
            if (!file.exists()) {
                logger.debug("file " + dirPath + " is not exited");
                return flist;
            }
            if (file.isDirectory()) {
                File[] filelist = file.listFiles();
                if (filelist != null) {
                    for (File aFilelist : filelist) {
                        if (!aFilelist.isDirectory()) {
                            flist.add(aFilelist.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取文件夹下的文件名称列表出错！", e);
            throw new RuntimeException(e);
        }
        return flist;
    }

    /**
     * 创建目录
     */
    public static File createDir(String dirPath) {
        File dir;
        try {
            dir = new File(dirPath);
            if (!dir.exists()) {
                FileUtils.forceMkdir(dir);
            }
        } catch (Exception e) {
            logger.error("创建目录出错！", e);
            throw new RuntimeException(e);
        }
        return dir;
    }

    /**
     * 创建文件
     */
    public static File createFile(String filePath) {
        File file;
        try {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                FileUtils.forceMkdir(parentDir);
            }
        } catch (Exception e) {
            logger.error("创建文件出错！", e);
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 复制目录（不会复制空目录）
     */
    public static void copyDir(String srcPath, String destPath) {
        try {
            File srcDir = new File(srcPath);
            File destDir = new File(destPath);
            if (srcDir.exists() && srcDir.isDirectory()) {
                FileUtils.copyDirectoryToDirectory(srcDir, destDir);
            }
        } catch (Exception e) {
            logger.error("复制目录出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制文件
     */
    public static void copyFile(String srcPath, String destPath) {
        try {
            File srcFile = new File(srcPath);
            File destDir = new File(destPath);
            if (srcFile.exists() && srcFile.isFile()) {
                FileUtils.copyFileToDirectory(srcFile, destDir);
            }
        } catch (Exception e) {
            logger.error("复制文件出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除目录
     */
    public static void deleteDir(String dirPath) {
        try {
            File dir = new File(dirPath);
            if (dir.exists() && dir.isDirectory()) {
                FileUtils.deleteDirectory(dir);
            }
        } catch (Exception e) {
            logger.error("删除目录出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                FileUtils.forceDelete(file);
            }
        } catch (Exception e) {
            logger.error("删除文件出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 重命名文件
     */
    public static void renameFile(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        if (srcFile.exists()) {
            File newFile = new File(destPath);
            boolean result = srcFile.renameTo(newFile);
            if (!result) {
                throw new RuntimeException("重命名文件出错！" + newFile);
            }
        }
    }

    /**
     * 读取文件内容
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readFile(String path) {
        if (!checkFileExists(path)) {
            return "";
        }
        String result = "";
        try {
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    result += lineTxt;
                }
                read.close();
            } else {
                System.out.println("Cannot find file:" + path);
            }
        } catch (Exception e) {
            System.out.println("Read file errer!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将字符串写入文件
     */
    public static boolean writeFile(String filePath, String fileContent) {
        OutputStream os = null;
        Writer w = null;
        try {
            FileUtil.createFile(filePath);
            os = new BufferedOutputStream(new FileOutputStream(filePath));
            w = new OutputStreamWriter(os, FrameworkConstant.UTF_8);
            w.write(fileContent);
            w.flush();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (w != null) {
                    w.close();
                }
            } catch (Exception e) {
                logger.error("释放资源出错！", e);
            }
        }

        return true;
    }

    /**
     * 获取真实文件名（去掉文件路径）
     */
    public static String getRealFileName(String fileName) {
        return FilenameUtils.getName(fileName);
    }

    /**
     * 判断文件是否存在
     */
    public static boolean checkFileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 判断文件夹是否存在
     */
    public static boolean checkFileDirExists(String filePath) {
        File file = new File(filePath);
        return !file.exists() && !file.isDirectory();
    }

    /**
     * @param filePath
     * @author csq
     * @date 2016-06-01
     * @version V1.0
     */
    public static long getFileLength(String filePath) {
        File f = new File(filePath);
        return f.length();
    }

    /**
     * @param filePathList
     * @author csq
     * @version V1.0
     * @since 2016-06-01
     */
    public static long getFileListLength(List<String> filePathList) {
        long fileListLength = 0;
        for (String aFilePathList : filePathList) {
            File f = new File(aFilePathList);
            fileListLength += f.length();
        }
        return fileListLength;
    }

    /**
     * @param path
     * @return
     * @author cuishiqing
     * @since 2016-06-03
     */
    public static boolean deleteFolder(String path) {
        boolean flag = false;
        File file = new File(path);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteSingleFile(path);
            } else {  // 为目录时调用删除目录方法
                return deleteSingleDirectory(path);
            }
        }

    }

    /**
     * @param sPath
     * @return
     * @author cuishiqing
     * @since 2016-06-03
     */
    public static boolean deleteSingleFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * @param sPath
     * @return
     * @author cuishiqing
     * @since 2016-06-03
     */
    public static boolean deleteSingleDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File file : files) {
                //删除子文件
                if (file.isFile()) {
                    flag = deleteSingleFile(file.getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                } //删除子目录
                else {
                    flag = deleteSingleDirectory(file.getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
            }
            if (!flag) {
                return false;
            }
            //删除当前目录
            return dirFile.delete();
        }
        return flag;
    }

    /**
     * @param filePath
     * @return
     * @author cuishiqing
     * @since 2016-06-12
     */
    public static String getFileLastEditTime(String filePath) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File(filePath);
        long modifiedTime = file.lastModified();
        Date d = new Date(modifiedTime);
        return format.format(d);
    }


    /**
     * @param path
     * @return
     * @author cuishiqing
     * @since 2016-06-03
     */
    public static boolean isLegalPath(String path) {
        String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
        return path.matches(matches);
    }

    /**
     * 2016-06-01 csq
     * 文件大小转为bit单位 支持KB,MB,GB,TB
     *
     * @param units
     * @param size
     * @return
     */
    public static long ConvertToBit(String units, String size) {
        long size_Long = Long.parseLong(size);
        long size_B = 0;
        switch (units) {
            case "KB":
                size_B = size_Long * 1024;
                break;
            case "MB":
                size_B = size_Long * 1024 * 1024;
                break;
            case "GB":
                size_B = size_Long * 1024 * 1024 * 1024;
                break;
            case "TB":
                size_B = size_Long * 1024 * 1024 * 1024 * 1024;
                break;
            default:
                break;
        }
        return size_B;
    }

}
