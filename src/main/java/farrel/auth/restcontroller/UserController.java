package farrel.auth.restcontroller;

import farrel.auth.model.AuthResponse;
import farrel.auth.model.User;
import farrel.auth.service.UserService;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/addUserMoney/{username}/{money}")
    public ResponseEntity<User> addUserMoney(@PathVariable("username") String username, @PathVariable("money") long money) {
        System.out.println(username);
        System.out.println(money);
        User updatedUser = userService.addMoney(username, money);

        if (updatedUser==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + username + " not found");
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/subsUserMoney/{username}/{money}")
    public ResponseEntity<User> subsUserMoney(@PathVariable("username") String username, @PathVariable("money") long money) {
        System.out.println(username);
        System.out.println(money);
        User updatedUser = userService.subsMoney(username, money);

        if (updatedUser==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + username + " not found");
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
}