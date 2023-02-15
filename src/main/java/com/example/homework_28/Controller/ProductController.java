package com.example.homework_28.Controller;

import com.example.homework_28.Dto.ApiResponse;
import com.example.homework_28.Model.Product;
import com.example.homework_28.Model.User;
import com.example.homework_28.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @PostMapping("/add")
    public ResponseEntity AddProduct(@Valid @RequestBody Product product, @AuthenticationPrincipal User admin_id ){
        productService.AddProduct(admin_id.getId(),product);
        return ResponseEntity.status(200).body("product added");
    }

    @GetMapping("/getproduct")
    public ResponseEntity GetProduct(){
        return ResponseEntity.status(200).body(productService.GetProducts());
    }

    @PutMapping("/update/{product_id}")
    public ResponseEntity UpdateProduct(@Valid @RequestBody Product product ,@AuthenticationPrincipal User admin_id, @PathVariable Integer product_id ){
        productService.UpdateProduct(product_id,admin_id.getId(),product);
        return ResponseEntity.status(200).body(new ApiResponse("product updated"));
    }
    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity DeleteProduct(@AuthenticationPrincipal User admin_id,@PathVariable Integer product_id ){
        productService.DeleteProducts(admin_id.getId(),product_id);
        return ResponseEntity.status(200).body(new ApiResponse("product deleted"));
    }

    @GetMapping("/get/product/{product_id}")
    public ResponseEntity GetProduct(@PathVariable Integer product_id ,@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(productService.GetProductByID(product_id,user.getId()));
    }
}
