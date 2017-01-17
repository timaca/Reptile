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

//处理请求的业务逻辑类
public class doGetService {
	NetUnit netUnit=NetUnit.getNetUnit();
	JsonUnit jsonUnit=new JsonUnit();
	public doGetService(){}
	
	/**
	 * 根据请求中参数返回相应的json字符串
	 * @param type相当于SinaArti中的tag属性
	 * type 的值 news 新闻 sports 体育 finance 财经 ent 娱乐 tech 科技  auto 汽车
	 * @return
	 */
	public String WhichType(String key,String type,String ip,String num){
		String json=null;
		SinaArti sinaArti=null;
                Log log=new Log();
                String result="";
		System.out.println("WhichType key:"+key);
                //tech默认tech
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
                                
                                result="当前IP请求超过限制";
                                json=jsonUnit.SinaArtiToJson(sinaArti, 10002);
                            }else{
                                times++;
                                user.setTimes(times);
                                DbUnit.getDbUnit().UpdateObject(user);
                                List<SinaArti> sinaArtis=DbUnit.getDbUnit().RetrieveSinaArti(Integer.parseInt(num), 0);
                                result="成功的返回";
                                for (SinaArti sinaArti1 : sinaArtis) {
                                    json=json+jsonUnit.SinaArtiToJson(sinaArti1, 1);
                                }
				
                            }
				
			}else{
				json=jsonUnit.SinaArtiToJson(sinaArti, 10001);
                                result="错误的请求key";
			}
                        log.setResult(result);
                        DbUnit.getDbUnit().CreateObject(log);
		}
		return json;
	}
	
	/**
	 * 
	 * @param url 需要爬的地址
	 * @param date需要爬的日期    2017-01-15
	 * @param initialDelay 首次执行的延时时间（单位秒）
	 * @param period  定时执行的间隔时间（单位秒）
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
			System.out.println("经筛选还有："+AfterALableList.size()+"篇文章");
			//添加日志
		    ReptileLog reptileLog=new ReptileLog();
		    reptileLog.setDate(NetUnit.getNetUnit().DateToString());
		    reptileLog.setResult("doGetService ReptileTimer 经筛选还有："+AfterALableList.size()+"篇文章");
		    DbUnit.getDbUnit().CreateObject(reptileLog);
		    
			for (ALable aLable : AfterALableList) {
				//System.out.println("main href:"+aLable.getHref()+" main title:"+aLable.getTitle());
				sinaArtis.add(netUnit.HtmlToObject(new URL(aLable.getHref()),tag));
			}
		} catch (Exception e) {
			System.out.println("doGetService ReptileTimer 捕获异常：");
			e.printStackTrace();
			
			//添加日志
		    ReptileLog reptileLog=new ReptileLog();
		    reptileLog.setDate(NetUnit.getNetUnit().DateToString());
		    reptileLog.setResult("doGetService ReptileTimer 捕获异常："+e.toString());
		    DbUnit.getDbUnit().CreateObject(reptileLog);
		}
		
		Runnable runnable = new Runnable() {  
	           public void run() {
                       String result="开始爬虫（当前文章数"+DbUnit.getDbUnit().CountArt()+"）:"+new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(new Date());
	               System.out.println(result);  
	                //添加日志
		           		    ReptileLog reptileLog=new ReptileLog();
		           		    reptileLog.setDate(NetUnit.getNetUnit().DateToString());
		           		    reptileLog.setResult(result);
		           		    DbUnit.getDbUnit().CreateObject(reptileLog);
                       for (SinaArti Arti : sinaArtis) {
	            	   if(DbUnit.getDbUnit().RetrieveSinaArtiByTitle(Arti.getTitle())==null){
	            		   result="CreateObject 添加文章："+DbUnit.getDbUnit().CreateObject(Arti);
	            		   System.out.println(result);
	            		   
	            		 //添加日志
		           		    ReptileLog reptileLog1=new ReptileLog();
		           		    reptileLog1.setDate(NetUnit.getNetUnit().DateToString());
		           		    reptileLog1.setResult(result);
		           		    DbUnit.getDbUnit().CreateObject(reptileLog1);
	            	   }else{
	            		   result="UpdateObject 更新文章："+DbUnit.getDbUnit().UpdateObject(Arti);
                                   System.out.println(result);
                                   //添加日志
		           		    ReptileLog reptileLog1=new ReptileLog();
		           		    reptileLog1.setDate(NetUnit.getNetUnit().DateToString());
		           		    reptileLog1.setResult(result);
		           		    DbUnit.getDbUnit().CreateObject(reptileLog1);
	            	   }
	            	   
	               }
	         
	               result="爬虫结束:（当前文章数"+DbUnit.getDbUnit().CountArt()+"）"+new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(new Date());
	             //添加日志
          		    ReptileLog reptileLog2=new ReptileLog();
          		    reptileLog2.setDate(NetUnit.getNetUnit().DateToString());
          		    reptileLog2.setResult(result);
          		    DbUnit.getDbUnit().CreateObject(reptileLog2);
	           }  
	       };  
	       ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
	       // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间 
	       service.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);  
	}
        
        /**
         * 获取用户ip   
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
    
    /**jquery获取ip源码
     * $(document).ready(function(){
         var pattern = /(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)/;
       
        var str = document.getElementsByTagName("center")[0].innerHTML;
        var reg = new RegExp(pattern, "g");
        alert(str.match(reg));
       // alert(pattern.match(str));
    }); 
     */
    /**
     * 通过第三方查询ip服务获取ip
     * @throws Exception 
     */
    public String GetIpByIP138() throws Exception{
        String ip="找不到ip";
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
     * 重置USER表times属性值
     */
    public void UserTimesTimer(long initialDelay,int period){
        Runnable runnable = new Runnable() {
            public void run() {
                 List<User> users=DbUnit.getDbUnit().RetrieveUser();
                for (User user : users) {
                    user.setTimes(0);
                    DbUnit.getDbUnit().UpdateObject(user);
                }
                 //添加日志
                 String result="重置ip/hour请求次数";
          	ReptileLog reptileLog=new ReptileLog();
          	 reptileLog.setDate(NetUnit.getNetUnit().DateToString());
          	 reptileLog.setResult(result);
          	 DbUnit.getDbUnit().CreateObject(reptileLog);
                
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
	// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间 
	service.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);  
    }
}
