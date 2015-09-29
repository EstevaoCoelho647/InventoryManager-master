package br.com.cast.turmaformacao.inventorymanager.controllers.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.cast.turmaformacao.inventorymanager.R;
import br.com.cast.turmaformacao.inventorymanager.model.entities.Product;
import br.com.cast.turmaformacao.inventorymanager.model.services.ProductBusinessService;
import br.com.cast.turmaformacao.inventorymanager.model.util.FormHelper;
import br.com.cast.turmaformacao.inventorymanager.controllers.sync.GetDBProducts;
import br.com.cast.turmaformacao.inventorymanager.controllers.sync.UpServerProducts;

/**
 * Created by Administrador on 25/09/2015.
 */
public class ProductFormActivity extends AppCompatActivity {
    EditText editTextNome;
    EditText editTextDescription;
    EditText editTextQtde;
    EditText editTextQtdeMin;
    EditText editTextValorUnit;
    ImageView imageViewImage;
    Button editImageButton;
    private static final int CAMERA_REQUEST = 1888;
    private UpServerProducts upServerProducts = new UpServerProducts(this);


    public static final String PARAM_PRODUCT = "PRODUCT";
    private Product product;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        initProduct();

        bindEditTextNome();
        bindEditTextDescription();
        bindEditTextQtde();
        bindEditTextQtdeMin();
        bindEditTextValorUnit();
        bindButtonImage();
        bindEditImageView();

    }

    private void bindButtonImage() {
        editImageButton = (Button) this.findViewById(R.id.buttonImageTake);
        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            bindEditImageView();
            String photoS  = bitMapToString(photo);
            imageViewImage.setImageBitmap(StringToBitMap(photoS));

        }
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

    private String  bitMapToString(Bitmap photo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    private void initProduct() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.product = extras.getParcelable(PARAM_PRODUCT);
        }
        this.product = product == null ? new Product() : this.product;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_product, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_add_product:
                onMenuAddClick();
                break;
            case R.id.menu_upload:
                onMenuUploadClick();

        }
        return super.onOptionsItemSelected(item);
    }

    private void onMenuUploadClick() {
        AsyncTask<String, String, List<Product>> execute = new GetDBProducts(ProductFormActivity.this).execute();
        String requiredMessage = "Campo Obrigatorio!";
        if (!FormHelper.validateRequired(requiredMessage, editTextNome, editTextQtde, editTextQtdeMin, editTextValorUnit)) {
            binProduct();
            ProductBusinessService.save(product);
            Toast.makeText(ProductFormActivity.this, "Produto adicionado com sucesso!", Toast.LENGTH_LONG).show();
            ProductFormActivity.this.finish();
            upServerProducts.execute();
        }

    }

    private void onMenuAddClick() {
        String requiredMessage = "Campo Obrigatorio!";
        if (!FormHelper.validateRequired(requiredMessage, editTextNome, editTextQtde, editTextQtdeMin, editTextValorUnit)) {
            binProduct();
            ProductBusinessService.save(product);
            Toast.makeText(ProductFormActivity.this, "Produto adicionado com sucesso!", Toast.LENGTH_LONG).show();
            ProductFormActivity.this.finish();
        }
    }

    private void binProduct() {
        product.setNome(editTextNome.getText().toString());
        product.setDescricao(editTextDescription.getText().toString());
        product.setQuantidade(Long.parseLong(editTextQtde.getText().toString()));
        product.setQuantidadeMin(Long.parseLong(editTextQtdeMin.getText().toString()));
        product.setValorUnitario(Double.parseDouble(editTextValorUnit.getText().toString()));

        imageViewImage.setDrawingCacheEnabled(true);
        Bitmap scaledBitmap = imageViewImage.getDrawingCache();
        product.setImagem(bitMapToString(scaledBitmap));

    }

    private void bindEditTextValorUnit() {
        editTextValorUnit = (EditText) findViewById(R.id.editTextValorUnit);
        editTextValorUnit.setText(product.getValorUnitario() == null ? "" : product.getValorUnitario().toString());
    }

    private void bindEditTextQtdeMin() {
        editTextQtdeMin = (EditText) findViewById(R.id.editTextQtdeMin);
        editTextQtdeMin.setText(product.getQuantidadeMin() == null ? "" : product.getQuantidadeMin().toString());
    }

    private void bindEditTextQtde() {
        editTextQtde = (EditText) findViewById(R.id.editTextQtde);
        editTextQtde.setText(product.getQuantidade() == null ? "" : product.getQuantidade().toString());
    }

    private void bindEditTextDescription() {
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextDescription.setText(product.getDescricao() == null ? "" : product.getDescricao());
    }

    private void bindEditTextNome() {
        editTextNome = (EditText) findViewById(R.id.editTextName);
        editTextNome.setText(product.getNome() == null ? "" : product.getNome());
    }

    private void bindEditImageView() {
        imageViewImage = (ImageView) findViewById(R.id.imageViewImage);
        imageViewImage.setImageBitmap(StringToBitMap(product.getImagem()));
    }

}
