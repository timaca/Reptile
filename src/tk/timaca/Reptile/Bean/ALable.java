package tk.timaca.Reptile.Bean;
/*
 * <a></a>标签javabean类
 */
public class ALable {
	private String href;//链接
	private String Title;//标题
	
	public ALable() {
	}

	public ALable(String href, String title) {
		super();
		this.href = href;
		Title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}
}
