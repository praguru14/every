package com.epps.framework.application.util.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import com.epps.framework.domain.exception.ApplicationException;

public class ApplicationLogger implements Logger  {
	
private Logger logger;
	
	public ApplicationLogger(@SuppressWarnings("rawtypes") final Class clazz) {
		this.logger = LoggerFactory.getLogger(clazz.getName());
	}
	
	public String getName() {
		return logger.getName(); 
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public void trace(String msg) {
		logger.trace(msg);
	}

	public void trace(String format, Object arg) {
		logger.trace(format, arg);
	}

	public void trace(String format, Object arg1, Object arg2) {
		logger.trace(format, arg1, arg2);
	}

	public void trace(String format, Object[] argArray) {
		logger.trace(format, argArray);
	}

	public void trace(String msg, Throwable t) {
		if (t instanceof ApplicationException) {
			final ApplicationException applicationException = (ApplicationException) t;
			if (applicationException.isLogged()) {
				this.logger.trace(msg + " - Exception logged earlier - " + applicationException.getMessage());
			} else {
				this.logger.trace(msg, applicationException);
				applicationException.setLogged(true);
			}
		} else {
			this.logger.trace(msg, t);
		}


	}

	public boolean isTraceEnabled(Marker marker) {
		return this.logger.isTraceEnabled(marker);
	}

	public void trace(Marker marker, String msg) {
		this.logger.trace(marker, msg);
	}

	public void trace(Marker marker, String format, Object arg) {
		this.logger.trace(marker, format, arg);
	}

	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		this.logger.trace(marker, format, arg1, arg2);
	}

	public void trace(Marker marker, String format, Object[] argArray) {
		this.logger.trace(marker, format, argArray);

	}

