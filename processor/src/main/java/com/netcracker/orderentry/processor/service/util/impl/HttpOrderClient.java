package com.netcracker.orderentry.processor.service.util.impl;

import com.netcracker.orderentry.processor.domain.Order;
import com.netcracker.orderentry.processor.domain.OrderItem;
import com.netcracker.orderentry.processor.service.impl.exception.OrderAlreadyPaidException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderItemNotFoundException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderNotFoundException;
import com.netcracker.orderentry.processor.service.util.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by ulza1116 on 8/22/2017.
 */
@Component
public class HttpOrderClient implements OrderClient {

    private final RestTemplate restTemplate;

    @Autowired
    public HttpOrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Order> getOrders(String stringUri, Map<String, String> params){
        URI uri = UrlUtil.buildUri(stringUri, params);
        ResponseEntity<List<Order>> responseEntity = restTemplate.exchange(new RequestEntity<List<Order>>(HttpMethod.GET, uri),
                new ParameterizedTypeReference<List<Order>>() {});

        return responseEntity.getBody();
    }

    @Override
    public void payForOrder(String stringUri, int orderId) throws OrderAlreadyPaidException, OrderNotFoundException {
        URI uri = UrlUtil.buildUri(stringUri + orderId);
        try {
            restTemplate.put(uri, null);
        }
        catch (HttpClientErrorException ex){
            if(ex.getStatusCode() == HttpStatus.BAD_REQUEST){
                throw new OrderAlreadyPaidException("Order with id = " + orderId + " already paid.");
            }
            else if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new OrderNotFoundException("Order with id = " + orderId + " not found.");
            }
        }
    }

    @Override
    public int getOrderCount(String stringUri, Map<String, String> params){
        URI uri = UrlUtil.buildUri(stringUri, params);
        return restTemplate.getForObject(uri, Integer.class);
    }

    @Override
    public double getTotalPrice(String stringUri, Map<String, String> params){
        URI uri = UrlUtil.buildUri(stringUri, params);
        return restTemplate.getForObject(uri, Double.class);
    }

    @Override
    public Order getOrder(String stringUri){
        URI uri = UrlUtil.buildUri(stringUri);
        return restTemplate.getForObject(uri, Order.class);
    }

    @Override
    public Order createOrder(String stringUri, Order order){
        URI uri = UrlUtil.buildUri(stringUri);
        return restTemplate.postForEntity(uri, order, Order.class).getBody();
    }

    @Override
    public OrderItem createOrderItem(String stringUri, OrderItem orderItem){
        URI uri = UrlUtil.buildUri(stringUri);
        HttpEntity<OrderItem> orderItemHttpEntity = new HttpEntity<>(orderItem);
        return restTemplate.exchange(uri, HttpMethod.POST, orderItemHttpEntity, OrderItem.class).getBody();
    }

    @Override
    public void deleteOrderItem(String stringUri) throws OrderItemNotFoundException {
        URI uri = UrlUtil.buildUri(stringUri);
        try {
            restTemplate.delete(uri);
        }
        catch (HttpClientErrorException ex){
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new OrderItemNotFoundException("Order was not found");
            }
        }
    }

}
