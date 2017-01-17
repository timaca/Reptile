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
			System.out.println("DaoTest net �����쳣��");
			e.printStackTrace();
		}
		return sinaArtis;
	}
	
	/** 
	 *  
	 *  
	 * ScheduledExecutorService�Ǵ�Java SE5��java.util.concurrent���Ϊ���������౻�����ģ�����������Ķ�ʱ����ʵ�ַ�ʽ��  
	 * ������������������������ºô��� 
	 * 1>�����Timer�ĵ��̣߳�����ͨ���̳߳صķ�ʽ��ִ�������  
	 * 2>���Ժ�����ȥ�趨��һ��ִ������delayʱ�� 
	 * 3>�ṩ�����õ�Լ�����Ա��趨ִ�е�ʱ���� 
	 *  
	 * ������ʵ�ִ��룬����ͨ��ScheduledExecutorService#scheduleAtFixedRateչʾ������ӣ�ͨ������������Ŀ��ƣ��״�ִ�м���delayʱ�䡣 
	 *  
	 *  
	 * @author GT 
	 *  
	 */  
	 public static void main(String[] args) {  
		 //Date day=new Date();
		//SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");//�������ڸ�ʽ
		//String date=new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(new Date());//��ȡϵͳʱ��
		 DaoTest daoTest=new DaoTest();
		 
	       Runnable runnable = new Runnable() {  
	           public void run() {  
	               System.out.println("��ʼ����:"+new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(new Date()));  
	               List<SinaArti> sinaArtis=daoTest.net();
	               
	               for (SinaArti Arti : sinaArtis) {
	            	   if(DbUnit.getDbUnit().RetrieveSinaArtiByTitle(Arti.getTitle())==null){
	            		   System.out.println("CreateObject ������£�"+DbUnit.getDbUnit().CreateObject(Arti));
	            	   }else{
	            		   //System.out.println("UpdateObject �������£�"+DbUnit.getDbUnit().UpdateObject(Arti));
	            	   }
	            	   
	               }
	               System.out.println("�������:"+new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(new Date()));
	           }  
	       };  
	       ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
	       // �ڶ�������Ϊ�״�ִ�е���ʱʱ�䣬����������Ϊ��ʱִ�еļ��ʱ�� 
	       service.scheduleAtFixedRate(runnable, 10, 3600*2, TimeUnit.SECONDS);  
	       
	}  
}
