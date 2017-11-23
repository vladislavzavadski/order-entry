package com.netcracker.orderentry.processor.client.impl;

import com.netcracker.orderentry.processor.domain.to.Order;
import com.netcracker.orderentry.processor.domain.to.OrderItem;
import com.netcracker.orderentry.processor.service.impl.exception.OrderAlreadyPaidException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderItemNotFoundException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderNotFoundException;
import com.netcracker.orderentry.processor.client.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        HttpEntity<String> entity = new HttpEntity<>(getHttpHeaders());

        ResponseEntity<List<Order>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Order>>() {});

        return responseEntity.getBody();
    }

    @Override
    public void payForOrder(String stringUri, int orderId) throws OrderAlreadyPaidException, OrderNotFoundException {
        URI uri = UrlUtil.buildUri(stringUri + orderId);
        try {
            restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(getHttpHeaders()), Void.class);
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
        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(getHttpHeaders()), Integer.class).getBody();
    }

    @Override
    public double getTotalPrice(String stringUri, Map<String, String> params){
        URI uri = UrlUtil.buildUri(stringUri, params);
        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(getHttpHeaders()), Double.class).getBody();
    }

    @Override
    public Order getOrder(String stringUri, int orderId) throws OrderNotFoundException {
        URI uri = UrlUtil.buildUri(stringUri + orderId);

        RequestEntity<Order> requestEntity = new RequestEntity<Order>(getHttpHeaders(), HttpMethod.GET, uri);

        try {
            ResponseEntity<Order> responseEntity = restTemplate.exchange(requestEntity, Order.class);
            return responseEntity.getBody();
        }
        catch (HttpClientErrorException ex){
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new OrderNotFoundException("Order with id = " + orderId + " was not found");
            }
            else {
                throw ex;
            }
        }
    }

    @Override
    public Order createOrder(String stringUri, Order order){
        URI uri = UrlUtil.buildUri(stringUri);
        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(order, getHttpHeaders()), Order.class).getBody();
    }

    @Override
    public OrderItem createOrderItem(String stringUri, OrderItem orderItem){
        URI uri = UrlUtil.buildUri(stringUri);
        HttpEntity<OrderItem> orderItemHttpEntity = new HttpEntity<>(orderItem, getHttpHeaders());
        return restTemplate.exchange(uri, HttpMethod.POST, orderItemHttpEntity, OrderItem.class).getBody();
    }

    @Override
    public void deleteOrderItem(String stringUri, int orderItemId) throws OrderItemNotFoundException {
        URI uri = UrlUtil.buildUri(stringUri + orderItemId);
        try {
            restTemplate.exchange(uri, HttpMethod.DELETE, new HttpEntity<>(getHttpHeaders()), Void.class);
        }
        catch (HttpClientErrorException ex){
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new OrderItemNotFoundException("Order was not found");
            }
        }
    }

    @Override
    public List<String> authenticate(String username, String password){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:8080/login", request, Void.class);
        return responseEntity.getHeaders().get("Set-Cookie");
    }

    private HttpHeaders getHttpHeaders(){
        Object cookies = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().
                getSession(false).getAttribute("inventoryCookies");
        List<String> listCookies = (List<String>)cookies;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Cookie",listCookies.stream().collect(Collectors.joining(";")));
        return httpHeaders;
    }
}
