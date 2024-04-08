package com.example.HotelManagement.service.metamodel_;

import com.example.HotelManagement.model.Booking;
import com.example.HotelManagement.model.Guest;
import com.example.HotelManagement.model.Payment;
import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.enums.BookingStatus;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.util.Date;

@StaticMetamodel(Booking.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Booking_ {


    public static final String CHECK_OUT_DATE = "checkOutDate";
    public static final String TOTAL_PRICE = "totalPrice";
    public static final String PAYMENTS = "payments";
    public static final String GUEST = "guest";
    public static final String ID = "id";
    public static final String CHECK_IN_DATE = "checkInDate";
    public static final String ROOM = "room";
    public static final String STATUS = "status";
    /**
     * @see Booking#checkOutDate
     **/
    public static volatile SingularAttribute<Booking, Date> checkOutDate;
    /**
     * @see Booking#totalPrice
     **/
    public static volatile SingularAttribute<Booking, Double> totalPrice;
    /**
     * @see Booking#payments
     **/
    public static volatile ListAttribute<Booking, Payment> payments;
    /**
     * @see Booking#guest
     **/
    public static volatile SingularAttribute<Booking, Guest> guest;
    /**
     * @see Booking#id
     **/
    public static volatile SingularAttribute<Booking, Long> id;
    /**
     * @see Booking
     **/
    public static volatile EntityType<Booking> class_;
    /**
     * @see Booking#checkInDate
     **/
    public static volatile SingularAttribute<Booking, Date> checkInDate;
    /**
     * @see Booking#room
     **/
    public static volatile SingularAttribute<Booking, Room> room;
    /**
     * @see Booking#status
     **/
    public static volatile SingularAttribute<Booking, BookingStatus> status;

}

