package farrel.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import farrel.auth.model.User;
import farrel.auth.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public User addMoney(String username, long newMoney) {
        User user = repository.findByUsername(username).orElse(null);
        if (user != null) {
            user.setMoney(user.getMoney()+newMoney);
            repository.save(user);
        }

        return user;
    }

    public User subsMoney(String username, long newMoney) {
        User user = repository.findByUsername(username).orElse(null);
        if (user != null) {
            user.setMoney(user.getMoney()-newMoney);
            repository.save(user);
        }

        return user;
    }
}
