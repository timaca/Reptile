package tk.timaca.Reptile.Bean;

public class User {
	private int uid;//�û�id
	private String key;//�û���Կ
	private String ip;//�û�ip
        private int times;//�û���һСʱ����Ĵ���
	
	public User(){
		
	}
	
	public User(int uid, String key,String ip,int times) {
		super();
		this.uid = uid;
		this.key = key;
		this.ip=ip;
                this.times=times;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getTimes(){
            return  times;
        }
        
        public void setTimes(int times){
            this.times=times;
        }
}
