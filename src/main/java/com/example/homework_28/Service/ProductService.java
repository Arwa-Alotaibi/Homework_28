package com.example.homework_28.Service;

import com.example.homework_28.Exception.ApiException;
import com.example.homework_28.Model.Orders;
import com.example.homework_28.Model.Product;
import com.example.homework_28.Model.User;
import com.example.homework_28.Repository.OrderRepository;
import com.example.homework_28.Repository.ProductRepository;
import com.example.homework_28.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    private UserRepository userRepository;

    public ProductService(OrderRepository orderRepository,ProductRepository productRepository,UserRepository userRepository){
        this.orderRepository=orderRepository;
        this.productRepository=productRepository;
        this.userRepository=userRepository;
    }
    public List<Product> GetProducts(){
        return productRepository.findAll();
    }
    public void AddProduct(Integer admin_id,Product product){
        User user = userRepository.findUserById(admin_id);
        if(user.getRole().equals("CUSTOMER")){
            throw  new ApiException("You do not have the authority to add product");
        }
        productRepository.save(product);
    }

    public void UpdateProduct(Integer product_id,Integer admin_id,Product product){
        Product update_product= productRepository.findProductById(product_id);
        User user= userRepository.findUserById(admin_id);
        if(update_product==null){
            throw new ApiException("product id not found");
        } else if (user.getRole().equals("CUSTOMER")) {
            throw  new ApiException("You do not have the authority to update product");
        }
        //    //id - name - price (Add All Required Validation)
        update_product.setName(product.getName());
        update_product.setPrice(product.getPrice());
        productRepository.save(update_product);
    }
    public void DeleteProducts(Integer admin_id,Integer product_id){
        Product delete_product= productRepository.findProductById(product_id);
        User user= userRepository.findUserById(admin_id);
        if(delete_product==null){
            throw new ApiException("product id not found!");
        }
        if(user.getRole().equals("CUSTOMER")){
            throw  new ApiException("You do not have the authority to delete product");
        }
        productRepository.delete(delete_product);
    }


    public Product GetProductByID(Integer order_id,Integer user_id){
        Product product = productRepository.findProductById(order_id);
        User user = userRepository.findUserById(user_id);
        if(product==null){
            throw new ApiException("product id not found");
        }
        return product;
    }

}
