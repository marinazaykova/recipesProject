package recipes.data;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @NotBlank
    @NonNull
    @Email(regexp = "(?i)[\\w!#$%&'*+/=?`{|}~^-]+" +
            "(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,6}")
    private String email;

    @NonNull
    @NotBlank
    @Size(min = 8)
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
