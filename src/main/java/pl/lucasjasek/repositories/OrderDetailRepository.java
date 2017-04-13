package pl.lucasjasek.repositories;


import org.springframework.data.repository.CrudRepository;
import pl.lucasjasek.model.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {

    List<OrderDetail> findOrderDetailBySessionId(Integer session);
}
