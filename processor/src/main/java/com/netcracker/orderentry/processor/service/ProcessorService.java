package com.netcracker.orderentry.processor.service;

import com.netcracker.orderentry.processor.domain.to.Order;
import com.netcracker.orderentry.processor.domain.to.OrderItem;
import com.netcracker.orderentry.processor.service.impl.exception.OfferNotFoundException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderAlreadyPaidException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderItemNotFoundException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderNotFoundException;

import java.util.List;

/**
 * Created by ulza1116 on 8/22/2017.
 */
public interface ProcessorService {
    List<Order> getOrdersByEmail(String email, int page, int limit) throws OfferNotFoundException;

    List<Order> getOrdersByPaidStatus(boolean paid, int page, int limit) throws OfferNotFoundException;

    void payForOrder(int orderId) throws OrderAlreadyPaidException, OrderNotFoundException;

    int getOrderCountByEmail(String email);

    double getTotalOrderPriceByEmail(String email);

    Order getOrder(int orderId) throws OrderNotFoundException, OfferNotFoundException;

    Order createOrder(Order order);

    OrderItem addOrderItem(OrderItem orderItem) throws OfferNotFoundException;

    void deleteOrderItem(int orderItemId) throws OrderItemNotFoundException;
}
