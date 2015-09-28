package br.com.cast.turmaformacao.inventorymanager.controllers.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.cast.turmaformacao.inventorymanager.R;
import br.com.cast.turmaformacao.inventorymanager.controllers.adapters.ProductListAdapter;
import br.com.cast.turmaformacao.inventorymanager.model.entities.Product;
import br.com.cast.turmaformacao.inventorymanager.model.services.ProductBusinessService;
import br.com.cast.turmaformacao.inventorymanager.model.util.GetDBProducts;
import br.com.cast.turmaformacao.inventorymanager.model.util.GetServerProducts;


public class ProductListActivity extends AppCompatActivity {

    private ListView listViewProductList;
    private Product selectedProduct;
    private GetDBProducts productList = new GetDBProducts(this);
    private GetServerProducts serverProductsList = new GetServerProducts(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);


        binProductList();
        productList.execute();
    }

    protected void onResume() {
        List<Product> values = ProductBusinessService.findAll();
        listViewProductList.setAdapter(new ProductListAdapter(this, values));
        ProductListAdapter adapter = (ProductListAdapter) listViewProductList.getAdapter();
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    private void binProductList() {
        listViewProductList = (ListView) findViewById(R.id.listViewProductList);
        registerForContextMenu(listViewProductList);
        listViewProductList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ProductListAdapter adapter = (ProductListAdapter) listViewProductList.getAdapter();
                selectedProduct = (Product) adapter.getItem(position);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_product, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_add:
                onMenuAddProductClick();
                break;
           case R.id.menu_refresh:
                try {
                    onMenuRefreshClick();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    private void onMenuAddProductClick() {
        Intent goToProductFormActivity = new Intent(ProductListActivity.this, ProductFormActivity.class);
        startActivity(goToProductFormActivity);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context_product_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                onMenuDeleteClick();
                break;
           case R.id.menu_edit:
                onMenuEditClick();
        }
        return super.onContextItemSelected(item);

    }

    private void onMenuRefreshClick() throws ExecutionException, InterruptedException {
        AsyncTask<String, String, List<Product>> execute = new GetDBProducts(ProductListActivity.this).execute();
        List<Product> productsBanco = ProductBusinessService.findAll();

        int taskHttpSize = execute.get().size();

        for (int i = 0; i < taskHttpSize; i++) {
            Product taskHttp = execute.get().get(0);
            if (productsBanco.size() > 0) {
                for (Product p : productsBanco) {
                    if (p.getWebId().equals(taskHttp.getId())) {
                        taskHttp.setWebId(p.getId());
                        ProductBusinessService.synchronize(execute.get().get(i));
                        Toast.makeText(ProductListActivity.this, "Tasks Atualizadas!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                ProductBusinessService.synchronize(execute.get().get(i));
                Toast.makeText(ProductListActivity.this, "Novas tasks Criadas!", Toast.LENGTH_SHORT).show();
            }
        }
        serverProductsList.execute();
        updateProductList();
    }


    private void onMenuEditClick() {
        Intent goToListForm = new Intent(ProductListActivity.this, ProductFormActivity.class);
        goToListForm.putExtra("PRODUCT", selectedProduct);
        startActivity(goToListForm);
    }


    private void onMenuDeleteClick() {
        new AlertDialog.Builder(ProductListActivity.this)
                .setTitle(R.string.lbl_confirm)
                .setMessage(R.string.msg_confirm_delete)
                .setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProductBusinessService.delete(selectedProduct);
                        selectedProduct = null;
                        String message = getString(R.string.mgs_delete);
                        updateProductList();
                        Toast.makeText(ProductListActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton(R.string.lbl_no, null)
                .create()
                .show();
    }

    private void updateProductList() {
        List<Product> values = ProductBusinessService.findAll();
        listViewProductList.setAdapter(new ProductListAdapter(this, values));
        ProductListAdapter adapter = (ProductListAdapter) listViewProductList.getAdapter();
        adapter.notifyDataSetChanged();
    }


}