package com.avon.mauritius.representative.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CartProducts", id = "_id")
public class CartProducts extends Model {
    @Column(name = "product_id")
    public String product_id;
    @Column(name = "name")
    public String name;
    @Column(name = "product_type")
    public String product_type;
    @Column(name = "product_code")
    public String product_code;
    @Column(name = "price")
    public String price;
    @Column(name = "limit_product")
    public String limit_product;
    @Column(name = "picture")
    public String picture;
    @Column(name = "quantity")
    public String quantity;

}
