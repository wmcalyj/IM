package im.webservice.messagepackage;

import java.io.Serializable;

/**
 * @author mengchaow
 * 
 */
public class MessagePackage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	@Override
	public String toString() {
		return "MessagePackage [Source: " + this.source + ", Target: "
				+ this.target + ", Text: " + this.text + ".]";
	}
}
