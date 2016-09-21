package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.Group;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.GroupService;
import com.erp.util.StringUtil;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-09-20.
 */
@WebServlet(name = "GroupServlet",
        urlPatterns = {"/GroupServlet"})
public class GroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(GroupServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String param = request.getParameter("param");
        String responseText = "";
        if ("query".equals(param)) {
            responseText = queryGroups(true);
        } else if ("add".equals(param) || "delete".equals(param)) {
            responseText = editGroup(request, param);
        } else if ("valid".equals(param)) {
            responseText = isValid(request);
        } else if ("query-combo".equals(param)) {
            responseText = queryGroups(false);
        }

        PrintWriter writer = response.getWriter();
        writer.write(responseText);
        writer.close();
    }

    /**
     * ≤È—Ø
     *
     * @return
     */
    private String queryGroups(boolean flag) {
        JSONArray array = null;
        try {
            array = GroupService.queryGroups();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(array);
        return responseBean.getResponseArray(flag);
    }

    /**
     * @param request
     * @param param
     * @return
     */
    private String editGroup(HttpServletRequest request, String param) {
        boolean success = false;
        String msg = "";
        try {
            if ("delete".equals(param)) {
                String[] groupId = request.getParameterValues("groupId");
                String staffId = request.getParameter("staffId");
                GroupService.deleteGroup(groupId, staffId);
            } else if ("add".equals(param)) {
                Group group = new Group();
                String groupId = request.getParameter("groupId");
                String groupCode = request.getParameter("groupCode");
                String groupName = request.getParameter("groupName");
                String groupDesc = request.getParameter("groupDesc");
                String[] module = request.getParameterValues("module");
                String[] modules = request.getParameterValues("modules");
                String staffId = request.getParameter("staffId");
                group.setGroupId(StringUtil.isEmpty(groupId) ? 0L : Long.valueOf(groupId));
                group.setGroupCode(groupCode);
                group.setGroupName(groupName);
                group.setGroupDesc(groupDesc);
                group.setModule(array2String(module));
                group.setModules(array2String(modules));
                group.setIs_del("0");
                group.setCreate_staffId(Long.valueOf(staffId));
                group.setUpdate_staffId(Long.valueOf(staffId));
                GroupService.insertOrUpdateGroup(group);
            }
            success = true;
            msg = TextEnum.getText(param, success);
        } catch (Exception e) {
            msg = TextEnum.getText(param, success) + e.getMessage();
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(success, msg);
        return responseBean.getResponseText();
    }

    /**
     * @param request
     * @return
     */
    private String isValid(HttpServletRequest request) {
        String groupCode = request.getParameter("groupCode");
        String groupId = request.getParameter("groupId");
        boolean success = false;
        try {
            Group group = GroupService.queryGroupByGroupId(groupCode, groupId);
            if (group == null) {
                success = true;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(success, "");
        return responseBean.getResponseBool();
    }

    /**
     *
     * @param array
     * @return
     */
    private String array2String(String[] array) {
        StringBuffer sb = new StringBuffer();
        if (array == null || array.length == 0) return "";
        for (int i=0;i<array.length;i++) {
            sb.append(",").append(array[i]);
        }
        return sb.deleteCharAt(0).toString();
    }
}