	public void trace(Marker marker, String msg, Throwable t) {
		if (t instanceof ApplicationException) {
			final ApplicationException applicationException = (ApplicationException) t;
			if (applicationException.isLogged()) {
				this.logger.trace(marker, msg + " - Exception logged earlier - " + applicationException.getMessage());
			} else {
				this.logger.trace(marker, msg, applicationException);
				applicationException.setLogged(true);
			}
		} else {
			this.logger.trace(marker, msg, t);
		}
	}

	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}

	public void debug(String msg) {
		this.logger.debug(msg);
	}

	public void debug(String format, Object arg) {
		this.logger.debug(format, arg);
	}

	public void debug(String format, Object arg1, Object arg2) {
		this.logger.debug(format, arg1, arg2);
	}

	public void debug(String format, Object[] argArray) {
		this.logger.debug(format, argArray);
	}

	public void debug(String msg, Throwable t) {
		if (t instanceof ApplicationException) {
			final ApplicationException applicationException = (ApplicationException) t;
			if (applicationException.isLogged()) {
				this.logger.debug(msg + " - Exception logged earlier - " + applicationException.getMessage());
			} else {
				this.logger.debug(msg, applicationException);
				applicationException.setLogged(true);
			}
		} else {
			this.logger.debug(msg, t);
		}
	}

	public boolean isDebugEnabled(Marker marker) {
		return this.logger.isDebugEnabled(marker);
	}

	public void debug(Marker marker, String msg) {
		this.logger.debug(marker, msg);
	}

	public void debug(Marker marker, String format, Object arg) {
		this.logger.debug(marker, format, arg);
	}

	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		this.logger.debug(marker, format, arg1, arg2);
	}

	public void debug(Marker marker, String format, Object[] argArray) {
		this.logger.debug(marker, format, argArray);
	}

	public void debug(Marker marker, String msg, Throwable t) {
		if (t instanceof ApplicationException) {
			final ApplicationException applicationException = (ApplicationException) t;
			if (applicationException.isLogged()) {
				this.logger.debug(marker, msg + " - Exception logged earlier - " + applicationException.getMessage());
			} else {
				this.logger.debug(marker, msg, applicationException);
				applicationException.setLogged(true);
			}
		} else {
			this.logger.debug(marker, msg, t);
		}
	}

	public boolean isInfoEnabled() {
		return this.logger.isInfoEnabled();
	}

	public void info(String msg) {
		this.logger.info(msg);
	}

	public void info(String format, Object arg) {
		this.logger.info(format, arg);
	}

	public void info(String format, Object arg1, Object arg2) {
		this.logger.info(format, arg1, arg2);
	}

	public void info(String format, Object[] argArray) {
		this.logger.info(format, argArray);
	}

	public void info(String msg, Throwable t) {
		if (t instanceof ApplicationException) {
			final ApplicationException applicationException = (ApplicationException) t;
			if (applicationException.isLogged()) {
				this.logger.info(msg + " - Exception logged earlier - " + applicationException.getMessage());
			} else {
				this.logger.info(msg, applicationException);
				applicationException.setLogged(true);
			}
		} else {
			this.logger.info(msg, t);
		}
	}

	public boolean isInfoEnabled(Marker marker) {
		return this.logger.isInfoEnabled();
	}

	public void info(Marker marker, String msg) {
		this.logger.info(marker, msg);
	}

	public void info(Marker marker, String format, Object arg) {
		this.logger.info(marker, format, arg);
	}

	public void info(Marker marker, String format, Object arg1, Object arg2) {
		this.logger.info(marker, format, arg1, arg2);
	}

	public void info(Marker marker, String format, Object[] argArray) {
		this.logger.info(marker, format, argArray);
	}

	public void info(Marker marker, String msg, Throwable t) {
		if (t instanceof ApplicationException) {
			final ApplicationException applicationException = (ApplicationException) t;
			if (applicationException.isLogged()) {
				this.logger.info(marker, msg + " - Exception logged earlier - " + applicationException.getMessage());
			} else {
				this.logger.info(marker, msg, applicationException);
				applicationException.setLogged(true);
			}
		} else {
			this.logger.info(marker, msg, t);
		}
	}

	public boolean isWarnEnabled() {
		return this.logger.isWarnEnabled();
	}

	public void warn(String msg) {
		this.logger.warn(msg);
	}

	public void warn(String format, Object arg) {
		this.logger.warn(format, arg);
	}

	public void warn(String format, Object[] argArray) {
		this.logger.warn(format, argArray);
	}

	public void warn(String format, Object arg1, Object arg2) {
		this.logger.warn(format, arg1, arg2);
	}

	public void warn(String msg, Throwable t) {
		if (t instanceof ApplicationException) {
			final ApplicationException applicationException = (ApplicationException) t;
			if (applicationException.isLogged()) {
				this.logger.warn(msg + " - Exception logged earlier - " + applicationException.getMessage());
			} else {
				this.logger.warn(msg, applicationException);
				applicationException.setLogged(true);
			}
		} else {
			this.logger.warn(msg, t);
		}
	}

	public boolean isWarnEnabled(Marker marker) {
		return this.logger.isWarnEnabled();
	}

	public void warn(Marker marker, String msg) {
		this.logger.warn(marker, msg);
	}

	public void warn(Marker marker, String format, Object arg) {
		this.logger.warn(marker, format, arg);
	}

	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		this.logger.warn(marker, format, arg1, arg2);
	}

	public void warn(Marker marker, String format, Object[] argArray) {
		this.logger.warn(marker, format, argArray);
	}

	public void warn(Marker marker, String msg, Throwable t) {
		if (t instanceof ApplicationException) {
			final ApplicationException applicationException = (ApplicationException) t;
			if (applicationException.isLogged()) {
				this.logger.warn(marker, msg + " - Exception logged earlier - " + applicationException.getMessage());
			} else {
				this.logger.warn(marker, msg, applicationException);
				applicationException.setLogged(true);
			}
		} else {
			this.logger.warn(marker, msg, t);
		}

	}

	public boolean isErrorEnabled() {
		return this.logger.isErrorEnabled();
	}

	public void error(String msg) {
		this.logger.error(msg);
	}

	public void error(String format, Object arg) {
		this.logger.error(format, arg);
	}

	public void error(String format, Object arg1, Object arg2) {
		this.logger.error(format, arg1, arg2);
	}

	public void error(String format, Object[] argArray) {
		this.logger.error(format, argArray);
	}

	public void error(String msg, Throwable t) {
		if (t instanceof ApplicationException) {
			final ApplicationException applicationException = (ApplicationException) t;
			if (applicationException.isLogged()) {
				this.logger.error(msg + " - Exception logged earlier - " + applicationException.getMessage());
			} else {
				this.logger.error(msg, applicationException);
				applicationException.setLogged(true);
			}
		} else {
			this.logger.error(msg, t);
		}
	}

	public boolean isErrorEnabled(Marker marker) {
		return this.logger.isErrorEnabled(marker);
	}

	public void error(Marker marker, String msg) {
		this.logger.error(marker, msg);
	}

	public void error(Marker marker, String format, Object arg) {
		this.logger.error(marker, format, arg);
	}

	public void error(Marker marker, String format, Object arg1, Object arg2) {
		this.logger.error(marker, format, arg1, arg2);
	}

	public void error(Marker marker, String format, Object[] argArray) {
		this.logger.error(marker, format, argArray);
	}

	public void error(Marker marker, String msg, Throwable t) {
		if (t instanceof ApplicationException) {
			final ApplicationException applicationException = (ApplicationException) t;
			if (applicationException.isLogged()) {
				this.logger.error(marker, msg + " - Exception logged earlier - " + applicationException.getMessage());
			} else {
				this.logger.error(marker, msg, applicationException);
				applicationException.setLogged(true);
			}
		} else {
			this.logger.error(marker, msg, t);
		}
	}

}
