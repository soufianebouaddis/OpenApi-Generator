package com.openapi.plugin.controller;



import com.openapi.api.UsersApi;
import com.openapi.model.User;
import com.openapi.model.UserInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OpenApiController implements UsersApi {
    private ArrayList<User> users = new ArrayList<>();

    @Override
    public ResponseEntity<User> createUser(UserInput userInput) {
        User u = new User();
        u.setId(users.size()+1);
        u.setName(userInput.getName());
        u.setAge(userInput.getAge());
        u.setEmail(userInput.getEmail());
        users.add(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Integer userId) {
        users.removeIf(p->p.getId().equals(userId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Override
    public ResponseEntity<User> getUserById(Integer userId) {
        return users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst().map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<User> updateUser(Integer userId, UserInput userInput) {
        for(User u : users){
            if(u.getId().equals(userId)){
                u.setName(userInput.getName());
                u.setEmail(userInput.getEmail());
                u.setAge(userInput.getAge());
                return ResponseEntity.status(HttpStatus.OK).body(u);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new User());
    }

}
