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

	public MessagePackage() {

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

	public void clone(MessagePackage target) {
		this.source = target.getSource();
		this.text = target.getText();
		this.target = target.getTarget();
	}
}
