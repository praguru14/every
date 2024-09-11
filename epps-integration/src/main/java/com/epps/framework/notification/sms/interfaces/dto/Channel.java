package com.epps.framework.notification.sms.interfaces.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Channel {

	Item[] item;

	public Item[] getItem() {
		return item;
	}

	public void setItem(Item[] item) {
		this.item = item;
	}

	
}
