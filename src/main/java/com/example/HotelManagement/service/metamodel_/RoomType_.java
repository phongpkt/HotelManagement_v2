package com.example.HotelManagement.service.metamodel_;

import com.example.HotelManagement.model.Room;
import com.example.HotelManagement.model.RoomType;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(RoomType.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class RoomType_ {


    public static final String ROOMS = "rooms";
    public static final String IMAGE_URLS = "imageUrls";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String ID = "id";
    public static final String PRICE_PER_NIGHT = "pricePerNight";
    public static final String CAPACITY = "capacity";
    /**
     * @see RoomType#rooms
     **/
    public static volatile ListAttribute<RoomType, Room> rooms;
    /**
     * @see RoomType#imageUrls
     **/
    public static volatile ListAttribute<RoomType, String> imageUrls;
    /**
     * @see RoomType#name
     **/
    public static volatile SingularAttribute<RoomType, String> name;
    /**
     * @see RoomType#description
     **/
    public static volatile SingularAttribute<RoomType, String> description;
    /**
     * @see RoomType#id
     **/
    public static volatile SingularAttribute<RoomType, Long> id;
    /**
     * @see RoomType
     **/
    public static volatile EntityType<RoomType> class_;
    /**
     * @see RoomType#pricePerNight
     **/
    public static volatile SingularAttribute<RoomType, Double> pricePerNight;
    /**
     * @see RoomType#capacity
     **/
    public static volatile SingularAttribute<RoomType, Integer> capacity;

}

