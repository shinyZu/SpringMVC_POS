package lk.ijse.spring.repo;

import lk.ijse.spring.entity.OrderDetail;
import lk.ijse.spring.entity.OrderItem_PK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, OrderItem_PK> {
}
