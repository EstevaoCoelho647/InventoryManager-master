package br.com.cast.turmaformacao.inventorymanager.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.inventorymanager.model.entities.Product;

/**
 * Created by Administrador on 25/09/2015.
 */
public class ProductContract {
    public static String TABLE = "Product";
    public static String ID = "id";
    public static String WEBID = "webId";
    public static String NAME = "nome";
    public static String DESCRIPTION = "descricao";
    public static String QUANTIDADE = "quantidade";
    public static String QTDEMIN = "quantidadeMin";
    public static String VALORUNITARIO = "valorUnitario";
    public static String DATE = "date";
    public static String IMAGE = "image";

    public static final String[] COLUNS = {ID, WEBID, NAME, DESCRIPTION, QUANTIDADE, QTDEMIN, VALORUNITARIO, DATE, IMAGE};

    private ProductContract() {
    }

    public static String getCreateTableScript() {

        final StringBuilder create = new StringBuilder();

        create.append(" CREATE TABLE " + TABLE);
        create.append(" ( ");
        create.append(ID + " INTEGER PRIMARY KEY, ");
        create.append(WEBID + " INTEGER, ");
        create.append(NAME + " TEXT NOT NULL, ");
        create.append(DESCRIPTION + " TEXT, ");
        create.append(QUANTIDADE + " INTEGER, ");
        create.append(QTDEMIN + " INTEGER, ");
        create.append(VALORUNITARIO + " REAL, ");
        create.append(DATE + " INTEGER, ");
        create.append(IMAGE + " TEXT ");
        create.append(" ); ");

        return create.toString();
    }

    public static ContentValues getContentValues(Product product) {
        ContentValues values = new ContentValues();
        values.put(ProductContract.ID, product.getId());
        values.put(ProductContract.WEBID, product.getWebId());
        values.put(ProductContract.NAME, product.getNome());
        values.put(ProductContract.DESCRIPTION, product.getDescricao());
        values.put(ProductContract.QUANTIDADE, product.getQuantidade());
        values.put(ProductContract.QTDEMIN, product.getQuantidadeMin());
        values.put(ProductContract.VALORUNITARIO, product.getValorUnitario());
        values.put(ProductContract.DATE, product.getDate());
        values.put(ProductContract.IMAGE, product.getImagem());

        return values;
    }

    private static Product getProduct(Cursor cursor) {
        Product product = new Product();
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            product.setId(cursor.getLong(cursor.getColumnIndex(ProductContract.ID)));
            product.setWebId(cursor.getLong(cursor.getColumnIndex(ProductContract.WEBID)));
            product.setNome(cursor.getString(cursor.getColumnIndex(ProductContract.NAME)));
            product.setDescricao(cursor.getString(cursor.getColumnIndex(ProductContract.DESCRIPTION)));
            product.setQuantidade(cursor.getLong(cursor.getColumnIndex(ProductContract.QUANTIDADE)));
            product.setQuantidadeMin(cursor.getLong(cursor.getColumnIndex(ProductContract.QTDEMIN)));
            product.setValorUnitario(cursor.getDouble(cursor.getColumnIndex(ProductContract.VALORUNITARIO)));
            product.setDate(cursor.getLong(cursor.getColumnIndex(ProductContract.DATE)));
            product.setImagem(cursor.getString(cursor.getColumnIndex(ProductContract.IMAGE)));


            return product;
        }
        return null;
    }

    public static List getProducts(Cursor cursor) {
        ArrayList<Product> products = new ArrayList();
        while (cursor.moveToNext()) {
            products.add(getProduct(cursor));
        }
        return products;
    }


}

