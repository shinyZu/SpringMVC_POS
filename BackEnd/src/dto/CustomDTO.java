package dto;

import javax.json.JsonArray;
import java.util.ArrayList;
import java.util.Date;

public class CustomDTO {
    private String orderId;
    private Date orderDate;
    private double subTotal;
    private int discount;
    private String customerId;
    private ArrayList<OrderDetailDTO> orderDetail;

    public CustomDTO() {
    }

    public CustomDTO(String orderId, Date orderDate, double subTotal, int discount, String customerId, ArrayList<OrderDetailDTO> orderDetail) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setSubTotal(subTotal);
        this.setDiscount(discount);
        this.setCustomerId(customerId);
        this.setOrderDetail(orderDetail);
    }

    public CustomDTO(String orderId, String date, double subTotal, int discount, String customerId, JsonArray orderDetail) {
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

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
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

    public ArrayList<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(ArrayList<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public String toString() {
        return "CustomDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", subTotal=" + subTotal +
                ", discount=" + discount +
                ", customerId='" + customerId + '\'' +
                ", orderDetail=" + orderDetail +
                '}';
    }
}
