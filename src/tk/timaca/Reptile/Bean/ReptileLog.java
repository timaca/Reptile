package tk.timaca.Reptile.Bean;
//爬虫日志
public class ReptileLog {

	private int id;//id
	private String date;//时间
	private String result;//事件
	
	public ReptileLog(){}
	
	public ReptileLog(int id,String date, String result) {
		super();
		this.id=id;
		this.date = date;
		this.result = result;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
