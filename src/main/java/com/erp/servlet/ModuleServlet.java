package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.Module;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.ModuleService;
import com.erp.util.JSONUtil;
import com.erp.util.SystemConfig;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by wang_ on 2016-07-21.
 */
@WebServlet(name = "ModuleServlet",
        urlPatterns = {"/ModuleServlet"})
public class ModuleServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(ModuleServlet.class);
    private static final long serialVersionUID = 1L;

    public ModuleServlet() {
        super();
    }

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

        String param = request.getParameter("param");
        if (param == null) {
            throw new IllegalArgumentException("the request parameter param is null, please check your request path is correct.");
        }
        String seq = request.getParameter("seq");
        String random_session = (String) request.getSession().getAttribute("random_session");

        if (!"query".equals(param) && (random_session == null || seq == null || !seq.equals(random_session))) {
            throw new IllegalArgumentException("the request is illegal.");
        }
        String responseText = "";
        if ("query".equals(param)) {
            String flag = request.getParameter("flag");
            if (flag == null) {
                throw new IllegalArgumentException("the request parameter flag is null, please check your request path is correct.");
            }
            responseText = queryModules(Boolean.valueOf(flag));
        } else if ("insert".equals(param) || "update".equals(param)
                || "delete".equals(param) || "resume".equals(param)) {
            responseText = addOrUpdateOrDelModule(param, request);
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
    private String queryModules(boolean flag) {
        try {
            logger.info("----------- The Module begin init -----------");
            ModuleService.initModules(flag);
            logger.info("----------- The Module init success -----------");
        } catch (ServiceException e) {
            logger.error("----------- The Module init failure:" + e.getMessage() + " -----------");
            e.printStackTrace();
        }

        int projectId = Integer.valueOf(SystemConfig.getValue("project.id"));
        Set<Module> modules = ModuleService.getModuleByProjectId(projectId);
        JSONArray moduleArray = JSONUtil.parseToJson(modules);

        ResponseBean responseBean = new ResponseBean(moduleArray);
        return responseBean.getResponseArray(false);
    }

    /**
     * @param param
     * @param request
     * @return
     */
    private String addOrUpdateOrDelModule(String param, HttpServletRequest request) {
        boolean success = false;
        String msg = "";
        try {
            if ("insert".equals(param)) {
                Module module = new Module();
                String moduleName = request.getParameter("modulename");
                String moduleurl = request.getParameter("moduleurl");
                String parentId = request.getParameter("parentId");
                String parentType = request.getParameter("parentType");
                String disorder = request.getParameter("disorder");
                String icon = request.getParameter("icon");
                module.setModuleName(moduleName);
                module.setHref(moduleurl);
                module.setParentId(Integer.valueOf(parentId));
                module.setParentType(parentType);
                module.setDisOrder(Integer.valueOf(disorder));
                module.setIcon(icon);
                ModuleService.insertModule(module);
            } else if ("update".equals(param)) {
                String id = request.getParameter("id");
                String text = request.getParameter("modulename");
                String url = request.getParameter("moduleurl");
                String order = request.getParameter("disorder");

                Module module = new Module();
                module.setModuleId(Integer.valueOf(id));
                module.setModuleName(text);
                module.setHref(url);
                module.setDisOrder(Integer.valueOf(order));
                ModuleService.updateModule(module);
            } else if ("delete".equals(param)) {
                String[] ids = request.getParameterValues("ids");
                ModuleService.deleteModule(ids);
            } else if ("resume".equals(param)) {
                String id = request.getParameter("id");
                ModuleService.updateModule(id);
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

}
