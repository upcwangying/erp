package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.Product;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.ProductService;
import com.erp.util.StringUtil;
import net.sf.json.JSONArray;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-08-26.
 */
@WebServlet(name = "ProductServlet",
        urlPatterns = {"/ProductServlet"})
public class ProductServlet extends HttpServlet {
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

        String param = request.getParameter("param");
        String seq = request.getParameter("seq");
        String random_session = (String) request.getSession().getAttribute("random_session");
//        System.out.println("random_session:" + random_session);
//        System.out.println("seq:" + seq);
        String responseText = "";
        PrintWriter writer = response.getWriter();
        if (random_session != null && seq != null && seq.equals(random_session)) {
            if ("query".equals(param)) {
                responseText = queryProduct();
            } else if ("add".equals(param) || "delete".equals(param)
                            || "up".equals(param) || "down".equals(param)) {
                responseText = editProduct(request, param);
            }
        } else {
            responseText = "非法请求路径！！！";
        }

        writer.write(responseText);
        writer.close();
    }

    /**
     *
     * @return
     */
    private String queryProduct() {
        JSONArray array = null;
        try {
            array = ProductService.queryProduct();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(array);
        return responseBean.getResponseArray(true);
    }

    /**
     *
     * @param request
     * @param param
     * @return
     */
    private String editProduct(HttpServletRequest request, String param) {
        boolean success = false;
        String msg = "";
        try {
            if ("add".equals(param)) {
                Product product = new Product();
                String productId = request.getParameter("productId");
                String productName = request.getParameter("productName");
                String productDesc = request.getParameter("productDesc");
                String jldwid = request.getParameter("jldw");
                String price = request.getParameter("price");
                String create_by = request.getParameter("create_by");
                String update_by = request.getParameter("update_by");
                product.setProductId(StringUtil.isEmpty(productId)?0L:Long.valueOf(productId));
                product.setProductName(productName);
                product.setProductDesc(productDesc);
                product.setJldwid(Long.valueOf(jldwid));
                product.setPrice(Double.valueOf(price));
                product.setCreate_StaffId(Long.valueOf(create_by));
                product.setUpdate_staffId(Long.valueOf(update_by));
                ProductService.insertOrUpdateProduct(product);
            } else if ("delete".equals(param)) {
                String[] productId = request.getParameterValues("productId");
                String staffId = request.getParameter("update_by");
                ProductService.deleteProduct(productId, staffId);
            } else if ("up".equals(param) || "down".equals(param)) {
                String[] productId = request.getParameterValues("productId");
                String flag = request.getParameter("flag");
                String staffId = request.getParameter("update_by");
//                System.out.println(Boolean.valueOf(flag));
                ProductService.updateProductValid(productId, staffId, Boolean.valueOf(flag));
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
