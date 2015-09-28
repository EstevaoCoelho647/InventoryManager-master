package br.com.cast.turmaformacao.inventorymanager.model.http;

import android.graphics.Bitmap;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.com.cast.turmaformacao.inventorymanager.model.entities.Product;

/**
 * Created by Administrador on 28/09/2015.
 */
public class ProductService {

    private static final String URL = "http://10.11.21.193:4000/api/v1/products/";

    private ProductService() {

    }

    public static List<Product> getProductById() {
        List<Product> products = null;

        try {
            java.net.URL url = new URL(URL);

            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            Log.i("getProductById", "Codigo de retorno da requisição de id: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();

                ObjectMapper objectMapper = new ObjectMapper();
                CollectionType construct = objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class);
                products = objectMapper.readValue(inputStream, construct);

            }
        } catch (Exception e) {
            Log.e(ProductService.class.getName(), e.getMessage());
        }
        return products;
    }

    public static Product setProductById() {
        Product product = null;

        try {
            java.net.URL url = new URL(URL);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = conn.getOutputStream();

            int responseCode = conn.getResponseCode();
            new ObjectMapper().writeValue(outputStream, product);


            outputStream.flush();
            outputStream.close();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error code" + responseCode);
            }

            conn.disconnect();

        } catch (Exception e) {
            Log.e(ProductService.class.getName(), "" + e.getMessage());
        }
        return product;
    }


}

