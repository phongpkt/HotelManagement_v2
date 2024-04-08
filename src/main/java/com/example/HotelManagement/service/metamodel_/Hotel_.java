package com.example.HotelManagement.service.metamodel_;

import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.Staff;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Hotel.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Hotel_ {


    public static final String ROOMS = "rooms";
    public static final String ADDRESS = "Address";
    public static final String PHONE = "Phone";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String STAFFS = "staffs";
    /**
     * @see Hotel#rooms
     **/
    public static volatile ListAttribute<Hotel, Room> rooms;
    /**
     * @see Hotel#Address
     **/
    public static volatile SingularAttribute<Hotel, String> Address;
    /**
     * @see Hotel#Phone
     **/
    public static volatile SingularAttribute<Hotel, String> Phone;
    /**
     * @see Hotel#name
     **/
    public static volatile SingularAttribute<Hotel, String> name;
    /**
     * @see Hotel#id
     **/
    public static volatile SingularAttribute<Hotel, Long> id;
    /**
     * @see Hotel
     **/
    public static volatile EntityType<Hotel> class_;
    /**
     * @see Hotel#staffs
     **/
    public static volatile ListAttribute<Hotel, Staff> staffs;

}

