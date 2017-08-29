package com.netcracker.orderentry.processor.service.impl;

import com.netcracker.orderentry.processor.domain.OrderItem;
import com.netcracker.orderentry.processor.service.ProcessorService;
import com.netcracker.orderentry.processor.service.impl.exception.OfferNotFoundException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderAlreadyPaidException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderItemNotFoundException;
import com.netcracker.orderentry.processor.service.util.OfferClient;
import com.netcracker.orderentry.processor.service.util.OrderClient;
import com.netcracker.orderentry.processor.domain.Offer;
import com.netcracker.orderentry.processor.domain.Order;
import com.netcracker.orderentry.processor.service.ParamName;
import com.netcracker.orderentry.processor.service.impl.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by ulza1116 on 8/22/2017.
 */

@Service
public class DefaultProcessorService implements ProcessorService {

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Value("${offer.service.url}")
    private String offerServiceUrl;

    @Value("${order.service.url.pay}")
    private String payForOrderUrl;

    @Value("${order.service.url.count}")
    private String getOrderCountUrl;

    @Value("${order.service.url.price}")
    private String getOrderPriceUrl;

    @Value("${order.service.url.item}")
    private String orderItemUrl;

    private static final Logger LOGGER = Logger.getLogger("logger");

    private final OrderClient orderClient;
    private final OfferClient offerClient;

    @Autowired
    public DefaultProcessorService(OrderClient orderClient, OfferClient offerClient) {
        this.orderClient = orderClient;
        this.offerClient = offerClient;

    }

    @Override
    public List<Order> getOrdersByEmail(String email, int page, int limit) throws OfferNotFoundException {

        LOGGER.info("\nMethod: DefaultProcessorService.getOrdersByEmail \n email: "+email+"\n page: "+page+" \nlimit: "+limit);

        Map<String, String> params = new HashMap<>();
        params.put(ParamName.EMAIL, email);
        params.put(ParamName.PAGE, Integer.toString(page));
        params.put(ParamName.LIMIT, Integer.toString(limit));


        return searchOrders(params);
    }

    @Override
    public List<Order> getOrdersByPaidStatus(boolean paid, int page, int limit) throws OfferNotFoundException {

        LOGGER.info("\nMethod: DefaultProcessorService.getOrdersByPaidStatus \n isPaid: "+paid+"\n page: "+page+" \nlimit: "+limit);

        Map<String, String> params = new HashMap<>();
        params.put(ParamName.PAID, Boolean.toString(paid));
        params.put(ParamName.PAGE, Integer.toString(page));
        params.put(ParamName.LIMIT, Integer.toString(limit));

        return searchOrders(params);

    }

    @Override
    public void payForOrder(int orderId) throws OrderAlreadyPaidException, OrderNotFoundException {
        LOGGER.info("\nMethod: DefaultProcessorService.payForOrder \n orderId: "+orderId);

        orderClient.payForOrder(payForOrderUrl, orderId);
    }

    @Override
    public int getOrderCountByEmail(String email){

        LOGGER.info("\nMethod: DefaultProcessorService.getOrderCountByEmail \n email: "+email);

        Map<String, String> params = new HashMap<>();
        params.put(ParamName.EMAIL, email);
        return orderClient.getOrderCount(getOrderCountUrl, params);
    }

    @Override
    public double getTotalOrderPriceByEmail(String email){

        LOGGER.info("\nMethod: DefaultProcessorService.getTotalOrderPriceByEmail \n email: "+email);

        Map<String, String> params = new HashMap<>();
        params.put(ParamName.EMAIL, email);
        return orderClient.getTotalPrice(getOrderPriceUrl, params);
    }

    @Override
    public Order getOrder(int orderId) throws OrderNotFoundException, OfferNotFoundException {

        LOGGER.info("\nMethod: DefaultProcessorService.getOrder \n orderId: "+orderId);
        Order order = orderClient.getOrder(orderServiceUrl, orderId);

        fillOrderItems(order.getOrderItemList());

        return order;
    }

    @Override
    public Order createOrder(Order order){

        LOGGER.info("\nMethod: DefaultProcessorService.createOrder \n order: "+order);
        order.setPaid(false);
        order.setTotalPrice(0.0);
        order.setOrderItemCount(0);

        return orderClient.createOrder(offerServiceUrl, order);
    }

    @Override
    public OrderItem addOrderItem(OrderItem orderItem) throws OfferNotFoundException {

        LOGGER.info("\nMethod: DefaultProcessorService.addOrderItem \n orderItem: "+orderItem);
        Offer offer = offerClient.getSingleOffer(offerServiceUrl, orderItem.getOfferId());
        orderItem.setPrice(offer.getPrice());

        return orderClient.createOrderItem(orderItemUrl, orderItem);
    }

    @Override
    public void deleteOrderItem(int orderItemId) throws OrderItemNotFoundException {

        LOGGER.info("\nMethod: DefaultProcessorService.deleteOrderItem \n orderItemId: "+orderItemId);
        orderClient.deleteOrderItem(orderItemUrl, orderItemId);
    }

    private List<Order> searchOrders(Map<String, String> params) throws OfferNotFoundException {
        List<Order> orders = orderClient.getOrders(orderServiceUrl, params);

        for (Order order : orders){
            fillOrderItems(order.getOrderItemList());
        }

        return orders;
    }

    private void fillOrderItems(List<OrderItem> orderItems) throws OfferNotFoundException {
        for (OrderItem orderItem : orderItems){
            orderItem.setOffer(offerClient.getSingleOffer(offerServiceUrl, orderItem.getOfferId()));
        }
    }

}
