import { Component } from '@angular/core';
import {ControlContainer, FormControl, FormGroup} from '@angular/forms';
import { ReservationService } from './reservation.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'reservation-app-two';

  constructor(private ReservationService: ReservationService){

  }

  rooms: Room[];
  roomSearchForm: FormGroup;
  currentCheckInVal: string;
  currentCheckoutVal: string;
  currentPrice: number;
  currentRoomNumber: number;

  ngOnInit(){
    this.roomSearchForm = new FormGroup({
      checkIn: new FormControl(''),
      checkOut: new FormControl(''),
      roomNumber: new FormControl('')
    });

    //when values in roomSearchForm change we run this lambda
    this.roomSearchForm.valueChanges.subscribe(form => {
      this.currentCheckInVal = form.checkIn;
      this.currentCheckoutVal = form.checkOut;
      //the roomnumber and room price will be a string seperated by a |
      if(form.roomNumber){
        let roomValues: String[] = form.roomNumber.split('|');
        this.currentRoomNumber = Number(roomValues[0]);
        this.currentPrice = Number(roomValues[1]);
      }
    });

    this.rooms = [new Room("127", "127", "150"),
    new Room("138", "138", "180"),
    new Room("254", "254", "200")
    ];
  }
}


export class Room {
  id: string;
  roomNumber: string;
  price: string;


  constructor(id: string, roomNumber: string, price: string){
    this.id = id;
    this.price = price;
    this.roomNumber = roomNumber;
  }
}
