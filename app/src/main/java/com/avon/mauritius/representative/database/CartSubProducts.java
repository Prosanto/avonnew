package com.avon.mauritius.representative.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CartSubProducts", id = "_id")
public class CartSubProducts extends Model {
    @Column(name = "subproduct_id")
    public String subproduct_id;
    @Column(name = "product_id")
    public String product_id;
    @Column(name = "product_save_id")
    public long product_save_id;
    @Column(name = "name")
    public String name;
    @Column(name = "product_code")
    public String product_code;


}
