package com.shubham.controller;

import com.shubham.dao.UserRepository;
import com.shubham.daoImpl.UserDaoImpl;
import com.shubham.model.PointsBalance;
import com.shubham.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDaoImpl userDao;

    // adding payer transaction route
    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user) {
        userRepository.save(user);
        userDao.updateBalance();
        userDao.balance();
        return user;
    }

    // getting list of all user or transaction history of payer
    @GetMapping("/users")
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    // getting balance per payer with their points
    @GetMapping("/balance")
    public ResponseEntity balance(){
        HashMap<String, Integer> balance = userDao.balance();
        return new ResponseEntity(balance, HttpStatus.OK);
    }

    // deducting the points from payers account based on the oldest date
    @PostMapping(value = "/deduct")
    public ResponseEntity deductPoints(@RequestBody PointsBalance points){
        if(points.getPoints() > userDao.getBalancePoints()){
            return new ResponseEntity("Payer doesn't have enough points for the transaction", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(userDao.updatePoints(points.getPoints()), HttpStatus.OK);
    }

}
