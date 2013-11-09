package im.webservice.messagepackage;

/**
 * @author mengchaow
 * 
 */
public class MessagePackage {
	private String source;
	private String target;
	private String text;

	public MessagePackage(String source, String target, String text) {
		this.source = source;
		this.target = target;
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
