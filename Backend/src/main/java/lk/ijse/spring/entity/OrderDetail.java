package lk.ijse.spring.entity;

import javax.persistence.*;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderDetailId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "itemCode", referencedColumnName = "itemCode")
    private Item item;

    private int orderQty;

}
