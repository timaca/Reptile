package tk.timaca.Reptile.Bean;
//��������
public class SinaArti {
	//����΢�Ŷ��ĺŵ�������Ϣ������SinaArti�������Ĳ���
	private String title;//����
	private String date;//ʱ��
	private String author_name;//����
	private String thumbnail_pic_s;//ͼƬ1
	private String thumbnail_pic_s02;//ͼƬ2
	private String thumbnail_pic_s03;//ͼƬ3
	private String url;//��������
	private String uniquekey;//Ψһ��ʶ
	private String type;//����һ
	private String realtype;//���Ͷ�
        private String content;//��������
        private String tag;//���±�ǩ
	
	public SinaArti(){}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public String getThumbnail_pic_s() {
		return thumbnail_pic_s;
	}
	public void setThumbnail_pic_s(String thumbnail_pic_s) {
		this.thumbnail_pic_s = thumbnail_pic_s;
	}
	public String getThumbnail_pic_s02() {
		return thumbnail_pic_s02;
	}
	public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
		this.thumbnail_pic_s02 = thumbnail_pic_s02;
	}
	public String getThumbnail_pic_s03() {
		return thumbnail_pic_s03;
	}
	public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
		this.thumbnail_pic_s03 = thumbnail_pic_s03;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUniquekey() {
		return uniquekey;
	}
	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRealtype() {
		return realtype;
	}
	public void setRealtype(String realtype) {
		this.realtype = realtype;
	}
        public String getContent(){
                return content;
        }
        public void setContent(String content){
                this.content=content;
        }
        public String getTag(){
            return tag;
        }
        public void setTag(String tag){
            this.tag=tag;
        }
    
        

	@Override
	public String toString() {
		return "SinaArti [title=" + title + ", date=" + date + ", author_name=" + author_name + ", thumbnail_pic_s="
				+ thumbnail_pic_s + ", thumbnail_pic_s02=" + thumbnail_pic_s02 + ", thumbnail_pic_s03="
				+ thumbnail_pic_s03 + ", url=" + url + ", uniquekey=" + uniquekey + ", type=" + type + ", realtype="
				+ realtype +", content=" + content+ ", tag=" + tag+ "]";
	}
	
	
}
