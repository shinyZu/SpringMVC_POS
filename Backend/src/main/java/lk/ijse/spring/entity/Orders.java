package lk.ijse.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class Orders {
    @Id
    private String orderId;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    private double orderCost;
    private int discount;

    @ManyToOne/*(cascade = {CascadeType.REFRESH,CascadeType.DETACH})*/
    @JoinColumn(name = "customerID", referencedColumnName = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
