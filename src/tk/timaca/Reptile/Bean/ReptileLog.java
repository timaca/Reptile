package tk.timaca.Reptile.Bean;
//������־
public class ReptileLog {

	private int id;//id
	private String date;//ʱ��
	private String result;//�¼�
	
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
