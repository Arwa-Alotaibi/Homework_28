package com.example.homework_28.Controller;

import com.example.homework_28.Dto.ApiResponse;
import com.example.homework_28.Model.User;
import com.example.homework_28.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/getuser")
    public ResponseEntity GetUsers(@AuthenticationPrincipal User user_id){
        return ResponseEntity.status(200).body(userService.GetUser(user_id.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity AddUser(@AuthenticationPrincipal User user_id, @Valid @RequestBody User user){
        userService.AddUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("register successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity UpdateUser(@Valid @RequestBody User user,@AuthenticationPrincipal User user_id){
        userService.UpdateUser(user_id.getId(), user);
        return ResponseEntity.status(200).body(new ApiResponse("user updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity DeleteUser(@PathVariable Integer id){
        userService.DeleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("user deleted!"));
    }

    @GetMapping("/get/users/{user_id}")
    public ResponseEntity GetUserbyid( @AuthenticationPrincipal @PathVariable Integer user_id){
        return ResponseEntity.status(200).body(userService.GetUserById(user_id));
    }
}
