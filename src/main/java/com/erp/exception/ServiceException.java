package com.erp.exception;

/**
 * ҵ�������(Service)�쳣
 * Created by wang_ on 2016-07-02.
 */
public class ServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public ServiceException(
            String message,
            Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String strMsg){
        super(strMsg);
    }

    public ServiceException(Exception e){
        super(e);
    }

}
