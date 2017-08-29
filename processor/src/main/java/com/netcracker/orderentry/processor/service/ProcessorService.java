package com.netcracker.orderentry.processor.service;

import com.netcracker.orderentry.processor.domain.Order;
import com.netcracker.orderentry.processor.domain.OrderItem;
import com.netcracker.orderentry.processor.service.impl.exception.OrderAlreadyPaidException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderItemNotFoundException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderNotFoundException;

import java.util.List;

/**
 * Created by ulza1116 on 8/22/2017.
 */
public interface ProcessorService {
    List<Order> getOrdersByEmail(String email, int page, int limit);

    List<Order> getOrdersByPaidStatus(boolean paid, int page, int limit);

    void payForOrder(int orderId) throws OrderAlreadyPaidException, OrderNotFoundException;

    int getOrderCountByEmail(String email);

    double getTotalOrderPriceByEmail(String email);

    Order getOrder(int orderId);

    Order createOrder(Order order);

    OrderItem addOrderItem(OrderItem orderItem);

    void deleteOrderItem(int orderItemId) throws OrderItemNotFoundException;
}
