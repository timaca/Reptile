package tk.timaca.Reptile.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tk.timaca.Reptile.Bean.ALable;
import tk.timaca.Reptile.Bean.SinaArti;

public class NetUnit {
	
	private static NetUnit instance=null;
	
	private NetUnit(){}
	
	
	public static NetUnit getNetUnit(){//单例模式
		synchronized (NetUnit.class) {
			if(instance==null){
				instance=new NetUnit();
			}
		}
		return instance;
	}
	/*
	public NetUnit(){}*/
	public String getUrlCotent(URL url) throws IOException{//传入url对象，返回网页内容
		String str="";//定义网页内容字符串
		InputStream inputStream=(InputStream)url.getContent();//定义输入流指向url.getcontent()
		byte[] b=new byte[1024];//定义字节为1024的缓存数组
		int max=inputStream.available();
		while(max>0){
			inputStream.read(b);
			str=str+new String(b,"utf8");
			max=max-1024;
		}
		inputStream.close();
		//System.out.println(str);
		return str;
	}
	
	public String getUrlHtml(URL url){//根据url返回html页面
		String result="";
		InputStream inputStream=null;
		URLConnection urlConnection;
		BufferedReader in = null;
		try {
			urlConnection = url.openConnection();
			inputStream=urlConnection.getInputStream();
			//urlConnection.setRequestProperty("accept", "*/*");  
			//urlConnection.setRequestProperty("connection", "Keep-Alive");  
			//urlConnection.setRequestProperty("user-agent",  
            //        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
			urlConnection.connect();  
	        // 获取所有响应头字段  
	        Map<String, List<String>> map = urlConnection.getHeaderFields();  
	        /*
	         * 
	        
	        // 遍历所有的响应头字段  
	        for (String key : map.keySet()) {  
	            System.out.println(key + "--->" + map.get(key));  
	        }  
	        
	         */
	        // 定义BufferedReader输入流来读取URL的响应  
	        in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));  
	        String line;  
	        while ((line = in.readLine()) != null) {  
	            result = result + line;  
	        }  
	    } catch (Exception e) {  
	        System.out.println("发送GET请求出现异常！" + e);  
	        e.printStackTrace();  
	    }  
	    // 使用finally块来关闭输入流  
	    finally {  
	        try {  
	            if (in != null) {  
	                in.close();  
	            }  
	        } catch (IOException ex) {  
	            ex.printStackTrace();  
	        }  
	    }  
	    return result;  
	}
	
	public void openUrl(String url){//用默认浏览器打开网页
		try { 
		    //String url = "http://www.baidu.com"; 
		    //String url = "http://www.jb51.net/"; 
		    java.net.URI uri = java.net.URI.create(url); 
		    // 获取当前系统桌面扩展 
		    java.awt.Desktop dp = java.awt.Desktop.getDesktop(); 
		    // 判断系统桌面是否支持要执行的功能 
		    if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) { 
		     //File file = new File("D:\\aa.txt"); 
		     //dp.edit(file);// 　编辑文件 
		      dp.browse(uri);// 获取系统默认浏览器打开链接 
		     // dp.open(file);// 用默认方式打开文件 
		     // dp.print(file);// 用打印机打印文件 
		    } 
		   } catch (java.lang.NullPointerException e) { 
		    // 此为uri为空时抛出异常 
		    e.printStackTrace(); 
		   } catch (java.io.IOException e) { 
		    // 此为无法获取系统默认浏览器 
		    e.printStackTrace(); 
		 } 
	}
	
	public void toSaveFile(URL url,String path) {
		//将从url获得的内容存到路径为path的文件中
		InputStream inputStream = null;
		try {
			inputStream = (InputStream)url.getContent();
			int max = inputStream.available();//定义文件的最大长度
			File file=new File(path);
			file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] b=new byte[1024];//定义字节为1024的缓存数组
			while(max>0){
				inputStream.read(b);
				fileOutputStream.write(b);
				max=max-1024;
			}
			inputStream.close();
			fileOutputStream.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("文件地址为："+url.getPath());	
	}
	
	/*
	 * @parameter url：需要爬取页面的url对象
	 * @return ALable的List集合
	 */
	public List<ALable> CaptureLink(URL url){
		List<ALable> ALableList=new ArrayList<ALable>();
		NetUnit netUnit=NetUnit.getNetUnit();
		String html=netUnit.getUrlHtml(url);
		int i=0;
		String regex ="<a\\shref=.*?/a>";
		//String regex="<a\\shref=\\'(http?://[^']+)\\'[^>]*>([^<]+)<\\/a>";
        //String regex ="http://.*?>";
       Pattern pt=Pattern.compile(regex);
       Matcher mt=pt.matcher(html);
       while(mt.find())
        {
    	   		ALable aLable=new ALable();
                System.out.println(mt.group());
                i++;

                //获取标题
                Matcher title=Pattern.compile(">.*?</a>").matcher(mt.group()); 
                while(title.find())
                {
                	String ALableTitle=title.group().replaceAll(">|</a>","");
                	aLable.setTitle(ALableTitle);
                    //System.out.println("标题:"+ALableTitle);
                }

                //获取网址
                Matcher myurl=Pattern.compile("href=.*?\\s").matcher(mt.group()); 
                while(myurl.find())
                {	String ALableUrl=myurl.group().replaceAll("href=|>","");
                	ALableUrl=ALableUrl.replaceAll("\"","");
                   // System.out.println("网址:"+ALableUrl);
                	aLable.setHref(ALableUrl);
                }
                ALableList.add(aLable);
                System.out.println();

                
        }
       
       for (ALable aLable : ALableList) {
    	   System.out.println("网址:"+aLable.getHref()+"标题:"+aLable.getTitle());
       }
       
       System.out.println(ALableList.size());
       
       System.out.println("共有"+i+"个符合结果");
		return ALableList;
		
	}
	
	/**
	 * 根据关键字筛选src
	 * @param Date
	 * @return
	 */
	public List<ALable> ScreenSrc(List<ALable> ALables,String Date){
		List<ALable> ALableList=new ArrayList<ALable>();
		//System.out.println("ScreenSrc size:"+ALables.size()+"ScreenSrc Date:"+Date);
		for (ALable aLable : ALables) {
			if(aLable.getHref()!=null){//先判断href是否为空
				if(aLable.getHref().contains(Date)){
					ALableList.add(aLable);
				}
			}
		}
		return ALableList;
	}
	
	/**
	 * 获取系统日期
	 * @return
	 */
	public String DateToString(){
		Date day=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//设置日期格式
		String date=dateFormat.format(day);//获取系统时间
		System.out.println(date);
		return date;
	}
	
	
	/**
	 * 使用jsoup第三方jar包解析html
	 * artibodyTitle main_title正文标题  	artibody正文内容
	 * 根据url获取内容并返回SinaArti对象
	 * @param url
	 * @return
	 */
	public SinaArti HtmlToObject(URL url){
		SinaArti sinaArti = null;
		NetUnit netUnit=NetUnit.getNetUnit();
		String html=netUnit.getUrlHtml(url);
		Document document=Jsoup.parse(html);
		Element artibody=document.getElementById("artibody");
		//netUnit.ParseObject(artibody);
		//Element artibodyTitle=document.getElementById("artibodyTitle");
		Element artibodyTitle=document.getElementById("main_title");
		//Element pub_date=document.getElementById("pub_date");
		Element pub_date=document.select(".titer").first();
		Elements art_keywords=document.select(".art_keywords a");
		for (Element art_keyword : art_keywords) {
			System.out.println("art_keywords:"+art_keyword.text());
		}
		Elements pngs = artibody.select("img[src$=.png]");//扩展名为.png的图片
		for (Element png : pngs) {
			System.out.println("pngs:"+png.attr("src"));
		}
		System.out.println("HtmlToObject pub_date:"+pub_date.text()+"artibodyTitle:"+artibodyTitle.text());
		System.out.println("context:"+artibody.html());
		return sinaArti;
	}
	
	
	public void ParseObject(Object obj){
		Class o=obj.getClass();
		System.out.println("ParseObject name:"+o.getName());
		Field[] fields=o.getFields();
		for (Field field : fields) {
			System.out.println(field.getName());
		}
	}
	
	public static void main(String[] args) throws MalformedURLException{
		NetUnit netUnit=NetUnit.getNetUnit();
		//NetUnit netUnit=new NetUnit();
		/*
		List<ALable> ALableList=new ArrayList<ALable>();
		URL url;
		try {
			url = new URL("http://tech.sina.com.cn//");
			//url = new URL("http://www.baidu.com//");
			ALableList=netUnit.CaptureLink(url);
			System.out.println("MAIN SIZE:"+ALableList.size());
			List<ALable> AfterALableList=netUnit.ScreenSrc(ALableList, netUnit.DateToString());
			for (ALable aLable : AfterALableList) {
				System.out.println("main href:"+aLable.getHref()+" main title:"+aLable.getTitle());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		String src="http://tech.sina.com.cn/d/v/2017-01-11/doc-ifxzkfuh6849281.shtml";
		netUnit.HtmlToObject(new URL(src));
	}
}
