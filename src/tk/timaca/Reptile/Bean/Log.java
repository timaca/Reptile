package tk.timaca.Reptile.Bean;

//api������־��
public class Log {
	private int logid;//��־id
	private User user;//�û�
	private String date;//ʱ��
	private String type;//
	private int num;//������Ŀ
	private String result;//���
	
	public Log() {
		super();
	}
	
	public Log(int logid,User user, String date, String type, int num, String result) {
		super();
		this.logid=logid;
		this.user = user;
		this.date = date;
		this.type = type;
		this.num = num;
		this.result = result;
	}



	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public int getLogid() {
		return logid;
	}

	public void setLogid(int logid) {
		this.logid = logid;
	}
	
	
}
