package study.exception;

import java.lang.RuntimeException;


public class NotAllowedNameException extends RuntimeException {
    
    public NotAllowedNameException(String msg){
        super(msg);
    }

}
