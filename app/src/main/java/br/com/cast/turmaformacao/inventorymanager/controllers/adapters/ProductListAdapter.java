package br.com.cast.turmaformacao.inventorymanager.controllers.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import br.com.cast.turmaformacao.inventorymanager.R;
import br.com.cast.turmaformacao.inventorymanager.model.entities.Product;
import br.com.cast.turmaformacao.inventorymanager.model.http.ProductService;

/**
 * Created by Administrador on 25/09/2015.
 */
public class ProductListAdapter extends BaseAdapter {

    private List<Product> productList;
    private Activity context;

    public ProductListAdapter(Activity context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = (Product) getItem(position);

        View productListItemView = context.getLayoutInflater().inflate(R.layout.list_item_product, parent, false);

        TextView textViewName = (TextView) productListItemView.findViewById(R.id.textViewNome);
        textViewName.setText(product.getNome());
        TextView textViewQtd = (TextView) productListItemView.findViewById(R.id.textViewQuantidade);
        textViewQtd.setText(product.getQuantidade().toString());
        TextView textViewDescricao = (TextView) productListItemView.findViewById(R.id.textViewDescricao);
        textViewDescricao.setText(product.getDescricao().toString());
        TextView textViewPreco = (TextView) productListItemView.findViewById(R.id.textViewPreco);
        textViewPreco.setText(product.getValorUnitario().toString());
        TextView textViewQtdMin = (TextView) productListItemView.findViewById(R.id.textViewQuantidadeMin);
        textViewQtdMin.setText(product.getQuantidadeMin().toString());
        TextView textViewData = (TextView) productListItemView.findViewById(R.id.textViewData);
        textViewData.setText(product.getDate().toString());

        ImageView imageViewImage = (ImageView) productListItemView.findViewById(R.id.imageView);
        imageViewImage.setImageBitmap(StringToBitMap(product.getImagem()));

      /*  Bitmap img = null;

        try {
            img = new GetImageFromWeb().execute(product.getImage()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        imageViewImage.setImageBitmap(img);*/

        return productListItemView;
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    private class GetImageFromWeb extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap img = null;


            try {
                img = BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return img;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);
        }
    }
}


