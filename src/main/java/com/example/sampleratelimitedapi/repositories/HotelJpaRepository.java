package com.example.sampleratelimitedapi.repositories;

import com.example.sampleratelimitedapi.models.Hotel;
import java.util.List;
import org.springframework.data.repository.Repository;

/**
 *
 *
 * <h1>HotelJpaRepository</h1>
 *
 * <p>The HotelJpaRepository extends the {@link Repository} class and uses <a
 * href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation">
 * Spring "magic"</a> to create methods that retrieve Hotel instances filtered by city or room.
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
public interface HotelJpaRepository extends Repository<Hotel, Long> {
  List<Hotel> findByCityIgnoreCase(String city);

  List<Hotel> findByRoomIgnoreCase(String room);
}
