package recipes.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.data.User;
import recipes.security.UserDetailsImp;
import recipes.repositories.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersRepository.findById(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + email + " was not found!"));
        return new UserDetailsImp(user);
    }


}
