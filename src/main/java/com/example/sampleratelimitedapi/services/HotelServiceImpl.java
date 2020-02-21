package com.example.sampleratelimitedapi.services;

import com.example.sampleratelimitedapi.models.Hotel;
import com.example.sampleratelimitedapi.repositories.HotelRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * <h1>HotelServiceImpl</h1>
 *
 * <p>The HotelServiceImpl implements the {@link HotelService} interface and calls the corresponding
 * {@link HotelRepository} methods.
 *
 * @author Marco Giuliani
 * @version 1.0
 * @since 2020-02-23
 */
@Service
public class HotelServiceImpl implements HotelService {
  private HotelRepository hotelRepository;

  @Autowired
  public HotelServiceImpl(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
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
    return hotelRepository.findAllHotelsByCity(city, sortByPrice);
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
    return hotelRepository.findAllHotelsByRoom(room, sortByPrice);
  }
}
