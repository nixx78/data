package lv.nixx.poc.service;

import org.springframework.stereotype.Service;

@Service
public class UserLoginProvider {

    public String getCurrentUser() {
        return "user.name";
    }
}
