package com.example.homework_28.Controller;


import com.example.homework_28.Dto.ApiResponse;
import com.example.homework_28.Model.Orders;
import com.example.homework_28.Model.User;
import com.example.homework_28.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }

    @PostMapping("/add/{product_id}")
    public ResponseEntity AddOrder(@PathVariable Integer product_id, @Valid @RequestBody Orders order, @AuthenticationPrincipal User user_id){
        orderService.AddOrder(user_id.getId(),product_id,order);
        return ResponseEntity.status(200).body(new ApiResponse("order added!"));
    }
    //Get all customer orders
    @GetMapping("/get/orders")
    public ResponseEntity Getorders(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(orderService.GetUserOrders(user.getId()));
    }

    @DeleteMapping("/delete/{order_id}")
    public ResponseEntity DeleteOrder(@PathVariable Integer order_id,@AuthenticationPrincipal User user_id){
        orderService.DeleteOrder(order_id, user_id.getId());
        return ResponseEntity.status(200).body(new ApiResponse("order deleted!"));
    }

    @PutMapping("/Status/{order_id}")
    public ResponseEntity UpdateStatus(@Valid @RequestBody Orders order ,@PathVariable Integer order_id , @AuthenticationPrincipal User admin_id){
        orderService.UpdateStatus(admin_id.getId(),order_id,order);
        return ResponseEntity.status(200).body(new ApiResponse("Status updated!"));
    }
    @PutMapping("/update/{order_id}")
    public ResponseEntity UpdateOrder(@Valid @RequestBody Orders order, @AuthenticationPrincipal User user_id,@PathVariable Integer order_id ){
        orderService.update_order(order_id,user_id.getId(),order);
        return ResponseEntity.status(200).body(new ApiResponse("order updated!"));
    }

    @GetMapping("/get/{order_id}")
    public ResponseEntity GetBYID(@PathVariable Integer order_id , @AuthenticationPrincipal User user_id){
        return ResponseEntity.status(200).body(orderService.GetOrderById(order_id,user_id.getId()));
    }
}
