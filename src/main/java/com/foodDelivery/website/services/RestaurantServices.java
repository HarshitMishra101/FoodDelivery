package com.foodDelivery.website.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foodDelivery.website.dto.RestaurantDTO;
import com.foodDelivery.website.exception.GlobalException;
import com.foodDelivery.website.model.FoodItems;
import com.foodDelivery.website.model.Restaurant;
import com.foodDelivery.website.repository.RestaurantRepository;

@Service
public class RestaurantServices {
	@Autowired
	private RestaurantRepository resRepo;
	
	public ResponseEntity<List<RestaurantDTO>> getAllRestaurant(){
		List<RestaurantDTO> resList = resRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
		return new ResponseEntity<>(resList, HttpStatus.OK);
	}
	public ResponseEntity<List<FoodItems>> getRestaurants(int restaurantId) throws GlobalException{
		Optional<Restaurant> resList = resRepo.findById(restaurantId);
		if (resList.isPresent()) {
            return new ResponseEntity<>(resList.get().getFoodItemsInRestaurant(), HttpStatus.OK);
        }
		else {
			throw new GlobalException("The following restaurant does not exist");
		}
	}
	
	public RestaurantDTO convertToDTO(Restaurant res) {
		RestaurantDTO resDTO = new RestaurantDTO();
		resDTO.setContactNo(res.getContactNo());
		resDTO.setLocation(res.getLocation());
		resDTO.setRestaurantName(res.getRestaurantName());
		resDTO.setRestaurantId(res.getRestaurantId());
		
		return resDTO;
		
	}
}
