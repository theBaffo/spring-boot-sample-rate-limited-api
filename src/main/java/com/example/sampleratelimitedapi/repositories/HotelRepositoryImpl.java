package com.example.sampleratelimitedapi.repositories;

import com.example.sampleratelimitedapi.models.Hotel;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * <h1>HotelRepositoryImpl</h1>
 *
 * <p>The HotelRepositoryImpl implements the {@link HotelRepository} interface and calls the
 * corresponding {@link HotelJpaRepository} methods, and also applies the sorting logic.
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
@Repository
public class HotelRepositoryImpl implements HotelRepository {
  private HotelJpaRepository hotelJpaRepository;

  @Autowired
  public HotelRepositoryImpl(HotelJpaRepository hotelJpaRepository) {
    this.hotelJpaRepository = hotelJpaRepository;
  }

  /**
   * Returns a List of Hotel instances filtered by city (case-insensitive)
   *
   * @param city The city used to filter the result
   * @param sortByPrice can be set as "ASC" or "DESC" to sort the result by the Hotel price.
   * @return a List of Hotel instances filtered by city (case-insensitive)
   */
  @Override
  public List<Hotel> findAllHotelsByCity(String city, String sortByPrice) {
    List<Hotel> hotels = hotelJpaRepository.findByCityIgnoreCase(city);

    if (sortByPrice != null && !sortByPrice.isEmpty()) {
      if (sortByPrice.toUpperCase().equals("ASC"))
        hotels.sort(Comparator.comparing(Hotel::getPrice));
      else if (sortByPrice.toUpperCase().equals("DESC"))
        hotels.sort(Comparator.comparing(Hotel::getPrice).reversed());
    }

    return hotels;
  }

  /**
   * Returns a List of Hotel instances filtered by room (case-insensitive)
   *
   * @param room The room used to filter the result.
   * @param sortByPrice can be set as "ASC" or "DESC" to sort the result by the Hotel price.
   * @return a List of Hotel instances filtered by room (case-insensitive)
   */
  @Override
  public List<Hotel> findAllHotelsByRoom(String room, String sortByPrice) {
    List<Hotel> hotels = hotelJpaRepository.findByRoomIgnoreCase(room);

    if (sortByPrice != null && !sortByPrice.isEmpty()) {
      if (sortByPrice.toUpperCase().equals("ASC"))
        hotels.sort(Comparator.comparing(Hotel::getPrice));
      else if (sortByPrice.toUpperCase().equals("DESC"))
        hotels.sort(Comparator.comparing(Hotel::getPrice).reversed());
    }

    return hotels;
  }
}
