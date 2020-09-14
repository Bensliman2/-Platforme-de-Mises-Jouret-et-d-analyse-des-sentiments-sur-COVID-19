package ma.fstt.model;

import java.io.Serializable;

public class ScrapRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String tag;
	
	public ScrapRequest() {}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
