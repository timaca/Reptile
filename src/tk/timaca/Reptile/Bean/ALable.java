package tk.timaca.Reptile.Bean;
/*
 * <a></a>��ǩjavabean��
 */
public class ALable {
	private String href;//����
	private String Title;//����
	
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
