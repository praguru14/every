package com.epps.framework.notification.mail.application.file;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.epps.framework.domain.exception.ApplicationException;
import com.epps.framework.domain.exception.ErrorCode;
import com.epps.framework.domain.exception.ResponseInfoType;

@Component("mailFileLoader")
public class MailFileLoader implements ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	/**
	 * Method will return the file contents if it exists as a byte array
	 * 
	 * @param pathUrl
	 * @return
	 * @throws ApplicationException
	 */
	public byte[] getResourceAsrray(final String pathUrl) throws ApplicationException {
		byte[] data = null;
		final Resource template = this.resourceLoader.getResource("classpath:" + pathUrl);
		if (template.exists()) {
			try {
				final InputStream inputStream = template.getInputStream();
				data = MailFileUtility.getBytes(inputStream);
			} catch (final IOException e) {
				throw new ApplicationException("File read failed for " + pathUrl, ErrorCode.UNEXPECTED_ERROR,ResponseInfoType.ERROR);
			}
		} else {
			throw new ApplicationException("File read failed for " + pathUrl, ErrorCode.UNEXPECTED_ERROR,ResponseInfoType.ERROR);
		}

		return data;
	}

	/**
	 * Method will return the file contents if it exists as an input stream
	 * 
	 * @param pathUrl
	 * @return {@link InputStream}
	 * @throws ApplicationException
	 */
	public InputStream getResourceAsStream(final String pathUrl) throws ApplicationException {
		InputStream inputStream = null;
		final Resource template = this.resourceLoader.getResource("classpath:" + pathUrl);
		if (template.exists()) {
			try {
				inputStream = template.getInputStream();
			} catch (final IOException e) {
				throw new ApplicationException("File read failed for " + pathUrl, ErrorCode.UNEXPECTED_ERROR,ResponseInfoType.ERROR);
			}
		} else {
			throw new ApplicationException("File read failed for " + pathUrl, ErrorCode.UNEXPECTED_ERROR,ResponseInfoType.ERROR);
		}

		return inputStream;
	}

	@Override
	public void setResourceLoader(final ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;

	}

}

