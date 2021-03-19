package com.nicecow.app;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {

    private final UserRepository userRepository;
    Controller(UserRepository userRepository) { this.userRepository = userRepository; }

    @RequestMapping("/api/home")
    private String home() {
        return "index";
    }

    @PostMapping("/api/create-account")
    private User createAccount(@RequestBody CreatingUser creatingUser) {
        if (creatingUser.getPassword().equals(creatingUser.getEnterPasswordAgain())) {
            return userRepository.save( new User(creatingUser.getEmail(), creatingUser.getPassword()));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("problem", "passwords do not match");
        jsonObject.put("fix", "provide matching passwords");
        throw new RequestRequirementUnmetException(jsonObject);
    }

//    @RequestMapping("/api/login")
//    private Integer login(@RequestBody JSONObject jsonObject) {
//        if (userRepository.findByEmail(jsonObject.getAsString("email")).getPassword().equals(jsonObject.getAsString("password"))) {
//            return 200;
//        }
//        return 401;
//    }

//    @RequestMapping("api/login")
//    private ResponseEntity login(@RequestBody String token, @RequestBody String username, String password) {
//        // 0) logic to verify token or password/email
//        // 1) logic to limit number of attempts in given period of time
//        // 2) how does user data fit in with caching? logic to exclude token email password from caching stuff, if we're doing that?
//        // 3) add no cross site scripting to user data? or at all?
//        // logic to
//        ResponseEntity responseEntity;
//        responseEntity.
//        return ;
//    }


}
