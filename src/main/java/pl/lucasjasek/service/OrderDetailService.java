package pl.lucasjasek.service;


import pl.lucasjasek.dto.OrderDTO;
import pl.lucasjasek.model.OrderDetail;

import java.security.Principal;
import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> findOrderDetailList(String username);

    List<String> getSchedule(String filmName, String date);

    List<Integer> getBookedPlaces(String hall, String date, String time);

    String setHallName(String time, String date);

    void createOrder(OrderDTO orderDTO, Principal principal);
}
