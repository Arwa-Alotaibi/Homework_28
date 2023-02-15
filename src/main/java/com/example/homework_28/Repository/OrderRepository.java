package com.example.homework_28.Repository;

import com.example.homework_28.Model.Orders;
import com.example.homework_28.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    Orders findOrderById(Integer id);

    Orders findOrderByStatus(String status);
    List <Orders> findAllByUser(User user);
}
