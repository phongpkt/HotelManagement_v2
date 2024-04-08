package com.example.HotelManagement.service.metamodel_;

import com.example.HotelManagement.model.Booking;
import com.example.HotelManagement.model.Guest;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.util.Date;

@StaticMetamodel(Guest.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Guest_ {


    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String BOOKING = "booking";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    /**
     * @see Guest#firstName
     **/
    public static volatile SingularAttribute<Guest, String> firstName;
    /**
     * @see Guest#lastName
     **/
    public static volatile SingularAttribute<Guest, String> lastName;
    /**
     * @see Guest#booking
     **/
    public static volatile SingularAttribute<Guest, Booking> booking;
    /**
     * @see Guest#address
     **/
    public static volatile SingularAttribute<Guest, String> address;
    /**
     * @see Guest#phone
     **/
    public static volatile SingularAttribute<Guest, String> phone;
    /**
     * @see Guest#date_of_birth
     **/
    public static volatile SingularAttribute<Guest, Date> date_of_birth;
    /**
     * @see Guest#id
     **/
    public static volatile SingularAttribute<Guest, Long> id;
    /**
     * @see Guest
     **/
    public static volatile EntityType<Guest> class_;
    /**
     * @see Guest#email
     **/
    public static volatile SingularAttribute<Guest, String> email;

}

