package com.ming.o2o.exceptions;

import com.ming.o2o.entity.LocalAuth;

public class LocalAuthOperationException extends RuntimeException {
    private static final long serialVersionUID = 2908328763918902640L;
    public LocalAuthOperationException(String msg) {
        super(msg);
    }
}
