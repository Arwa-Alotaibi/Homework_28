package com.example.homework_28.Service;

import com.example.homework_28.Exception.ApiException;
import com.example.homework_28.Model.Orders;
import com.example.homework_28.Model.Product;
import com.example.homework_28.Model.User;
import com.example.homework_28.Repository.OrderRepository;
import com.example.homework_28.Repository.ProductRepository;
import com.example.homework_28.Repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    private UserRepository userRepository;

    private ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,UserRepository userRepository,ProductRepository productRepository){
        this.orderRepository=orderRepository;
        this.userRepository=userRepository;
        this.productRepository=productRepository;
    }

    //In Add order endpoint: calculate the total price , status by defalut new
    public void AddOrder(Integer user_id,Integer product_id,  Orders order){
        User user = userRepository.findUserById(user_id);
        Product product = productRepository.findProductById(product_id);
        if(product==null){
            throw new ApiException("product id not found!");
        }
        else if(product!=null){
            if(order.getTotalPrice()<product.getPrice()) {
                throw new ApiException("the product price is greater than your orders");
            }
                else if (order.getStatus().equals("new")) {
                    double All_total = order.getTotalPrice()-product.getPrice();
                    order.setTotalPrice(All_total);
            }
        }
        order.setProduct(product);
        order.setUser(user);
        orderRepository.save(order);
    }
    public List GetUserOrders(Integer user_id ){
        User user = userRepository.findUserById(user_id);
        List <Orders> All_Order = orderRepository.findAllByUser(user);
        if(All_Order.isEmpty()){
            throw new ApiException("you don't have orders!");
        }
        return All_Order;
    }

    // check order status if its in progress or complete throw an exception
    public void DeleteOrder(Integer order_id,Integer user_id){
        Orders delete_order =orderRepository.findOrderById(order_id);
         if(delete_order==null){
            throw new ApiException("order id not found");
        }
         else if (delete_order.getUser().getId()!=user_id) {
             throw new ApiException("You do not have the authority to delete !");
         }
        else if(delete_order.getStatus().equals("inProgress")){
            throw new ApiException("your cant delete your order because it's inProgress ");
        } else if (delete_order.getStatus().equals("completed")) {
            throw new ApiException("your cant delete your order because it's completed");
        }
        orderRepository.delete(delete_order);
    }

    //change order status(only admin can change it)
    public void UpdateStatus(Integer admin_id,Integer order_id,Orders order){
        User user = userRepository.findUserById(admin_id);
        Orders update= orderRepository.findOrderById(order_id);
         if (update==null) {
            throw new ApiException("order id not found");
        }
       else if(user.getRole().equals("CUSTOMER")) {
            throw new ApiException("You do not have the authority to update status");
        }
        update.setStatus(order.getStatus());
        orderRepository.delete(update);
    }


    public void update_order(Integer order_id,Integer user_id,Orders order){
        User user = userRepository.findUserById(user_id);
        Orders update_order=orderRepository.findOrderById(order_id);
          if(update_order==null){
            throw new ApiException("order id not found");
        }
        else if(update_order.getUser().getId()!=user_id){
            throw new ApiException("You do not have the authority to update ");
        }

//        update_order.setId(order_id);
        update_order.setQuantity(order.getQuantity());
        update_order.setTotalPrice(order.getTotalPrice());
        update_order.setDateReceived(order.getDateReceived());
        update_order.setStatus(update_order.getStatus());
        orderRepository.save(update_order);
    }


    //Get order
    public Orders GetOrderById(Integer order_id,Integer user_id){
        Orders orders= orderRepository.findOrderById(order_id);
        User user = userRepository.findUserById(user_id);
        if(orders==null){
            throw new ApiException("order id not found");
        }
        else if(orders.getUser().getId()!=user_id) {
            throw new ApiException("You do not have the authority to search ");
        }
        return orders;
    }

}
