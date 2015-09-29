package br.com.cast.turmaformacao.inventorymanager.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.cast.turmaformacao.inventorymanager.model.entities.Product;

/**
 * Created by Administrador on 25/09/2015.
 */
public class ProductRepository {

    public static void save(Product product) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = ProductContract.getContentValues(product);

        if (product.getId() == null) {
            db.insert(ProductContract.TABLE, null, values);
        } else {
            String where = ProductContract.ID + " = ?";
            String[] params = {product.getId().toString()};
            db.update(ProductContract.TABLE, values, where, params);
        }
        db.close();
        databaseHelper.close();
    }

    public static List<Product> getAll() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(ProductContract.TABLE, ProductContract.COLUNS, null, null, null, null, ProductContract.ID);
        List<Product> values = ProductContract.getProducts(cursor);

        db.close();
        databaseHelper.close();
        return values;
    }

    public static void delete(long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = ProductContract.ID + " = ? ";
        String[] params = {String.valueOf(id)};
        db.delete(ProductContract.TABLE, where, params);

        db.close();
        databaseHelper.close();
    }

    public static Long getIdByWebId(Long web_id) {

        DatabaseHelper dataBaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        String where = ProductContract.WEBID + " = ? ";
        String[] params = {web_id.toString()};

        Cursor cursor = db.query(ProductContract.TABLE, ProductContract.COLUNS, where, params, null, null, null);

        Product p = ProductContract.getProduct(cursor);

        return p == null ? null : p.getId();
    }

    public static Product getById(Long id) {

        DatabaseHelper dataBaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        String where = ProductContract.ID + " = ? ";
        String[] params = {id.toString()};

        Cursor cursor = db.query(ProductContract.TABLE, ProductContract.COLUNS, where, params, null, null, null);

        Product product = ProductContract.getProduct(cursor);

        return product;


    }
}
