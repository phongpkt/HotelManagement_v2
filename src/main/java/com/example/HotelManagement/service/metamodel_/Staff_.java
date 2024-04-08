package com.example.HotelManagement.service.metamodel_;

import com.example.HotelManagement.model.Hotel;
import com.example.HotelManagement.model.Staff;
import com.example.HotelManagement.model.enums.StaffRole;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Staff.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Staff_ {


    public static final String PASSWORD = "password";
    public static final String ROLE = "role";
    public static final String PHONE = "phone";
    public static final String FIRST_NAME = "FirstName";
    public static final String HOTEL = "hotel";
    public static final String ID = "id";
    public static final String LAST_NAME = "LastName";
    public static final String EMAIL = "email";
    /**
     * @see Staff#password
     **/
    public static volatile SingularAttribute<Staff, String> password;
    /**
     * @see Staff#role
     **/
    public static volatile SingularAttribute<Staff, StaffRole> role;
    /**
     * @see Staff#phone
     **/
    public static volatile SingularAttribute<Staff, String> phone;
    /**
     * @see Staff#FirstName
     **/
    public static volatile SingularAttribute<Staff, String> FirstName;
    /**
     * @see Staff#hotel
     **/
    public static volatile SingularAttribute<Staff, Hotel> hotel;
    /**
     * @see Staff#id
     **/
    public static volatile SingularAttribute<Staff, Long> id;
    /**
     * @see Staff#LastName
     **/
    public static volatile SingularAttribute<Staff, String> LastName;
    /**
     * @see Staff
     **/
    public static volatile EntityType<Staff> class_;
    /**
     * @see Staff#email
     **/
    public static volatile SingularAttribute<Staff, String> email;

}

