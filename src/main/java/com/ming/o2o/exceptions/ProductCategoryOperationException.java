package com.ming.o2o.exceptions;

public class ProductCategoryOperationException extends RuntimeException{

    private static final long serialVersionUID = -6646356690453048672L;
    public ProductCategoryOperationException(String msg) {
        super(msg);
    }
}
