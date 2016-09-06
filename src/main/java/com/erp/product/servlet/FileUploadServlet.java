package com.erp.product.servlet;

import com.erp.util.ImageUtil;
import com.erp.util.SystemConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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

@WebServlet(name = "FileUploadServlet",
		urlPatterns = {"/FileUploadServlet"})
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		String delfile = request.getParameter("delfile");
		if (delfile != null && !delfile.isEmpty()) {
			String parentFile = delfile.substring(0,8);
			String delPath = request.getServletContext().getRealPath("/") + "upload/" + parentFile + "/";
			String[] delFiles = delfile.split(",");
			for (String delFile : delFiles) {
				File file = new File(delPath, delFile);
				if (file.exists()) {
					file.delete();
				}
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}

		PrintWriter out = response.getWriter();

		//�ļ�����Ŀ¼·��
		StringBuffer savePath = new StringBuffer(request.getServletContext().getRealPath("/"));
		savePath.append("upload/");

		//�ļ�����Ŀ¼URL
		StringBuffer saveUrl = new StringBuffer(request.getContextPath());
		saveUrl.append("/upload/");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath.append(ymd).append("/");
		saveUrl.append(ymd).append("/");

		File dirFile = new File(savePath.toString());
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = null;
		JSONObject json = null;
		String fileName = null;
		try {
			items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				fileName = item.getName();
				long fileSize = item.getSize();
				if (!item.isFormField()) {
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String randomName = df.format(new Date()) + "_" + new Random().nextInt(1000);

					String newFileName = randomName + "." + fileExt;
					// ѹ����ͼƬ��
					String parseFileName = randomName + "_ys." + fileExt;

					File uploadedFile = new File(savePath.toString(), newFileName);

					String url = saveUrl.toString() + newFileName;
					String thumbnailUrl = url;
					String deleteUrl = url;

					if (SystemConfig.getBooleanValue("fileupload.imageResizeEnable")) {
						File parseUploadedFile = new File(savePath.toString(), parseFileName);

						FileInputStream in = (FileInputStream) item.getInputStream();
						ImageUtil.compressImage(in, parseUploadedFile,
								SystemConfig.getIntegerValue("fileupload.imageWidth", 80),
								SystemConfig.getIntegerValue("fileupload.imageHeight", 80));
						thumbnailUrl = saveUrl.toString() + parseFileName;
					}

					item.write(uploadedFile);

					JSONArray array = new JSONArray();
					json = new JSONObject();
					json.put("name", fileName);
					json.put("size", fileSize);
					json.put("url", url);
					json.put("thumbnailUrl", thumbnailUrl);
					json.put("deleteUrl", "/erp/FileUploadServlet?delfile=" + newFileName+","+parseFileName);
					json.put("deleteType", "GET");
					array.add(json);
					JSONObject object = new JSONObject();
					object.put("files", array);
					out.println(object.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = new JSONObject();
			json.put("name", fileName);
			json.put("size", 902604);
			json.put("error", "�ϴ�ͼƬʧ�ܣ���");
			out.println(json.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}