package com.project.shoppingcart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    //PRIMARY KEY
    @Id // this makes 'id' field as primary key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tells JPA to auto generate a unique value for 'id' field
    private Long id; // store unique identifier for each image
    private String fileName;
    private String fileType;

    @Lob //tells JPA to map the Blob field as a Large Object(LOB) in the database.
    private Blob image;//This field stores the actual image data in binary form using a Blob (Binary Large Object)
    private String downloadURL;

    //FOREIGN KEY
    @ManyToOne // defines many-to-one relationship with 'Product' entity[many images can be associated with one product]
    @JoinColumn(name = "product_id")// this field will store the foreign key that links to 'Product' entity.  In the 'Image' table, the column 'product_id' will hold the ID of the related 'Product'
    private Product product;//This is the reference to the associated 'Product' entity. It allows you to access the product related to the image.
}
