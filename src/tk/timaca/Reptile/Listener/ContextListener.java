package tk.timaca.Reptile.Listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import tk.timaca.Reptile.Service.NetUnit;

import tk.timaca.Reptile.Service.doGetService;

//ServletÕìÌýÆ÷
public class ContextListener extends HttpServlet implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContextListener.super.contextDestroyed(sce);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		new doGetService().ReptileTimer("http://tech.sina.com.cn//","tech",NetUnit.getNetUnit().DataToString(), 10, 3600);
                new doGetService().UserTimesTimer(10, 3600);
	}
	
}
