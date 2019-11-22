package com.awcsoftware.mybatis;

public class DbException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message = null;
	 
    public DbException() {
        super();
    }
 
    public DbException(String message) {
        super(message);
        this.message = message;
    }
 
    public DbException(Throwable cause) {
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
