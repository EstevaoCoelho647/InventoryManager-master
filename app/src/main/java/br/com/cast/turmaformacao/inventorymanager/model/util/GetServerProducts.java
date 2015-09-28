package br.com.cast.turmaformacao.inventorymanager.model.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.cast.turmaformacao.inventorymanager.R;
import br.com.cast.turmaformacao.inventorymanager.model.entities.Product;
import br.com.cast.turmaformacao.inventorymanager.model.http.ProductService;
import br.com.cast.turmaformacao.inventorymanager.model.services.ProductBusinessService;

/**
 * Created by Administrador on 28/09/2015.
 */
public class GetServerProducts extends AsyncTask<String, String, List<Product>> {

    private Context context;
    private ProgressDialog progress;

    public GetServerProducts(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        progress.setMessage(context.getString(R.string.msg_loading_message));
        progress.show();
        super.onPreExecute();
    }

    @Override
    protected List<Product> doInBackground(String... params) {
        int cont = 0;

        for (long i = 0; i < 10000; i++) {
            publishProgress("Loading");
            cont++;
        }
        List<Product> products;
        products = ProductService.getProductById();

        return products;
    }

    @Override
    protected void onPostExecute(List<Product> products) {
        progress.dismiss();
    }

    @Override
    protected void onProgressUpdate(String... params) {
        progress.setMessage(params[0]);

    }
}


