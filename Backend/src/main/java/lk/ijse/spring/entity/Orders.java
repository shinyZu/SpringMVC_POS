package lk.ijse.spring.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {
    @Id
    private String orderId;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    private double orderCost;
    private int discount;

    @ManyToOne
    @JoinColumn(name = "customerId"/*, referencedColumnName = "customerId"*/)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
