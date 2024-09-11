package com.epps.framework.notification.mail.application.processor;


import org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository;

import com.epps.framework.notification.sms.interfaces.dto.Item;
import com.epps.framework.notification.sms.interfaces.dto.Rss;

public class RssProcessor{
	
	MemoryIdempotentRepository dedupQueue;
	


	public void process(final Rss rss) {
		if(rss != null && rss.getChannel() != null ) {
			for(Item item : rss.getChannel().getItem()) {
				if(!dedupQueue.contains(item.toString())) {
					dedupQueue.add(item.toString());
					consumeItem(item);
				}
			}
		}
	}
	
	private void consumeItem(final Item item) {
		
	}

	
	
	public MemoryIdempotentRepository getDedupQueue() {
		return dedupQueue;
	}
	
	public void setDedupQueue(MemoryIdempotentRepository dedupQueue) {
		this.dedupQueue = dedupQueue;
	}

}
