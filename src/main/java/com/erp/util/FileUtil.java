package com.erp.util;

import net.sf.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by wang_ on 2016-08-25.
 */
public class FileUtil {
    private static Logger logger = Logger.getLogger(FileUtil.class);
    public static HashMap<String, String> extMap = new HashMap<>();
    public static String uploadDirName = "";
    public static String uploadFilePath = "";
    public static String uploadFileUrl = "";

    public static String[] picResizeType = new String[]{"jpg", "jpeg", "png", "bmp"};

    public static String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
    public static String moveupDirPath = "";
    public static String currentDirPath = "";
    public static String currentPath = "";
    public static String currentUrl = "";

    static {
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
    }

    /**
     *
     * @param request
     * @return
     */
    public static String uploadFile(HttpServletRequest request) {
        //文件保存目录路径
        StringBuffer savePath = new StringBuffer(request.getServletContext().getRealPath("/"));
        savePath.append("attached\\");
        logger.info("savePath=" + savePath.toString());

        //文件保存目录URL
        StringBuffer saveUrl = new StringBuffer(request.getContextPath());
        saveUrl.append("/attached/");
        logger.info("saveUrl=" + saveUrl.toString());

        if(!ServletFileUpload.isMultipartContent(request)){
            return getError("请选择文件。");
        }

        //检查目录
        File uploadDir = new File(savePath.toString());
        if(!uploadDir.isDirectory()){
            return getError("上传目录不存在。");
        }

        //检查目录写权限
        if(!uploadDir.canWrite()){
            return getError("上传目录没有写权限。");
        }

        String dirName = request.getParameter("dir");
        if (dirName == null) {
            dirName = "image";
        }
        if(!extMap.containsKey(dirName)){
            return getError("目录名不正确。");
        }
        uploadDirName = dirName;

        //创建文件夹
        savePath.append(dirName).append("/");
        saveUrl.append(dirName).append("/");
        File saveDirFile = new File(savePath.toString());
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath.append(ymd).append("/");
        saveUrl.append(ymd).append("/");

        uploadFilePath = savePath.toString();
        uploadFileUrl = saveUrl.toString();

        File dirFile = new File(savePath.toString());
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        return null;
    }

    /**
     *
     * @param request
     */
    public static String fileManager(HttpServletRequest request) {
        //根目录路径，可以指定绝对路径
        StringBuffer rootPath = new StringBuffer(request.getServletContext().getRealPath("/"));
        rootPath.append("attached\\");

        //根目录URL，可以指定绝对路径
        StringBuffer rootUrl = new StringBuffer(request.getContextPath());
        rootUrl.append("/attached/");

        String dirName = request.getParameter("dir");
        if (dirName != null) {
            if(!Arrays.asList(new String[]{"image", "flash", "media", "file"}).contains(dirName)){
                return getError("Invalid Directory name.");
            }
            rootPath.append(dirName).append("/");
            rootUrl.append(dirName).append("/");
            File saveDirFile = new File(rootPath.toString());
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
        }

        //根据path参数，设置各路径和URL
        String path = request.getParameter("path") != null ? request.getParameter("path") : "";
        currentPath = rootPath.append(path).toString();
        currentUrl = rootUrl.append(path).toString();
        currentDirPath = path;
        if (!"".equals(path)) {
            String str = currentDirPath.substring(0, currentDirPath.length() - 1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
        }

        //不允许使用..移动到上一级目录
        if (path.indexOf("..") >= 0) {
            return getError("Access is not allowed.");
        }

        //最后一个字符不是'/'
        if (!"".equals(path) && !path.endsWith("/")) {
            return getError("Parameter is not valid.");
        }

        return null;
    }

    private static String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toString();
    }
}
