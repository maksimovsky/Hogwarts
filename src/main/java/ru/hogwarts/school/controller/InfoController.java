package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${server.port}")
    private int port;

    Logger logger = LoggerFactory.getLogger(InfoController.class);

    @GetMapping("/getPort")
    public ResponseEntity<Integer> getPort() {
        logger.debug("Getting port number");
        return ResponseEntity.ok(port);
    }
}
