package com.epps.framework.notification.mail.interfaces.dto.mail;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MailAttachments {

	/**
	 * Class used to manage the various attachment attributes
	 * 
	 * @author 
	 * 
	 */
	protected static class Attachment {
		private final byte[] content;
		private final String fileName;
		private final String contentType;// optional

		Attachment(final byte[] content, final String fileName, final String contentType) {
			this.content = content;
			this.fileName = fileName;
			this.contentType = contentType;
		}

		Attachment(final byte[] content, final String fileName) {
			this(content, fileName, null);
		}

		public byte[] getContent() {
			return this.content;
		}

		public String getFileName() {
			return this.fileName;
		}

		public String getContentType() {
			return this.contentType;
		}

	}

	private final Map<String, Attachment> attachments = new LinkedHashMap<String, Attachment>();

	public byte[] getAttachment(final String fileName) {
		return this.attachments.get(fileName).getContent();
	}

	public String getContentType(final String fileName) {
		return this.attachments.get(fileName).getContentType();
	}

	public void addAttachment(final String name, final byte[] contents) {
		this.attachments.put(name, new Attachment(contents, name));
	}

	public void addAttachment(final String name, final byte[] contents, final String contentType) {
		this.attachments.put(name, new Attachment(contents, name, contentType));
	}

	public void clearAttachments() {
		this.attachments.clear();
	}

	public boolean isEmpty() {
		return this.attachments.isEmpty();
	}

	public Set<String> getAttachmentsNames() {
		return new HashSet<String>(this.attachments.keySet());
	}

}

