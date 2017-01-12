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
	
	
	public static NetUnit getNetUnit(){//����ģʽ
		synchronized (NetUnit.class) {
			if(instance==null){
				instance=new NetUnit();
			}
		}
		return instance;
	}
	/*
	public NetUnit(){}*/
	public String getUrlCotent(URL url) throws IOException{//����url���󣬷�����ҳ����
		String str="";//������ҳ�����ַ���
		InputStream inputStream=(InputStream)url.getContent();//����������ָ��url.getcontent()
		byte[] b=new byte[1024];//�����ֽ�Ϊ1024�Ļ�������
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
	
	public String getUrlHtml(URL url){//����url����htmlҳ��
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
	        // ��ȡ������Ӧͷ�ֶ�  
	        Map<String, List<String>> map = urlConnection.getHeaderFields();  
	        /*
	         * 
	        
	        // �������е���Ӧͷ�ֶ�  
	        for (String key : map.keySet()) {  
	            System.out.println(key + "--->" + map.get(key));  
	        }  
	        
	         */
	        // ����BufferedReader����������ȡURL����Ӧ  
	        in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));  
	        String line;  
	        while ((line = in.readLine()) != null) {  
	            result = result + line;  
	        }  
	    } catch (Exception e) {  
	        System.out.println("����GET��������쳣��" + e);  
	        e.printStackTrace();  
	    }  
	    // ʹ��finally�����ر�������  
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
	
	public void openUrl(String url){//��Ĭ�����������ҳ
		try { 
		    //String url = "http://www.baidu.com"; 
		    //String url = "http://www.jb51.net/"; 
		    java.net.URI uri = java.net.URI.create(url); 
		    // ��ȡ��ǰϵͳ������չ 
		    java.awt.Desktop dp = java.awt.Desktop.getDesktop(); 
		    // �ж�ϵͳ�����Ƿ�֧��Ҫִ�еĹ��� 
		    if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) { 
		     //File file = new File("D:\\aa.txt"); 
		     //dp.edit(file);// ���༭�ļ� 
		      dp.browse(uri);// ��ȡϵͳĬ������������� 
		     // dp.open(file);// ��Ĭ�Ϸ�ʽ���ļ� 
		     // dp.print(file);// �ô�ӡ����ӡ�ļ� 
		    } 
		   } catch (java.lang.NullPointerException e) { 
		    // ��ΪuriΪ��ʱ�׳��쳣 
		    e.printStackTrace(); 
		   } catch (java.io.IOException e) { 
		    // ��Ϊ�޷���ȡϵͳĬ������� 
		    e.printStackTrace(); 
		 } 
	}
	
	public void toSaveFile(URL url,String path) {
		//����url��õ����ݴ浽·��Ϊpath���ļ���
		InputStream inputStream = null;
		try {
			inputStream = (InputStream)url.getContent();
			int max = inputStream.available();//�����ļ�����󳤶�
			File file=new File(path);
			file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] b=new byte[1024];//�����ֽ�Ϊ1024�Ļ�������
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
		System.out.println("�ļ���ַΪ��"+url.getPath());	
	}
	
	/*
	 * @parameter url����Ҫ��ȡҳ���url����
	 * @return ALable��List����
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

                //��ȡ����
                Matcher title=Pattern.compile(">.*?</a>").matcher(mt.group()); 
                while(title.find())
                {
                	String ALableTitle=title.group().replaceAll(">|</a>","");
                	aLable.setTitle(ALableTitle);
                    //System.out.println("����:"+ALableTitle);
                }

                //��ȡ��ַ
                Matcher myurl=Pattern.compile("href=.*?\\s").matcher(mt.group()); 
                while(myurl.find())
                {	String ALableUrl=myurl.group().replaceAll("href=|>","");
                	ALableUrl=ALableUrl.replaceAll("\"","");
                   // System.out.println("��ַ:"+ALableUrl);
                	aLable.setHref(ALableUrl);
                }
                ALableList.add(aLable);
                System.out.println();

                
        }
       
       for (ALable aLable : ALableList) {
    	   System.out.println("��ַ:"+aLable.getHref()+"����:"+aLable.getTitle());
       }
       
       System.out.println(ALableList.size());
       
       System.out.println("����"+i+"�����Ͻ��");
		return ALableList;
		
	}
	
	/**
	 * ���ݹؼ���ɸѡsrc
	 * @param Date
	 * @return
	 */
	public List<ALable> ScreenSrc(List<ALable> ALables,String Date){
		List<ALable> ALableList=new ArrayList<ALable>();
		//System.out.println("ScreenSrc size:"+ALables.size()+"ScreenSrc Date:"+Date);
		for (ALable aLable : ALables) {
			if(aLable.getHref()!=null){//���ж�href�Ƿ�Ϊ��
				if(aLable.getHref().contains(Date)){
					ALableList.add(aLable);
				}
			}
		}
		return ALableList;
	}
	
	/**
	 * ��ȡϵͳ����
	 * @return
	 */
	public String DateToString(){
		Date day=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//�������ڸ�ʽ
		String date=dateFormat.format(day);//��ȡϵͳʱ��
		System.out.println(date);
		return date;
	}
	
	
	/**
	 * ʹ��jsoup������jar������html
	 * artibodyTitle main_title���ı���  	artibody��������
	 * ����url��ȡ���ݲ�����SinaArti����
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
		Elements pngs = artibody.select("img[src$=.png]");//��չ��Ϊ.png��ͼƬ
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
