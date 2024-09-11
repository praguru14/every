package com.epps.framework.notification.mail.interfaces.dto.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InlineBinaryContent {

	private boolean embeddedContentPresent = false;

	/**
	 * Map holds all image paths that we load from the classpath
	 */
	private final Map<String, String> embeddedFiles = new HashMap<String, String>();

	/**
	 * if there is a file that is dynamic just pass the contents as byte array
	 */
	private final Map<String, byte[]> embeddedContent = new HashMap<String, byte[]>();

	/**
	 * Will add the relativePath to the list of inline contents
	 * 
	 * @param relativePath
	 */
	public void addContent(final String imageName, final String relativePath) {
		this.embeddedContentPresent = true;
		this.embeddedFiles.put(imageName, relativePath);
	}

	/**
	 * Will add the byte[] to the list of inline contents
	 * 
	 * @param relativePath
	 */
	public void addContent(final String imageName, final byte[] content) {
		this.embeddedContentPresent = true;
		this.embeddedContent.put(imageName, content);
	}

	/**
	 * Will return a set of all files to be added from classpath
	 * 
	 * @return {@link Set} of {@link String}
	 */
	public Set<String> getBinaryContentKeys() {
		return new HashSet<String>(this.embeddedContent.keySet());
	}

	public String getAttachmentPath(final String fileName) {
		return this.embeddedFiles.get(fileName);
	}

	/**
	 * Will return a set of all files to be added as byte arrays
	 * 
	 * @return {@link Set} of {@link String}
	 */
	public Set<String> getAttachmentsFileNames() {
		return new HashSet<String>(this.embeddedFiles.keySet());
	}

	public byte[] getbinaryContent(final String fileName) {
		return this.embeddedContent.get(fileName);
	}

	public boolean isEmbeddedContentPresent() {
		return this.embeddedContentPresent;
	}

}
