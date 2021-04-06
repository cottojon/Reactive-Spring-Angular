package com.landon.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.landon.hotel.model.Reservation;
import com.landon.hotel.service.ReservationService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ReservationResource.ROOM_V1_RESERVATION)
@CrossOrigin //angular app and spring app run on different ports locally
public class ReservationResource {
	
	public static final String ROOM_V1_RESERVATION = "/room/v1/reservation/";
	
	private final ReservationService reservationService;
	
	
	@Autowired
	public ReservationResource(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Mono<Reservation> getReservationById(@PathVariable String id){
		return reservationService.getReservation(id);
	}
	
	
	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Mono<Reservation> createReservation(@RequestBody Mono<Reservation> reservation){
		return reservationService.createReservation(reservation);
	}
	
	@PutMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Mono<Reservation> updatePrice(@PathVariable String id, @RequestBody Mono<Reservation> reservation){
		return reservationService.updateReservation(id, reservation);
	}
	
	@DeleteMapping(path = "{id}")
	public Mono<Boolean> deleteReservation(@PathVariable String id){
		return reservationService.deleteReservation(id);
	}
}
