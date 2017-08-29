package com.netcracker.orderentry.processor.service.util;

import com.netcracker.orderentry.processor.domain.Order;
import com.netcracker.orderentry.processor.domain.OrderItem;
import com.netcracker.orderentry.processor.service.impl.exception.OrderAlreadyPaidException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderItemNotFoundException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * Created by ulza1116 on 8/22/2017.
 */
public interface OrderClient {
    List<Order> getOrders(String stringUri, Map<String, String> params);

    void payForOrder(String stringUri, int orderId) throws OrderAlreadyPaidException, OrderNotFoundException;

    int getOrderCount(String stringUri, Map<String, String> params);

    double getTotalPrice(String stringUri, Map<String, String> params);

    Order getOrder(String stringUri, int orderId) throws OrderNotFoundException;

    Order createOrder(String stringUri, Order order);

    OrderItem createOrderItem(String stringUri, OrderItem orderItem);

    void deleteOrderItem(String stringUri, int orderItemId) throws OrderItemNotFoundException;
}
