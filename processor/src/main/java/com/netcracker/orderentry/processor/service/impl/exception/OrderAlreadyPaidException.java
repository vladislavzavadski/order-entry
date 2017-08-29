package com.netcracker.orderentry.processor.service.impl.exception;

/**
 * Created by ulza1116 on 8/24/2017.
 */
public class OrderAlreadyPaidException extends Throwable {
    public OrderAlreadyPaidException(String s) {
        super(s);
    }
}
