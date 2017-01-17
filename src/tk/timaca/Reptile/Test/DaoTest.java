package tk.timaca.Reptile.Test;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tk.timaca.Reptile.Bean.ALable;
import tk.timaca.Reptile.Bean.SinaArti;
import tk.timaca.Reptile.Dao.DbUnit;
import tk.timaca.Reptile.Service.NetUnit;

public class DaoTest {
	public DaoTest() {
	}
	
	public List<SinaArti> net(){
		NetUnit netUnit=NetUnit.getNetUnit();
		List<ALable> ALableList=new ArrayList<ALable>();
		List<SinaArti> sinaArtis=new ArrayList<SinaArti>();
		URL url;
		try {
			url = new URL("http://tech.sina.com.cn//");
			//url = new URL("http://www.baidu.com//");
			ALableList=netUnit.CaptureLink(url);
			//System.out.println("MAIN SIZE:"+ALableList.size());
			List<ALable> AfterALableList=netUnit.ScreenSrc(ALableList, "2017-01-15");
			System.out.println("net:"+AfterALableList.size());
			for (ALable aLable : AfterALableList) {
				//System.out.println("main href:"+aLable.getHref()+" main title:"+aLable.getTitle());
				sinaArtis.add(netUnit.HtmlToObject(new URL(aLable.getHref()),"tech"));
			}
		} catch (Exception e) {
			System.out.println("DaoTest net 捕获异常：");
			e.printStackTrace();
		}
		return sinaArtis;
	}
	
	/** 
	 *  
	 *  
	 * ScheduledExecutorService是从Java SE5的java.util.concurrent里，做为并发工具类被引进的，这是最理想的定时任务实现方式。  
	 * 相比于上两个方法，它有以下好处： 
	 * 1>相比于Timer的单线程，它是通过线程池的方式来执行任务的  
	 * 2>可以很灵活的去设定第一次执行任务delay时间 
	 * 3>提供了良好的约定，以便设定执行的时间间隔 
	 *  
	 * 下面是实现代码，我们通过ScheduledExecutorService#scheduleAtFixedRate展示这个例子，通过代码里参数的控制，首次执行加了delay时间。 
	 *  
	 *  
	 * @author GT 
	 *  
	 */  
	 public static void main(String[] args) {  
		 //Date day=new Date();
		//SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");//设置日期格式
		//String date=new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(new Date());//获取系统时间
		 DaoTest daoTest=new DaoTest();
		 
	       Runnable runnable = new Runnable() {  
	           public void run() {  
	               System.out.println("开始爬虫:"+new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(new Date()));  
	               List<SinaArti> sinaArtis=daoTest.net();
	               
	               for (SinaArti Arti : sinaArtis) {
	            	   if(DbUnit.getDbUnit().RetrieveSinaArtiByTitle(Arti.getTitle())==null){
	            		   System.out.println("CreateObject 添加文章："+DbUnit.getDbUnit().CreateObject(Arti));
	            	   }else{
	            		   //System.out.println("UpdateObject 更新文章："+DbUnit.getDbUnit().UpdateObject(Arti));
	            	   }
	            	   
	               }
	               System.out.println("爬虫结束:"+new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(new Date()));
	           }  
	       };  
	       ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
	       // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间 
	       service.scheduleAtFixedRate(runnable, 10, 3600*2, TimeUnit.SECONDS);  
	       
	}  
}
