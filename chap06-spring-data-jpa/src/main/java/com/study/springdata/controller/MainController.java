package com.study.springdata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/test")
    public ResponseEntity<String> test() { //응답 객체에 값을 담는다.
//        return ResponseEntity.ok("hello"); //localhost:8000/test
        return ResponseEntity.status(200).body("안녕");
    }
}
