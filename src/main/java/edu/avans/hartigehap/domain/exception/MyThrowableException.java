package edu.avans.hartigehap.domain.exception;

public class MyThrowableException extends Throwable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public MyThrowableException() {
        super();
    }
    
    public MyThrowableException(String s) {
        super(s);
    }
    
    public MyThrowableException(String s, Throwable throwable) {
        super(s, throwable);
    }
    
    public MyThrowableException(Throwable throwable) {
        super(throwable);
    }
}
