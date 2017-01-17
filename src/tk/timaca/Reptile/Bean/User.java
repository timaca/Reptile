package tk.timaca.Reptile.Bean;

public class User {
	private int uid;//用户id
	private String key;//用户密钥
	private String ip;//用户ip
        private int times;//用户上一小时请求的次数
	
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
