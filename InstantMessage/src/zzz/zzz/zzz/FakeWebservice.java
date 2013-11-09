package zzz.zzz.zzz;

import im.webservice.messagepackage.MessagePackage;

/**
 * @author mengchaow
 *
 */
public class FakeWebservice {
	MessagePackage mp;
	public void sendPackage(MessagePackage mp)
	{
		this.mp = mp;
	}
	public MessagePackage receivePackage()
	{
		return this.mp;
	}
}