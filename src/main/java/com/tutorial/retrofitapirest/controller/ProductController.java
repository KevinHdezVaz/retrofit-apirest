package com.tutorial.retrofitapirest.controller;

import com.tutorial.retrofitapirest.dto.ProductDTO;
import com.tutorial.retrofitapirest.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {

    private List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(1, "Antrax", 
            "El ántrax (o carbunco) es una enfermedad infecciosa grave causada por una bacteria grampositiva y baciliforme conocida como Bacillus anthracis. El ántrax puede encontrarse naturalmente en la tierra y afecta comúnmente a mascotas y animales salvajes en todo el mundo. Aunque no es lo habitual, las personas pueden contraer el ántrax si están en contacto con animales infectados o productos de origen animal contaminados."))
         
            


    );

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getOne(@PathVariable("id") int id) {
        Product product = findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO dto) {
        int index = products.isEmpty()? 1 : getLastIndex() + 1;
        Product product = Product.builder().id(index).name(dto.getName()).price(dto.getPrice()).build();
        products.add(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> update(@PathVariable("id") int id, @RequestBody ProductDTO dto) {
        Product product = findById(id);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") int id) {
        Product product = findById(id);
        products.remove(product);
        return ResponseEntity.ok(product);
    }

    private int getLastIndex() {
        return products.stream().max(Comparator.comparing(Product::getId)).get().getId();
    }

    private Product findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findAny().orElse(null);
    }
}
