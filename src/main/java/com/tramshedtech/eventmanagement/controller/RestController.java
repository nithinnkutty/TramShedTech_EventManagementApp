package com.tramshedtech.eventmanagement.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/getUid")
    public ResponseEntity<Integer> getUid(HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        return ResponseEntity.ok(uid);
    }
}
