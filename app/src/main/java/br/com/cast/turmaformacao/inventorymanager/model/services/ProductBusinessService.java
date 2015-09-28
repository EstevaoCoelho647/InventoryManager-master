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
}

