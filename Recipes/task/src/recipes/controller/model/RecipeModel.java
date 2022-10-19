package recipes.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeModel {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Category is mandatory")
    private String category;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotEmpty
    @NotNull(message = "Ingredients cannot be null")
    @Size(min = 1, message = "At least one ingredient is required")
    private List<String> ingredients;
    @NotEmpty
    @NotNull
    @Size(min = 1, message = "At least one direction is required")
    private List<String> directions;
}
