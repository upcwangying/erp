package com.erp.product2.servlet;

import com.erp.product2.enums.SortEnum;
import com.erp.product2.util.FileUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wang_ on 2016-08-25.
 */
@WebServlet(name = "FileManagerJsonServlet",
        urlPatterns = {"/FileManagerJsonServlet"})
public class FileManagerJsonServlet extends HttpServlet {
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
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();
        String param = FileUtil.fileManager(request);
        if (param != null) {
            out.println(param);
            out.close();
            return;
        }

        //排序形式，name or size or type
        String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

        //目录不存在或不是目录
        File currentPathFile = new File(FileUtil.currentPath);
        if(!currentPathFile.isDirectory()){
            out.println(getError("Directory does not exist."));
            out.close();
            return;
        }

        //遍历目录取的文件信息
        List<HashMap<String, Object>> fileList = new ArrayList<>();
        if(currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                HashMap<String, Object> hash = new HashMap<>();
                String fileName = file.getName();
                if(file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if(file.isFile()){
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.asList(FileUtil.fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }

        Collections.sort(fileList, SortEnum.getSortEnumByName(order).getComparator());

        JSONObject result = new JSONObject();
        result.put("moveup_dir_path", FileUtil.moveupDirPath);
        result.put("current_dir_path", FileUtil.currentDirPath);
        result.put("current_url", FileUtil.currentUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);

        out.println(result.toString());
        out.close();
    }

    private String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toString();
    }
}
