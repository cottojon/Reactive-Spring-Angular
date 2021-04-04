package com.landon.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.landon.hotel.model.Reservation;

import reactor.core.publisher.Mono;


@Service
public class ReservationServiceImpl implements ReservationService{
	
	private final ReactiveMongoOperations reactiveMongoOperations;
	
	@Autowired
	public ReservationServiceImpl(ReactiveMongoOperations reactiveMongoOperations) {
		this.reactiveMongoOperations = reactiveMongoOperations;
	}
	
	@Override
	public Mono<Reservation> getReservation(String id) {
		return reactiveMongoOperations.findById(id, Reservation.class);
	}

	@Override
	public Mono<Reservation> createReservation(Mono<Reservation> reservationMono) {
		return reactiveMongoOperations.save(reservationMono);
	}

	@Override
	public Mono<Reservation> updateReservation(String id, Mono<Reservation> reservationMono) {
		
		//save is an upsert: it will update it if id already exist, if not it creates a new reservation
		//the problem with this is will update all fields
		//we don't want to do that, we will only allow the price to be updated
		//return reactiveMongoOperations.save(reservationMono);
		
		//update just the price
		return reservationMono.flatMap(reservation -> {
			return reactiveMongoOperations.findAndModify( //find and modify the reservation
					Query.query(Criteria.where("id").is(id)),
					Update.update("price", reservation.getPrice()), Reservation.class
					).flatMap(result -> { //then update the price so the front end can see we have updated it
						result.setPrice(reservation.getPrice());
						return Mono.just(result);
					});
		});
	}

	@Override
	public Mono<Boolean> deleteReservation(String id) {
		//remove returns Mono<DeleteResult>
		//this object has two fields, acknowledge boolean and deleteCount
		return reactiveMongoOperations.remove(
				Query.query(Criteria.where("id").is(id)),  Reservation.class)
				.flatMap(deleteResult -> Mono.just(deleteResult.wasAcknowledged()));
	};

}
