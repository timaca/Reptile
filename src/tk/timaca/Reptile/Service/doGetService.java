package tk.timaca.Reptile.Service;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tk.timaca.Reptile.Bean.ALable;
import tk.timaca.Reptile.Bean.ReptileLog;
import tk.timaca.Reptile.Bean.SinaArti;
import tk.timaca.Reptile.Bean.User;
import tk.timaca.Reptile.Bean.Log;
import tk.timaca.Reptile.Dao.DbUnit;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//���������ҵ���߼���
public class doGetService {
	NetUnit netUnit=NetUnit.getNetUnit();
	JsonUnit jsonUnit=new JsonUnit();
	public doGetService(){}
	
	/**
	 * ���������в���������Ӧ��json�ַ���
	 * @param type�൱��SinaArti�е�tag����
	 * type ��ֵ news ���� sports ���� finance �ƾ� ent ���� tech �Ƽ�  auto ����
	 * @return
	 */
	public String WhichType(String key,String type,String ip,String num){
		String json=null;
		SinaArti sinaArti=null;
                Log log=new Log();
                String result="";
		System.out.println("WhichType key:"+key);
                //techĬ��tech
                if(type==null||num==null){
                    type="tech";
                    num="1";
                }
                if(type.equals("tech")||type.equals("news")||type.equals("sport")||type.equals("finance")||type.equals("ent")||type.equals("auto")){
                    type="tech";
                }
		if(key!=null&&ip!=null){
                    
                    User user=DbUnit.getDbUnit().RetrieveUserByKey(key);
                    log.setNum(Integer.parseInt(num));
                    log.setType(type);
                    log.setDate(NetUnit.getNetUnit().DateToString());
                    log.setUser(user);
			if((user.getIp()).equals(ip)){
                            int times=user.getTimes();
                            if(times>50){
                                
                                result="��ǰIP���󳬹�����";
                                json=jsonUnit.SinaArtiToJson(sinaArti, 10002);
                            }else{
                                times++;
                                user.setTimes(times);
                                DbUnit.getDbUnit().UpdateObject(user);
                                List<SinaArti> sinaArtis=DbUnit.getDbUnit().RetrieveSinaArti(Integer.parseInt(num), 0);
                                result="�ɹ��ķ���";
                                for (SinaArti sinaArti1 : sinaArtis) {
                                    json=json+jsonUnit.SinaArtiToJson(sinaArti1, 1);
                                }
				
                            }
				
			}else{
				json=jsonUnit.SinaArtiToJson(sinaArti, 10001);
                                result="���������key";
			}
                        log.setResult(result);
                        DbUnit.getDbUnit().CreateObject(log);
		}
		return json;
	}
	
