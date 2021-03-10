package com.example.demo.controller;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Authenticated;
import com.example.demo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository repository;
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public Iterable<User> findAllUsers(){
        return this.repository.findAll();
    }

    @DeleteMapping("/users/{id}")
    public HashMap<String, Object> deleteId(@PathVariable Long id){
        this.repository.deleteById(id);
        HashMap<String, Object> newHash = new HashMap<>();
        Long count = this.repository.count();
        newHash.put("count",count);
        return newHash;
    }

    @GetMapping("/users/{id}")
    public Optional<User> findId(@PathVariable Long id){
        return this.repository.findById(id);
    }

    @PostMapping("/users")
    public User userUpdate(@RequestBody User newUser){
        return this.repository.save(newUser);
    }

    @PatchMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id){
        if(this.repository.existsById(id)){
            User oldId = this.repository.findById(id).get();
            oldId.setEmail(user.getEmail());
            oldId.setPassword(user.getPassword());
            return this.repository.save(oldId);
        }
        else{
            return this.repository.save(user);
        }
    }

    @PostMapping("/users/authenticate")
    public User isAuthenticated(@RequestBody User user){
        User myUser = this.repository.findByEmail(user.getEmail());
        if(myUser.getPassword().equals(user.getPassword())){
            Authenticated myAuth = new Authenticated(true, myUser);
        } else this.isAuthenticated(myUser);
        return myUser;
    }



}
