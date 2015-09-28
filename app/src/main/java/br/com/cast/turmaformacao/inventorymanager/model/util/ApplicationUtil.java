package br.com.cast.turmaformacao.inventorymanager.model.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrador on 16/09/2015.
 */
public final class ApplicationUtil{

    private static Context context;

    private static Context applicationContext;

    private ApplicationUtil(){

    }

    public static void setContext(Context context){
        ApplicationUtil.context = context;
    }

    public static Context getContext() {
        return ApplicationUtil.context;
    }
}
