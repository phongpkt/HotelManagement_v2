package com.example.HotelManagement.service.metamodel_;

import com.example.HotelManagement.model.Gallery;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Gallery.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Gallery_ {


    public static final String IMAGE_URL = "image_url";
    public static final String ID = "id";
    public static final String ROOM_TYPE = "room_type";
    /**
     * @see Gallery#image_url
     **/
    public static volatile SingularAttribute<Gallery, String> image_url;
    /**
     * @see Gallery#id
     **/
    public static volatile SingularAttribute<Gallery, Long> id;
    /**
     * @see Gallery
     **/
    public static volatile EntityType<Gallery> class_;
    /**
     * @see Gallery#room_type
     **/
    public static volatile SingularAttribute<Gallery, Long> room_type;

}

