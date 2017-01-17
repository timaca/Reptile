package tk.timaca.Reptile.Service;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import tk.timaca.Reptile.Bean.SinaArti;

/*
 * json工具类
 */
public class JsonUnit {
	public JsonUnit(){}
	
	/**
	 * 根据sinaArti生成json
		 * {
			    "result": {
			        "stat": 1,
			        "data": {
			            "date": "2017年01月12日 08:40",
			            "author_name": "新浪科技",
			            "realtype": "近地",
			            "thumbnail_pic_s": "http://n.sinaimg.cn/tech/transform/20170112/DTd7-fxzqnip0762009.jpg",
			            "thumbnail_pic_s03": "http://n.sinaimg.cn/tech/transform/20170112/hkSA-fxzkssy2144554.jpg",
			            "thumbnail_pic_s02": "http://n.sinaimg.cn/tech/transform/20170112/jxIw-fxzqnkq8855933.jpg",
			            "type": "小行星",
			            "title": "直径30米小行星刚从地球附近飞过：飞行速度达每秒16公里",
			            "url": "http://tech.sina.com.cn/d/s/2017-01-12/doc-ifxzqnim3988020.shtml"
			        }
			    },
			    "reason": "成功的返回"
			}
	 * @param sinaArti 
	 * stat 返回码  1 成功的返回  	10001  错误的请求KEY  10002  当前IP请求超过限制  		10003 	接口维护 
	 * @return
	 */
	public String SinaArtiToJson(SinaArti sinaArti,int stat){
		String reason = null;//返回结果
		String json = null;
		JSONObject jsonObject=new JSONObject();
		JSONObject result=new JSONObject();
		if(stat==1){
			reason="成功的返回";
			JSONObject data=new JSONObject(sinaArti);
			result.append("data", data);
		}else if(stat==10001){
			reason="错误的请求KEY";
		}else if(stat==10002){
			reason="当前IP请求超过限制";
		}else if(stat==10003){
			reason="接口维护";
		}else{}
		jsonObject.append("reason", reason);
		result.append("stat", stat);
		jsonObject.append("result", result);
		json=jsonObject.toString();
		json=json.replaceAll("[\\[\\]]", "");
		System.out.println("SinaArtiToJson json:"+json);
		return json;
	}
	
	/**
	 * 解析请求的json
	 */
	public void ParseJson(String json){
		
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, MalformedURLException{
		NetUnit netUnit=NetUnit.getNetUnit();
		String src="http://tech.sina.com.cn/d/s/2017-01-12/doc-ifxzqnim3988020.shtml";
		SinaArti sinaArti=netUnit.HtmlToObject(new URL(src),"tech");
		new JsonUnit().SinaArtiToJson(sinaArti,1);
	}
}
