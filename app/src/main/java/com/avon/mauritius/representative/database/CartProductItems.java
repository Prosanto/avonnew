package com.avon.mauritius.representative.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CartProductItems", id = "_id")
public class CartProductItems extends Model {
    @Column(name = "productitems_id")
    public String productitems_id;
    @Column(name = "subproduct_id")
    public String subproduct_id;
    @Column(name = "name")
    public String name;
    @Column(name = "product_code")
    public String product_code;
    @Column(name = "subproduct_save_id")
    public long subproduct_save_id;


}
