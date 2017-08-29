package com.netcracker.orderentry.inventory.controller;

import com.netcracker.orderentry.inventory.domain.Order;
import com.netcracker.orderentry.inventory.domain.OrderItem;
import com.netcracker.orderentry.inventory.service.OrderService;
import com.netcracker.orderentry.inventory.service.exception.OrderItemNotFoundException;
import com.netcracker.orderentry.inventory.service.exception.OrderNotFoundException;
import com.netcracker.orderentry.inventory.service.exception.OrderPaidException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "OrderControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Order createOrder(@RequestBody Order order){
        return orderService.createOrder(order);
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    @ApiOperation("Get the order with specific ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Order.class)})
    public Order getOrder(@PathVariable("orderId") int orderId) throws OrderNotFoundException {
        return orderService.getOrder(orderId);
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.DELETE)
    public void deleteOrder(@PathVariable("orderId") int orderId) throws OrderNotFoundException {
        orderService.deleteOrder(orderId);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public List<Order> getOrders(@RequestParam("limit") int limit, @RequestParam("page") int page){
        return orderService.getOrders(limit, page);
    }

    @RequestMapping(value = "/order", method = RequestMethod.PUT)
    public Order updateOrder(@RequestBody Order order, @PathVariable("orderId") int orderId) throws OrderNotFoundException {
        return orderService.updateOrder(order, orderId);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET, params = {"email", "page", "limit"})
    public List<Order> searchOrdersByEmail(@RequestParam("email") String email, @RequestParam("page") int page,
                                           @RequestParam("limit") int limit){
        return orderService.searchOrdersByEmail(email, page, limit);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET, params = {"paid", "page", "limit"})
    public List<Order> searchOrdersByStatus(@RequestParam("paid") boolean paid, @RequestParam("page") int page,
                                            @RequestParam("limit") int limit){
        return orderService.searchOrdersByStatus(paid, page, limit);
    }

    @RequestMapping(value = "/order/pay/{orderId}", method = RequestMethod.PUT)
    public Order payForOrder(@PathVariable("orderId") int orderId) throws OrderNotFoundException, OrderPaidException {
        return orderService.payForOrder(orderId);
    }

    @RequestMapping(value = "/order/count", method = RequestMethod.GET, params = {"email"})
    public int getTotalOrderCount(@RequestParam("email") String email){
        return orderService.getTotalOrderCount(email);
    }

    @RequestMapping(value = "/order/price", method = RequestMethod.GET, params = {"email"})
    public double getTotalPrice(@RequestParam("email") String email){
        return orderService.getTotalPrice(email);
    }

    @RequestMapping(value = "/order/item", method = RequestMethod.POST)
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem){
        return orderService.createOrderItem(orderItem);
    }

    @RequestMapping(value = "/order/item/{orderItemId}", method = RequestMethod.DELETE)
    public void deleteOrderItem(@PathVariable("orderItemId") int orderItemId) throws OrderItemNotFoundException, OrderPaidException {
        orderService.deleteOrderItem(orderItemId);
    }

    @ExceptionHandler(value = {OrderNotFoundException.class, OrderItemNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void orderNotFoundExceptionHandler(OrderNotFoundException ex){}

    @ExceptionHandler(OrderPaidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void orderPaidExceptionHandler(OrderPaidException ex){}
}