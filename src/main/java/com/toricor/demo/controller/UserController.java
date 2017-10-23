package com.toricor.demo.controller;

import com.toricor.demo.domain.User;
import com.toricor.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    UserService userService;

    // 顧客全件取得
    @RequestMapping(method = RequestMethod.GET)
    List<User> getCustomers() {
        List<User> users = userService.findAll();
        return users;
    }

    // 顧客1件取得
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    User getCustomer(@PathVariable Integer id) {
        User user = userService.findOne(id);
        return user;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    User postCustomers(@RequestBody User user) {
        return userService.create(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    User putCustomer(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        return userService.update(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomer(@PathVariable Integer id) {
        userService.delete(id);
    }
}
