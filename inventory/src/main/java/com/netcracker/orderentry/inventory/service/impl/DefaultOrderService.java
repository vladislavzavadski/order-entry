package com.netcracker.orderentry.inventory.service.impl;

import com.netcracker.orderentry.inventory.domain.Order;
import com.netcracker.orderentry.inventory.domain.OrderItem;
import com.netcracker.orderentry.inventory.repository.OrderItemRepository;
import com.netcracker.orderentry.inventory.repository.OrderRepository;
import com.netcracker.orderentry.inventory.service.OrderService;
import com.netcracker.orderentry.inventory.service.exception.OrderItemNotFoundException;
import com.netcracker.orderentry.inventory.service.exception.OrderNotFoundException;
import com.netcracker.orderentry.inventory.service.exception.OrderPaidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public DefaultOrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem){
        Order order = orderRepository.findOne(orderItem.getOrder().getId());
        order.setOrderItemCount(order.getOrderItemCount() + 1);
        order.setTotalPrice(order.getTotalPrice() + orderItem.getPrice());
        orderRepository.save(order);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public Order getOrder(int orderId) throws OrderNotFoundException {

        Order order = orderRepository.findOne(orderId);

        if(order == null){
            throw new OrderNotFoundException("Order with id = "+orderId+ " was not found.");
        }

        return order;
    }

    @Override
    public void deleteOrder(int orderId) throws OrderNotFoundException {

        if(!orderRepository.exists(orderId)){
            throw new OrderNotFoundException("Order with id = "+orderId+ " was not found.");
        }

        orderRepository.delete(orderId);
    }

    @Override
    public List<Order> getOrders(int limit, int page){
        PageRequest pageRequest = new PageRequest(page, limit);
        return orderRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Order updateOrder(Order order, int orderId) throws OrderNotFoundException {
        Order storedOrder = orderRepository.findOne(orderId);

        if(storedOrder == null){
            throw new OrderNotFoundException("Order with id = " + orderId + " was not found");
        }

        storedOrder.setEmail(order.getEmail());
        storedOrder.setOrderDate(order.getOrderDate());
        storedOrder.setOrderItemCount(order.getOrderItemCount());
        storedOrder.setOrderItemList(order.getOrderItemList());
        storedOrder.setPaid(order.isPaid());
        storedOrder.setTotalPrice(order.getTotalPrice());

        return orderRepository.save(storedOrder);
    }

    @Override
    public List<Order> searchOrdersByEmail(String email, int page, int limit){
        PageRequest pageRequest = new PageRequest(page, limit);
        return orderRepository.findOrdersByEmail(email, pageRequest);
    }

    @Override
    public List<Order> searchOrdersByStatus(boolean paid, int page, int limit){
        PageRequest pageRequest = new PageRequest(page, limit);
        return orderRepository.findOrderByPaidEquals(paid, pageRequest);
    }

    @Override
    public void deleteOrderItem(int orderItemId) throws OrderItemNotFoundException, OrderPaidException {

        if(!orderItemRepository.exists(orderItemId)){
            throw new OrderItemNotFoundException("Order item with id=" + orderItemId + " was not found");
        }
        if(orderItemRepository.findOne(orderItemId).getOrder().isPaid()){
            throw new OrderPaidException("Order already paid");
        }

        orderItemRepository.delete(orderItemId);
    }

    @Override
    public int getTotalOrderCount(String email){
        return orderRepository.selectOrdersCountByEmail(email);
    }

    @Override
    public double getTotalPrice(String email){
        return orderRepository.getTotalPriceByEmail(email);
    }

    @Override
    public Order payForOrder(int orderId) throws OrderNotFoundException, OrderPaidException {

        if(!orderRepository.exists(orderId)){
            throw new OrderNotFoundException("Order with id = " + orderId + " was not found");
        }

        Order order = orderRepository.getOne(orderId);

        if(order.isPaid()){
            throw new OrderPaidException("Order with id = " + orderId + " already paid");
        }

        order.setPaid(true);
        return orderRepository.save(order);

    }
}