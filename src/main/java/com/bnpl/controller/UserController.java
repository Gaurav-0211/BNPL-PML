package com.bnpl.controller;

import com.bnpl.dto.Response;
import com.bnpl.dto.UserDto;
import com.bnpl.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Post request to create a new user
    @PostMapping("/create")
    public ResponseEntity<Response> createUser(@RequestBody @Valid UserDto userDto) {
        Response response = this.userService.createUser(userDto);
        return ResponseEntity.ok(response);
    }

    // Get request to find user by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        Response response =  this.userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    // Get request to fetch all users
    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllUsers() {
        Response response = this.userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    // Get request to get user by email ID
    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<Response> getUserByEmail(@PathVariable @Valid String email) {
        Response response = this.userService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }
    // Put request to update user
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @Valid @RequestBody UserDto dto){
        Response response = this.userService.updateUser(id, dto);
        return ResponseEntity.ok(response);
    }

    // Delete request to delete user account
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id){
        Response response = this.userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }

}
