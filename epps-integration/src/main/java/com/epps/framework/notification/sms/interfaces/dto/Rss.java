package com.epps.framework.notification.sms.interfaces.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rss {
	
	Channel channel;

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	

}
