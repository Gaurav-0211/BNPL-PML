package com.bnpl.service.impl;

import com.bnpl.dto.Response;
import com.bnpl.dto.UserDto;
import com.bnpl.exception.NoDataExist;
import com.bnpl.model.User;
import com.bnpl.repository.UserRepository;
import com.bnpl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Response response;

    @Autowired
    private ModelMapper mapper;

    // Create a new USER API
    public Response createUser(UserDto userDto) {
        log.info("create user triggered in user service Impl");
        User user = this.mapper.map(userDto, User.class);
        user.setRole(User.Role.CUSTOMER);

        User saved = this.userRepository.save(user);

        response.setStatus("SUCCESS");
        response.setMessage("User Created successfully");
        response.setData(this.mapper.map(saved, UserDto.class));
        response.setStatusCode(201);
        response.setResponse_message("Process execution success");

        log.info("create user executed in user service Impl");

        return response;
    }

    // Get User by ID API
    public Response getUserById(Long id) {
        log.info("Get user by ID service Impl triggered");
        User user =  this.userRepository.findById(id)
                .orElseThrow(()-> new NoDataExist("User not found with given ID"));

        response.setStatus("SUCCESS");
        response.setMessage("User fetched successfully");
        response.setData(this.mapper.map(user, UserDto.class));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");
        log.info("Get user by ID service Impl executed");

        return response;
    }

    // Get user by Email ID API
    public Response getUserByEmail(String email) {
        log.info("Get by email user service Impl start");
        User user =  this.userRepository.findByEmail(email)
                .orElseThrow(()-> new NoDataExist("No user exist with given email"));

        response.setStatus("SUCCESS");
        response.setMessage("User fetched successfully");
        response.setData(this.mapper.map(user, UserDto.class));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");
        log.info("Get by email user service Impl end");

        return response;
    }

    // Get All User API
    public Response getAllUsers() {
        log.info("get all user service Impl start");
        List<User> users =  this.userRepository.findAll();

        response.setStatus("SUCCESS");
        response.setMessage("All User fetched successfully");
        response.setData(users.stream().map((user)->this.mapper.map(user, UserDto.class)).collect(Collectors.toList()));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");
        log.info("get all user service Impl end");

        return response;
    }

    // Delete user by ID API
    @Override
    public Response deleteUser(Long id) {
        log.info("delete user by ID service Impl start");
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new NoDataExist("No User found with given ID"));
        this.userRepository.delete(user);

        response.setStatus("SUCCESS");
        response.setMessage("User deleted successfully");
        response.setData(null);
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");
        log.info("delete user by ID service Impl end");

        return response;
    }


    // Update user API
    @Override
    public Response updateUser(Long id, UserDto dto) {
        log.info("Update user service impl start");
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new NoDataExist("No user fetched with given ID"));
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());

        User updated = this.userRepository.save(user);

        response.setStatus("SUCCESS");
        response.setMessage("User updated successfully");
        response.setData(this.mapper.map(updated, UserDto.class));
        response.setStatusCode(200);
        response.setResponse_message("Process execution success");
        log.info("Update user service impl end");

        return response;
    }
}
