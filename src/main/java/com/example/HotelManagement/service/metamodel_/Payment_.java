package com.example.HotelManagement.service.metamodel_;

import com.example.HotelManagement.model.Booking;
import com.example.HotelManagement.model.Payment;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.util.Date;

@StaticMetamodel(Payment.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Payment_ {


    public static final String TOTAL_AMOUNT = "totalAmount";
    public static final String BOOKING = "booking";
    public static final String ID = "id";
    public static final String PAYMENT_DATE = "payment_date";
    public static final String PAYMENT_METHOD = "payment_method";
    /**
     * @see Payment#totalAmount
     **/
    public static volatile SingularAttribute<Payment, Double> totalAmount;
    /**
     * @see Payment#booking
     **/
    public static volatile SingularAttribute<Payment, Booking> booking;
    /**
     * @see Payment#id
     **/
    public static volatile SingularAttribute<Payment, Long> id;
    /**
     * @see Payment
     **/
    public static volatile EntityType<Payment> class_;
    /**
     * @see Payment#payment_date
     **/
    public static volatile SingularAttribute<Payment, Date> payment_date;
    /**
     * @see Payment#payment_method
     **/
    public static volatile SingularAttribute<Payment, String> payment_method;

}

