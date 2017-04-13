package pl.lucasjasek.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.lucasjasek.model.Session;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Integer> {

    @Query("select s.id FROM Session s WHERE s.hall = :hall and s.date = :date and s.time = :time")
    List<Integer> findSessionIdByCriteriaList(@Param("hall") String hall,
                                              @Param("date") String date,
                                              @Param("time") String time);

    @Query("select s.id FROM Session s WHERE s.date = :date and s.time = :time")
    Integer findSessionIdByCriteriaList(@Param("date") String date,
                                        @Param("time") String time);

    @Query("select s.time from Session s where s.filmName = :filmName and s.date = :date")
    List<String> findFilmScheduleTimeByCriteriaList(@Param("filmName") String filmName,
                                                    @Param("date") String date);

    @Query("select s.hall from Session s where s.time = :time and s.date = :date")
    String findHallByTimeAndDate(@Param("time") String time,
                                 @Param("date") String date);

    Session findSessionById(Integer id);
}
