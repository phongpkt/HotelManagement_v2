package com.example.HotelManagement.service.metamodel_;

import com.example.HotelManagement.model.Booking;
import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.RoomType;
import com.example.HotelManagement.model.enums.RoomStatus;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Room.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Room_ {


    public static final String STATUS = "Status";
    public static final String HOTEL = "hotel";
    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String BOOKINGS = "bookings";
    /**
     * @see Room#Status
     **/
    public static volatile SingularAttribute<Room, RoomStatus> Status;
    /**
     * @see Room#hotel
     **/
    public static volatile SingularAttribute<Room, Hotel> hotel;
    /**
     * @see Room#id
     **/
    public static volatile SingularAttribute<Room, Long> id;
    /**
     * @see Room#type
     **/
    public static volatile SingularAttribute<Room, RoomType> type;
    /**
     * @see Room#bookings
     **/
    public static volatile ListAttribute<Room, Booking> bookings;
    /**
     * @see Room
     **/
    public static volatile EntityType<Room> class_;

}

