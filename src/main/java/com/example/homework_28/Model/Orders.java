package com.example.homework_28.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    //  id - quantity - totalPrice - dateReceived - status(new-inProgress-completed) (Add All Required Validation)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    //@NotNull
    private int quantity;
    //@NotNull
    private double totalPrice;

    //@NotNull
    private String dateReceived ;
    @Pattern(regexp = "(new|inProgress|completed)",message = "status must be in new or inProgress or completed")
    @NotNull(message = "the status should be not null")
    private String status;


    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    @JsonIgnore
    private User user;


    @ManyToOne
    @JoinColumn(name = "product_id" , referencedColumnName = "id")
    @JsonIgnore
    private Product product;







}
