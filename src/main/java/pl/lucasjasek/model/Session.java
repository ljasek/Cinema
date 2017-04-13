package pl.lucasjasek.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Session {

    @Id
    private Integer id;

    private String date;

    private String time;

    private String filmName;

    private String hall;


    public Session() {
    }

    public Integer getId() {
        return id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    @Override
    public String toString() {
        return "Session{" +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", filmName='" + filmName + '\'' +
                ", hall='" + hall + '\'' +
                '}';
    }
}
