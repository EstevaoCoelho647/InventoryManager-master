package br.com.cast.turmaformacao.inventorymanager.model.services;


import java.util.List;

import br.com.cast.turmaformacao.inventorymanager.model.entities.Product;
import br.com.cast.turmaformacao.inventorymanager.model.persistence.ProductRepository;

/**
 * Created by Administrador on 25/09/2015.
 */
public class ProductBusinessService {
    private ProductBusinessService() {
        super();
    }

    public static void save(Product product) {
        ProductRepository.save(product);

    }
    public static List<Product> findAll() {
        List<Product> products = ProductRepository.getAll();

        return products;
    }


    public static void delete(Product selectedProduct) {
        ProductRepository.delete(selectedProduct.getId());
    }

    public static void synchronize(Product product) {
        ProductRepository.save(product);
    }

    public static void sincronized(){

        List<Product> listaWeb = ProductRepository.getAll();

        for(Product pWeb : listaWeb){
            Long id = ProductBusinessService.getIdByWebId(pWeb.getWebId());
            if(id == null){
                save(pWeb);
            }else{
                pWeb.setId(id);
                Product produtoLocal = ProductBusinessService.getById(id);
                if(checkLastModified(produtoLocal,pWeb)){
                    save(pWeb);
                }
            }
        }
    }
    private static boolean checkLastModified(Product local, Product web){

        if(web.getDate() > local.getDate()){
            return true;
        }

        return false;

    }

    public static Product getById(Long id){
        return ProductRepository.getById(id);
    }

    public static Long getIdByWebId(Long web_id) {

        return ProductRepository.getIdByWebId(web_id);
    }

}

