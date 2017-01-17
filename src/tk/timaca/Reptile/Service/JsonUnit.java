package tk.timaca.Reptile.Service;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import tk.timaca.Reptile.Bean.SinaArti;

/*
 * json������
 */
public class JsonUnit {
	public JsonUnit(){}
	
	/**
	 * ����sinaArti����json
		 * {
			    "result": {
			        "stat": 1,
			        "data": {
			            "date": "2017��01��12�� 08:40",
			            "author_name": "���˿Ƽ�",
			            "realtype": "����",
			            "thumbnail_pic_s": "http://n.sinaimg.cn/tech/transform/20170112/DTd7-fxzqnip0762009.jpg",
			            "thumbnail_pic_s03": "http://n.sinaimg.cn/tech/transform/20170112/hkSA-fxzkssy2144554.jpg",
			            "thumbnail_pic_s02": "http://n.sinaimg.cn/tech/transform/20170112/jxIw-fxzqnkq8855933.jpg",
			            "type": "С����",
			            "title": "ֱ��30��С���Ǹմӵ��򸽽��ɹ��������ٶȴ�ÿ��16����",
			            "url": "http://tech.sina.com.cn/d/s/2017-01-12/doc-ifxzqnim3988020.shtml"
			        }
			    },
			    "reason": "�ɹ��ķ���"
			}
	 * @param sinaArti 
	 * stat ������  1 �ɹ��ķ���  	10001  ���������KEY  10002  ��ǰIP���󳬹�����  		10003 	�ӿ�ά�� 
	 * @return
	 */
	public String SinaArtiToJson(SinaArti sinaArti,int stat){
		String reason = null;//���ؽ��
		String json = null;
		JSONObject jsonObject=new JSONObject();
		JSONObject result=new JSONObject();
		if(stat==1){
			reason="�ɹ��ķ���";
			JSONObject data=new JSONObject(sinaArti);
			result.append("data", data);
		}else if(stat==10001){
			reason="���������KEY";
		}else if(stat==10002){
			reason="��ǰIP���󳬹�����";
		}else if(stat==10003){
			reason="�ӿ�ά��";
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
	 * ���������json
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
