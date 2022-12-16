/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wsa.restfull1;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Product.Product;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author iqbal al habib
 */

@RestController
public class ProductServiceController {
    //creating map/array
    private static Map<String, Product> productRepo = new HashMap<>();
    
    //creating product object
    static {
        Product honey = new Product();
        honey.setId("1");
        honey.setName("Honey");
        honey.setPrice(2000000.00);
        honey.setDisc(50);
        honey.setTotal(honey.getPrice(),honey.getDisc());
        productRepo.put(honey.getId(), honey);
        
        Product almond = new Product();
        almond.setId("2");
        almond.setName("Almond");
        almond.setPrice(4000000.00);
        almond.setDisc(50);
        almond.setTotal(almond.getPrice(),almond.getDisc());
        productRepo.put(almond.getId(), almond);
    }
    
    //get request
    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProducts(
            @RequestParam(value = "name", required = false, defaultValue = "honey") String name
    ) {
       return new ResponseEntity<>(productRepo.values(), HttpStatus.OK); 
    }
    
    //post request
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        //condition if the product already exists
        if (productRepo.containsKey(product.getId())){
            return new ResponseEntity<>("Product already exists", HttpStatus.CONFLICT);
        }
        else{
            product.setTotal(product.getPrice(), product.getDisc());
            productRepo.put(product.getId(), product);
            return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
        }
    }
    
    
    //put request
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
   public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product) { 
      //condition if the product isn't exists
      if(!productRepo.containsKey(id)){
          return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
      }
      else {
        productRepo.remove(id);
        product.setId(id);
        product.setTotal(product.getPrice(), product.getDisc());
        productRepo.put(id, product);
        return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
      }
   }
    
   //delete request
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        //condition if the product isn't exists
        if(!productRepo.containsKey(id)){
          return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
      }
      else {
        productRepo.remove(id);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
      }
    }
    
}
