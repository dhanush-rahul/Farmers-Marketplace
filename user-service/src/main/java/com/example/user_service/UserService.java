/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.user_service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author dhanu
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(Users user){
        userRepository.save(user);
    }

    public List<Users> listAllUsers(){
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public void updateFarmerStatus(Integer userId, UserRoleEnum status){
        Users user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found!"));
        if(user.getRole() == UserRoleEnum.FARMER){
            user.setRole(status);
            userRepository.save(user);
        }
        else{
            throw new IllegalStateException("User is not a farmer!");
        }
    }


}