	/**
	 * 
	 * @param url ��Ҫ���ĵ�ַ
	 * @param date��Ҫ��������    2017-01-15
	 * @param initialDelay �״�ִ�е���ʱʱ�䣨��λ�룩
	 * @param period  ��ʱִ�еļ��ʱ�䣨��λ�룩
	 */
	public void ReptileTimer(String urlstr,String tag,String date,long initialDelay,long period){
		List<ALable> ALableList=new ArrayList<ALable>();
		List<SinaArti> sinaArtis=new ArrayList<SinaArti>();
		URL url;
		try {
			url = new URL(urlstr);
			//url = new URL("http://www.baidu.com//");
			ALableList=netUnit.CaptureLink(url);
			//System.out.println("MAIN SIZE:"+ALableList.size());
			List<ALable> AfterALableList=netUnit.ScreenSrc(ALableList, date);
			System.out.println("��ɸѡ���У�"+AfterALableList.size()+"ƪ����");
			//�����־
		    ReptileLog reptileLog=new ReptileLog();
		    reptileLog.setDate(NetUnit.getNetUnit().DateToString());
		    reptileLog.setResult("doGetService ReptileTimer ��ɸѡ���У�"+AfterALableList.size()+"ƪ����");
		    DbUnit.getDbUnit().CreateObject(reptileLog);
		    
			for (ALable aLable : AfterALableList) {
				//System.out.println("main href:"+aLable.getHref()+" main title:"+aLable.getTitle());
				sinaArtis.add(netUnit.HtmlToObject(new URL(aLable.getHref()),tag));
			}
		} catch (Exception e) {
			System.out.println("doGetService ReptileTimer �����쳣��");
			e.printStackTrace();
			
			//�����־
		    ReptileLog reptileLog=new ReptileLog();
		    reptileLog.setDate(NetUnit.getNetUnit().DateToString());
		    reptileLog.setResult("doGetService ReptileTimer �����쳣��"+e.toString());
		    DbUnit.getDbUnit().CreateObject(reptileLog);
		}
		
		Runnable runnable = new Runnable() {  
	           public void run() {
                       String result="��ʼ���棨��ǰ������"+DbUnit.getDbUnit().CountArt()+"��:"+new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(new Date());
	               System.out.println(result);  
	                //�����־
		           		    ReptileLog reptileLog=new ReptileLog();
		           		    reptileLog.setDate(NetUnit.getNetUnit().DateToString());
		           		    reptileLog.setResult(result);
		           		    DbUnit.getDbUnit().CreateObject(reptileLog);
                       for (SinaArti Arti : sinaArtis) {
	            	   if(DbUnit.getDbUnit().RetrieveSinaArtiByTitle(Arti.getTitle())==null){
	            		   result="CreateObject ������£�"+DbUnit.getDbUnit().CreateObject(Arti);
	            		   System.out.println(result);
	            		   
	            		 //�����־
		           		    ReptileLog reptileLog1=new ReptileLog();
		           		    reptileLog1.setDate(NetUnit.getNetUnit().DateToString());
		           		    reptileLog1.setResult(result);
		           		    DbUnit.getDbUnit().CreateObject(reptileLog1);
	            	   }else{
	            		   result="UpdateObject �������£�"+DbUnit.getDbUnit().UpdateObject(Arti);
                                   System.out.println(result);
                                   //�����־
		           		    ReptileLog reptileLog1=new ReptileLog();
		           		    reptileLog1.setDate(NetUnit.getNetUnit().DateToString());
		           		    reptileLog1.setResult(result);
		           		    DbUnit.getDbUnit().CreateObject(reptileLog1);
	            	   }
	            	   
	               }
	         
	               result="�������:����ǰ������"+DbUnit.getDbUnit().CountArt()+"��"+new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(new Date());
	             //�����־
          		    ReptileLog reptileLog2=new ReptileLog();
          		    reptileLog2.setDate(NetUnit.getNetUnit().DateToString());
          		    reptileLog2.setResult(result);
          		    DbUnit.getDbUnit().CreateObject(reptileLog2);
	           }  
	       };  
	       ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
	       // �ڶ�������Ϊ�״�ִ�е���ʱʱ�䣬����������Ϊ��ʱִ�еļ��ʱ�� 
	       service.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);  
	}
        
        /**
         * ��ȡ�û�ip   
         * @param request
         * @return 
         */
    public  String getUserIp(HttpServletRequest request)
    {
        String ip = request.getHeader("Cdn-Src-Ip");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("X-Forwarded-For");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("X-Real-IP");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        if(ip.indexOf(",") > -1)
            ip = ip.substring(0, ip.indexOf(","));
        return ip;
    }
    
    /**jquery��ȡipԴ��
     * $(document).ready(function(){
         var pattern = /(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)/;
       
        var str = document.getElementsByTagName("center")[0].innerHTML;
        var reg = new RegExp(pattern, "g");
        alert(str.match(reg));
       // alert(pattern.match(str));
    }); 
     */
    /**
     * ͨ����������ѯip�����ȡip
     * @throws Exception 
     */
    public String GetIpByIP138() throws Exception{
        String ip="�Ҳ���ip";
        NetUnit netUnit=NetUnit.getNetUnit();
        String contentString=netUnit.getUrlCotent(new URL("http://www.1356789.com/"));
        //System.out.println("contentString:"+contentString);
	String pattern = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

	Pattern r = Pattern.compile(pattern);
	Matcher m = r.matcher(contentString);
	if(m.find()){
            ip=m.group();
        }
        
        return ip;
    }
    /**
     * ����USER��times����ֵ
     */
    public void UserTimesTimer(long initialDelay,int period){
        Runnable runnable = new Runnable() {
            public void run() {
                 List<User> users=DbUnit.getDbUnit().RetrieveUser();
                for (User user : users) {
                    user.setTimes(0);
                    DbUnit.getDbUnit().UpdateObject(user);
                }
                 //�����־
                 String result="����ip/hour�������";
          	ReptileLog reptileLog=new ReptileLog();
          	 reptileLog.setDate(NetUnit.getNetUnit().DateToString());
          	 reptileLog.setResult(result);
          	 DbUnit.getDbUnit().CreateObject(reptileLog);
                
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
	// �ڶ�������Ϊ�״�ִ�е���ʱʱ�䣬����������Ϊ��ʱִ�еļ��ʱ�� 
	service.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);  
    }
}
