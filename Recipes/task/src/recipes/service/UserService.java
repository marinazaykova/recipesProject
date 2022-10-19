package recipes.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.data.User;
import recipes.repositories.UsersRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    public UserService(UsersRepository usersRepository, PasswordEncoder encoder) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
    }

    public boolean registerUser(User user){
        Optional<User> userToRegister = usersRepository.findById(user.getEmail());
        if(userToRegister.isPresent()) {
            return false;
        }
        String encodedPass = encoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        usersRepository.save(user);
        return true;

    }

}
