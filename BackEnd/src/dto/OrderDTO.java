package dto;

import java.util.Date;

public class OrderDTO {
    private String orderId;
    private Date orderDate;
    private double orderCost;
    private int discount;
    private String customerId;

    public OrderDTO() {
    }

    public OrderDTO(String orderId) {
        this.orderId = orderId;
    }

    public OrderDTO(String orderId, Date orderDate, double orderCost, int discount, String customerId) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setOrderCost(orderCost);
        this.setDiscount(discount);
        this.setCustomerId(customerId);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(double orderCost) {
        this.orderCost = orderCost;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", orderCost=" + orderCost +
                ", discount=" + discount +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
