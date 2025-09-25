package com.avon.mauritius.representative.database;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryHelper {
    public static ArrayList<CartProducts> getProducts() {
        ArrayList<CartProducts> notiDBs = new ArrayList<CartProducts>();
        List<CartProducts> teamDB = new Select().all()
                .from(CartProducts.class)
                .execute();
        notiDBs.addAll(teamDB);
        return notiDBs;
    }

    public static ArrayList<CartProducts> getProductsbyId(String productID) {
        ArrayList<CartProducts> notiDBs = new ArrayList<CartProducts>();
        List<CartProducts> teamDB = new Select().all()
                .from(CartProducts.class)
                .where("product_id = ?", productID)
                .execute();
        notiDBs.addAll(teamDB);
        return notiDBs;
    }

    public static CartProducts getCartProducts(long product_id) {
        return new Select()
                .from(CartProducts.class)
                .where("_id = ?", product_id)
                .executeSingle();
    }

    public static CartProducts getCartProductID(String product_id) {
        return new Select()
                .from(CartProducts.class)
                .where("product_id = ?", product_id)
                .executeSingle();
    }


    //===============SubProducts=============
    public static ArrayList<CartSubProducts> getSubProducts(String product_id) {
        ArrayList<CartSubProducts> notiDBs = new ArrayList<CartSubProducts>();
        List<CartSubProducts> teamDB = new Select().all()
                .from(CartSubProducts.class)
                .where("product_id = ?", product_id)
                .execute();
        notiDBs.addAll(teamDB);
        return notiDBs;
    }

    public static ArrayList<CartSubProducts> getSubProductsbyproductsave(long product_save_id) {
        ArrayList<CartSubProducts> notiDBs = new ArrayList<CartSubProducts>();
        List<CartSubProducts> teamDB = new Select().all()
                .from(CartSubProducts.class)
                .where("product_save_id = ?", product_save_id)
                .execute();
        notiDBs.addAll(teamDB);
        return notiDBs;
    }

    public static CartSubProducts getCartSubProducts(String subproduct_id) {
        return new Select()
                .from(CartSubProducts.class)
                .where("subproduct_id = ?", subproduct_id)
                .executeSingle();
    }

    //===============Products Item=============


    public static ArrayList<CartProductItems> getCartProductItemsList(long subproduct_save_id) {
        ArrayList<CartProductItems> notiDBs = new ArrayList<CartProductItems>();
        List<CartProductItems> teamDB = new Select().all()
                .from(CartProductItems.class)
                .where("subproduct_save_id = ?", subproduct_save_id)
                .execute();
        notiDBs.addAll(teamDB);
        return notiDBs;
    }


    //============Delete Product=======

    public static void DeleteCartProductsbyProductID(String product_id) {

        ArrayList<CartProducts> allCartProducts = getProductsbyId(product_id);
        for (CartProducts mCartProducts : allCartProducts) {
            ArrayList<CartSubProducts> allCartSubProducts = getSubProductsbyproductsave(mCartProducts.getId());
            for (CartSubProducts mCartSubProducts : allCartSubProducts) {
                new Delete().from(CartProductItems.class).
                        where("subproduct_save_id = ?", mCartSubProducts.getId())
                        .execute();
            }
            new Delete().from(CartSubProducts.class)
                    .where("product_save_id = ?", mCartProducts.getId()).execute();

            new Delete().from(CartProducts.class)
                    .where("product_id = ?", product_id)
                    .execute();
        }

    }

    public static void DeleteCartProductsByID(long product_id) {

        CartProducts mCartProducts = getCartProducts(product_id);
        if (mCartProducts != null) {

            ArrayList<CartSubProducts> allCartProducts = getSubProductsbyproductsave(product_id);
            for (CartSubProducts mCartSubProducts : allCartProducts) {
                new Delete().from(CartProductItems.class).
                        where("subproduct_save_id = ?", mCartSubProducts.getId())
                        .execute();

                new Delete().from(CartSubProducts.class)
                        .where("_id = ?", mCartSubProducts.getId()).execute();

            }
            new Delete().from(CartProducts.class)
                    .where("_id = ?", product_id)
                    .execute();
        }

    }

    public static void DeleteAllData() {
        new Delete().from(CartProductItems.class).execute();
        new Delete().from(CartSubProducts.class).execute();
        new Delete().from(CartProducts.class).execute();

    }


}
