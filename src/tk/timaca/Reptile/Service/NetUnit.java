package tk.timaca.Reptile.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tk.timaca.Reptile.Bean.ALable;

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
	
	public static void main(String[] args) throws MalformedURLException{
		
		String str="<link rel='icon' sizes='any' mask href='//www.sina.com.cn/favicon.svg'>"+
					"<meta name='theme-color' content='red'>"+
					"<link rel='icon' type='image/x-icon' href='//www.sina.com.cn/favicon.ico' /> "+
					
						"<meta name='viewport' content='width=1024' />"+
						"<meta name='publishid' content='2,823,1'>"+
						"<meta name='stencil' content='PGLS000027'>"+
						"<meta name='verify-v1' content='6HtwmypggdgP1NLw7NOuQBI2TW8+CfkYCoyeB8IDbn8=' />"+
						"<meta content='width=1024,maximum-scale=2.0' name='viewport'/>"+
						"<link rel='stylesheet' href='//n3.sinaimg.cn/tech/https_index/feed_tech.min.css' type='text/css' />"+
						"<!-- ��ͨ css -->"+
						"<link rel='stylesheet' type='text/css' href='//n3.sinaimg.cn/tech/https_index/top.css'>"+
						"<!-- ��¼ css -->"+
						"<link rel='stylesheet' type='text/css' href='//i.sso.sina.com.cn/css/userpanel/v1/top_account_v2.css'>"+
						"<!-- ��¼Ƥ��css -->"+
						"<link rel='stylesheet' type='text/css' href='//n2.sinaimg.cn/tech/https_index/comment.css'>"+
						"<link rel='stylesheet' href='//n2.sinaimg.cn/common/channelnav/css/common_nav.css' type='text/css'>"+
						"<!-- <link rel='stylesheet' href='//n2.sinaimg.cn/tech/index16/01/tech_index.css?ver=1482050345' type='text/css' /> -->"+
						"<link rel='stylesheet' href='//n2.sinaimg.cn/tech/https_index/tech_index.css' type='text/css' />";
		
		//NetUnit netUnit=new NetUnit();
		NetUnit netUnit=NetUnit.getNetUnit();
		List<ALable> ALableList=new ArrayList<ALable>();
		URL url;
		try {
			url = new URL("http://tech.sina.com.cn//");
			//url = new URL("http://www.baidu.com//");
			//str=netUnit.getUrlCotent(url);
			//netUnit.toSaveFile(url, "sina.html");
			//str=netUnit.getUrlHtml(url);
			//System.out.println(str);
			ALableList=netUnit.CaptureLink(url);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int index=(int)(Math.random()*408);
		ALable aLable=ALableList.get(index);
		String SonUrl=aLable.getHref();
		String SonHtml=netUnit.getUrlHtml(new URL(SonUrl));
		System.out.println(SonHtml);
		//str="href='//n2.sinaimg.cn/common/channelnav/css/common_nav.css'";
		//str="Chapter 3";
		/*
		 * 
		
		//<a\shref=\"(https?://[^"]+)\"[^>]*>([^<]+)<\/a>
		String string="null";
		Pattern pattern=Pattern.compile("<a.*?/a>");
		Matcher matcher=pattern.matcher(str);
		System.out.println(matcher.find());
		string=matcher.group();
		System.out.println("����ƥ�䵽ʲô��:"+string);
		*/
		
	}
}
