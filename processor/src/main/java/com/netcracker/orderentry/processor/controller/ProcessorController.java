package com.netcracker.orderentry.processor.controller;

import com.netcracker.orderentry.processor.service.ProcessorService;
import com.netcracker.orderentry.processor.service.impl.exception.OfferNotFoundException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderAlreadyPaidException;
import com.netcracker.orderentry.processor.service.impl.exception.OrderItemNotFoundException;
import com.netcracker.orderentry.processor.domain.Order;
import com.netcracker.orderentry.processor.domain.OrderItem;
import com.netcracker.orderentry.processor.service.impl.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ulza1116 on 8/22/2017.
 */
@RestController
public class ProcessorController {

    private final ProcessorService processorService;

    @Autowired
    public ProcessorController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @RequestMapping(value = "/order/email", method = RequestMethod.GET, params = {"email", "page", "limit"})
    public List<Order> getOrdersByEmail(@RequestParam(value = "email") String email, @RequestParam("page") int page,
                                        @RequestParam("limit") int limit) throws OfferNotFoundException {
        return processorService.getOrdersByEmail(email, page, limit);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET, params = {"paid", "page", "limit"})
    public List<Order> getOrderByPaidStatus(@RequestParam("paid") boolean paid, @RequestParam("page") int page,
                                             @RequestParam("limit") int limit) throws OfferNotFoundException {
        return processorService.getOrdersByPaidStatus(paid, page, limit);
    }

    @RequestMapping(value = "/order/pay/{orderId}", method = RequestMethod.PUT)
    public void payForOrder(@PathVariable("orderId") int orderId) throws OrderNotFoundException, OrderAlreadyPaidException {
        processorService.payForOrder(orderId);
    }

    @RequestMapping(value = "/order/count", method = RequestMethod.GET, params = {"email"})
    public int getOrderCount(@RequestParam("email") String email){
        return processorService.getOrderCountByEmail(email);
    }

    @RequestMapping(value = "/order/price", method = RequestMethod.GET, params = {"email"})
    public double getTotalOrderPrice(@RequestParam("email") String email){
        return processorService.getTotalOrderPriceByEmail(email);
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public Order getOrder(@PathVariable("orderId") int orderId) throws OrderNotFoundException, OfferNotFoundException {
        return processorService.getOrder(orderId);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Order createOrder(@RequestBody Order order){
        return processorService.createOrder(order);
    }

    @RequestMapping(value = "/order/item", method = RequestMethod.POST)
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) throws OfferNotFoundException {
        return processorService.addOrderItem(orderItem);
    }

    @RequestMapping(value = "/order/item/{orderItemId}", method = RequestMethod.DELETE)
    public void deleteOrderItem(@PathVariable("orderItemId") int orderItemId) throws OrderItemNotFoundException {
        processorService.deleteOrderItem(orderItemId);
    }

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String offerNotFoundExceptionHandler(OfferNotFoundException ex){
        return "Offer not found";
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderItemNotFoundExceptionHandler(OrderItemNotFoundException ex){
        return "Order item not found";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderNotFoundExceptionHandler(OrderNotFoundException ex){
        return "Order not found";
    }

    @ExceptionHandler(OrderAlreadyPaidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String orderAlreadyPaidExceptionHandler(OrderAlreadyPaidException ex){
        return "Order already paid";
    }

}
