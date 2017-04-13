package pl.lucasjasek.repositories;


import org.springframework.data.repository.CrudRepository;
import pl.lucasjasek.model.Seat;

import java.util.List;

public interface SeatRepository extends CrudRepository<Seat, Integer> {

    List<Seat> findSeatBySeatNumberIn(List<Integer> seatNumber);
}
