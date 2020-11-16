package com.kalwador.sslpining;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SecuredController {

    @GetMapping("/secured")
    public String secured() {
        System.out.println("Inside secured()");
        return "Hello user !!! : " + new Date();
    }
}
