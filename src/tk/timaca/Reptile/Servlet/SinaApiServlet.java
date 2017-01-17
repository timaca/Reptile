package tk.timaca.Reptile.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tk.timaca.Reptile.Service.doGetService;

public class SinaApiServlet extends HttpServlet{
        private doGetService doGetService=new doGetService();
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String key=request.getParameter("key");//请求密钥
                String type=request.getParameter("type");//请求类型
                String num=request.getParameter("num");//请求数量
                System.out.println("doGet key:"+key);
                //获取请求ip
                String ip= doGetService.GetIpByIP138();
                System.out.println("doGet ip:"+ip);
                response.setCharacterEncoding("utf-8");
                PrintWriter printWriter=response.getWriter();
                printWriter.print(doGetService.WhichType(key, type, ip, num));
            } catch (Exception ex) {
                Logger.getLogger(SinaApiServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
}
