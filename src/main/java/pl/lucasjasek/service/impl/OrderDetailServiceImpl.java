package pl.lucasjasek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lucasjasek.dto.OrderDTO;
import pl.lucasjasek.model.OrderDetail;
import pl.lucasjasek.model.Seat;
import pl.lucasjasek.model.User;
import pl.lucasjasek.repositories.OrderDetailRepository;
import pl.lucasjasek.repositories.SeatRepository;
import pl.lucasjasek.repositories.SessionRepository;
import pl.lucasjasek.service.OrderDetailService;
import pl.lucasjasek.service.UserService;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    private final UserService userService;

    private final SessionRepository sessionRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final SeatRepository seatRepository;

    @Autowired
    public OrderDetailServiceImpl(UserService userService, SessionRepository sessionRepository, OrderDetailRepository orderDetailRepository,
                                  SeatRepository seatRepository) {
        this.userService = userService;
        this.sessionRepository = sessionRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.seatRepository = seatRepository;
    }


    @Override
    public List<OrderDetail> findOrderDetailList(String username){
        User user = userService.findByUsername(username);
        List<OrderDetail> orderDetailList = user.getOrderDetailList();

        return orderDetailList;
    }

    @Override
    public String setHallName(String time, String date) {

        return sessionRepository.findHallByTimeAndDate(time, date);
    }

    @Override
    public List<String> getSchedule(String filmName, String date) {

        return sessionRepository.findFilmScheduleTimeByCriteriaList(filmName, date);
    }

    @Override
    public List<Integer> getBookedPlaces(String hall, String date, String time) {

        Integer sessionId = getSessionId(date, time);
        List<OrderDetail> orderDetailList = orderDetailRepository.findOrderDetailBySessionId(sessionId);

        List<Integer> bookedPlaces = orderDetailList.stream()
                .map(OrderDetail::getSeatList)
                .flatMap(List::stream)
                .map(Seat::getId)
                .collect(Collectors.toList());

        return bookedPlaces;
    }

    @Override
    public void createOrder(OrderDTO orderDTO, Principal principal) {

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDate(new Date());
        orderDetail.setPaid(orderDTO.isPaid());
        orderDetail.setSeatList(getSeatById(orderDTO));
        orderDetail.setSession(sessionRepository.findSessionById(getSessionId(orderDTO.getDate(), orderDTO.getTime())));
        orderDTO.setUuid(orderDetail.getUuid());

        if(null != principal){
            orderDetail.setUser(userService.findByUsername(principal.getName()));
        }

        orderDetailRepository.save(orderDetail);
    }

    private Integer getSessionId(String date, String time) {

        Integer sessionId = sessionRepository.findSessionIdByCriteriaList(date, time);

        return sessionId;
    }

    private List<Seat> getSeatById(OrderDTO orderDTO) {

        List<Seat> seatList = seatRepository.findSeatBySeatNumberIn(orderDTO.getIds());

        return seatList;
    }
}
