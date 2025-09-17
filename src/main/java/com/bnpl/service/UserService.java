package com.bnpl.service;

import com.bnpl.dto.Response;
import com.bnpl.dto.UserDto;

public interface UserService {

    Response createUser(UserDto userDto);

    Response getUserById(Long id);

    Response getUserByEmail(String email);

    Response getAllUsers();

    Response deleteUser(Long id);

    Response updateUser(Long id, UserDto dto);

}
