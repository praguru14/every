package com.epps.framework.notification.mail.application.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.FileTypeMap;

public class MailFileUtility {

	private static final int DEFAULT_BUFFER_SIZE = 512;

	public static int copy(final InputStream input, final OutputStream output) throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		buffer = null;
		return count;
	}

	public static byte[] getBytes(final InputStream input) throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			outputStream.write(buffer, 0, n);
		}
		buffer = null;
		return outputStream.toByteArray();
	}

	public static ByteArrayOutputStream getByteStream(final InputStream input) throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			outputStream.write(buffer, 0, n);
		}
		buffer = null;
		return outputStream;
	}

	/**
	 * Method attempts to identify the content type of the file based on the
	 * fileName provided If no match found then it returns
	 * "application/octet-stream"
	 * 
	 * @param fileName
	 * @return {@link String}
	 */
	public static String getContentTypeFromFileName(final String fileName) {

		String contentType = FileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
		if ((null == contentType) || "".equals(contentType)) {
			contentType = "application/octet-stream";
		}
		return contentType;
	}
}

