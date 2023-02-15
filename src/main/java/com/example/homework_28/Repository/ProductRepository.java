package com.example.homework_28.Repository;

import com.example.homework_28.Model.Orders;
import com.example.homework_28.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAllByOrderList(Orders order);

    Product findProductById(Integer id);

}
