package main.java.ebay.controllers;

import java.util.concurrent.atomic.AtomicLong;

import main.java.ebay.data.Employee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    
    @RequestMapping(method = RequestMethod.GET, value = "/employee")
    public Employee greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new Employee("Jvalant","mailjvalant@gmail.com");
    }
    
}