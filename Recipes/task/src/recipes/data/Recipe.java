package recipes.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Component
@Entity
@Table(name = "RECIPES")
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @NotBlank(message = "Name is mandatory")
    @NonNull
    private String name;

    @NotBlank(message = "Category is mandatory")
    @NonNull
    private String category;

    //validate min 8 characters
    @NonNull
    private LocalDateTime date;

    @NotBlank(message = "Description is mandatory")
    @NonNull
    private String description;


    @NotEmpty
    @NotNull(message = "Ingredients cannot be null")
    @NonNull
    @Size(min = 1, message = "At least one ingredient is required")
    @ElementCollection
    private List<String> ingredients;

    @NotEmpty
    @NotNull
    @NonNull
    @Size(min = 1, message = "At least one direction is required")
    @ElementCollection
    private List<String> directions;

    @JsonIgnore
    private String email;


    public Recipe(String name, String category, LocalDateTime date, String description, List<String> ingredients, List<String> directions) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }
}
