package pl.lucasjasek.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Seat {

    @Id
    private Integer id;
    private Integer seatNumber;


    public Seat() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}
