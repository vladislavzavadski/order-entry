package com.netcracker.orderentry.processor.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by ulza1116 on 8/22/2017.
 */
public class Order {

    private int id;

    private String email;

    private double totalPrice;

    private int orderItemCount;

    private Date orderDate;

    private boolean paid;

    private List<OrderItem> orderItemList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderItemCount() {
        return orderItemCount;
    }

    public void setOrderItemCount(int orderItemCount) {
        this.orderItemCount = orderItemCount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderItemCount=" + orderItemCount +
                ", orderDate=" + orderDate +
                ", paid=" + paid +
                '}';
    }
}
