package recipes.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.controller.model.RecipeModel;
import recipes.data.Recipe;
import recipes.repositories.RecipeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;


    public Recipe addRecipe(RecipeModel recipe, String email) {
        Recipe recipeCreated = new Recipe(
                recipe.getName(), recipe.getCategory(),
                LocalDateTime.now(),
                recipe.getDescription(), recipe.getIngredients(),
                recipe.getDirections());
        recipeCreated.setEmail(email);
        return recipeRepository.save(recipeCreated);
    }

    public Recipe getRecipeById(long id) {

        return recipeRepository.findById(id)
                .orElseThrow(
                        () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Recipe with id=" + id + " was not found!"));
    }

    public boolean deleteRecipe(long id, String email) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if(optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            if(!recipe.getEmail().equals(email)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public boolean updateRecipeById(long id, RecipeModel model, String email) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if(recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            if(!recipe.getEmail().equals(email)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }

            recipe.setName(model.getName());
            recipe.setCategory(model.getCategory());
            recipe.setDate(LocalDateTime.now());
            recipe.setDescription(model.getDescription());
            recipe.setIngredients(model.getIngredients());
            recipe.setDirections(model.getDirections());

            recipeRepository.save(recipe);
            return true;
        }
        return false;
    }

    public List<Recipe> searchByCategory(String category) {
        return new ArrayList<>(recipeRepository
                .findByCategoryIgnoreCaseOrderByDateDesc(category));

    }

    public List<Recipe> searchByName(String name) {
        return new ArrayList<>(recipeRepository
                .findAllByNameContainingIgnoreCaseOrderByDateDesc(name));

    }
}
