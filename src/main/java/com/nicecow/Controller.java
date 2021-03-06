package com.nicecow;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final UserRepository userRepository;
    Controller(UserRepository userRepository) { this.userRepository = userRepository; }

    @PostMapping("api/login")
    private HttpTrace.Response login(@RequestHeader()) {
        return ;
    }


}
