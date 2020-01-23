package com.awcsoftware.app;

import java.io.IOException;
import java.text.ParseException;

import javax.mail.MessagingException;

public class AppException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message = null;
	
	public AppException() {
		super();
	}
	
	public AppException(String message) {
		super(message);
		this.message = message;
	}	
	public AppException(Throwable cause) {
        super(cause);
    }
	
	public AppException(ParseException cause) {
        super(cause);
    }

	public AppException(IOException cause) {
        super(cause);
    }

	public AppException(MessagingException cause) {
        super(cause);
    }

	@Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
