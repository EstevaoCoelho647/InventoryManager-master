package br.com.cast.turmaformacao.inventorymanager;

import android.app.Application;

import br.com.cast.turmaformacao.inventorymanager.model.util.ApplicationUtil;

/**
 * Created by Administrador on 25/09/2015.
 */
public class ProductManagerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtil.setContext(getApplicationContext());
    }
}

