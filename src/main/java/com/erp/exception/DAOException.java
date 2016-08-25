package com.erp.exception;

/**
 * ���ݿ������(DAO)�쳣
 * Created by wang_ on 2016-07-02.
 */
public class DAOException extends Exception {
    private static final long serialVersionUID = 1L;

    public DAOException(
            String message,
            Throwable cause) {
        super(message, cause);
    }

    public DAOException(String strMsg){
        super(strMsg);
    }

    public DAOException(Exception e){
        super(e);
    }
}
