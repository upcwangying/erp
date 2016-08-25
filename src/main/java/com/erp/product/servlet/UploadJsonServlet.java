package com.erp.product.servlet;

import com.erp.product.util.FileUtil;
import com.erp.product.util.FormulaUtil;
import com.erp.product.util.ImageUtil;
import com.erp.util.SystemConfig;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wang_ on 2016-08-25.
 */
@WebServlet(name = "UploadJsonServlet",
        urlPatterns = {"/UploadJsonServlet"})
public class UploadJsonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        //最大文件大小
        long maxSize = FormulaUtil.getLongResult(SystemConfig.getValue("imageSizeLimit"));
        String param = FileUtil.uploadFile(request);
        if (param != null) {
            out.println(param);
            out.close();
            return;
        }

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        List items = null;
        try {
            items = upload.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                String fileName = item.getName();
                long fileSize = item.getSize();
                if (!item.isFormField()) {
                    //检查文件大小
                    if(fileSize > maxSize){
                        out.println(getError("上传文件大小超过限制。"));
                        out.close();
                        return;
                    }
                    //检查扩展名
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    if(!Arrays.asList(FileUtil.extMap.get(FileUtil.uploadDirName).split(",")).contains(fileExt)){
                        out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + FileUtil.extMap.get(FileUtil.uploadDirName) + "格式。"));
                        out.close();
                        return;
                    }

                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;

                    File uploadedFile = new File(FileUtil.uploadFilePath, newFileName);
                    if (Arrays.asList(FileUtil.picResizeType).contains(fileExt)
                                && SystemConfig.getBooleanValue("imageResizeEnable")) {
                        FileInputStream in = (FileInputStream) item.getInputStream();
                        ImageUtil.compressImage(in, uploadedFile,
                                SystemConfig.getIntegerValue("imageWidth", 400),
                                    SystemConfig.getIntegerValue("imageHeight", 300));
                    } else {
                        item.write(uploadedFile);
                    }

                    JSONObject obj = new JSONObject();
                    obj.put("error", 0);
                    obj.put("url", FileUtil.uploadFileUrl + newFileName);
                    out.println(obj.toString());
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println(getError("上传文件失败。"));
            out.close();
        }

    }

    private String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toString();
    }
}
