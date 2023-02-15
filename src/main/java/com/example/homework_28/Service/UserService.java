package com.example.homework_28.Service;

import com.example.homework_28.Exception.ApiException;
import com.example.homework_28.Model.User;
import com.example.homework_28.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public User GetUser(Integer user_id){
        User user = userRepository.findUserById(user_id);
        if(user==null){
            throw new ApiException("user id not found");
        }
        return user;
    }

    public void AddUser(User user){
        String hashedPassword=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
    public void UpdateUser(Integer user_id,User user){
        User old_user= userRepository.findUserById(user_id);
//        if(old_user==null){
//            throw new ApiException("user id not found");
//        }
        old_user.setUsername(user.getUsername());
        //id - username - password - role(CUSTOMER-ADMIN) (Add All Required Validation)
       // old_user.setPassword(user.getPassword());
        user.setPassword(new BCryptPasswordEncoder().encode(old_user.getPassword()));
        old_user.setRole(user.getRole());
        userRepository.save(old_user);
    }

    public void DeleteUser(Integer user_id){
        User delete_user = userRepository.findUserById(user_id);
        if(delete_user==null){
            throw new ApiException("User id not found");
        }
        userRepository.delete(delete_user);
    }

    public User GetUserById(Integer user_id){
        User user = userRepository.findUserById(user_id);
        if(user==null){
            throw new ApiException("user id not found");
        } else if (user.getId()!=user_id) {
            throw new ApiException("You do not have the authority to search");
        }
        return user;
    }
}
