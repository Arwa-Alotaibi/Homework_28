package com.example.homework_28.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    //id - name - price (Add All Required Validation)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @NotNull
    private String name;

    @NotNull
    private double price;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private List<Orders> orderList;

}
