package recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import recipes.data.User;

public interface UsersRepository extends CrudRepository<User, String> {
    User findByEmail(String email);
}

