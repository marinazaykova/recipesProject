package recipes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.controller.model.RecipeModel;
import recipes.data.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@Validated
public class RecipeController {

    private final RecipeService recipeService;
    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipe/new")
    public Map<String, Long> addRecipe(@Valid @RequestBody RecipeModel recipe) {
        Recipe savedRecipe = recipeService.addRecipe(recipe, getLoggedInUser());
        return Map.of("id", savedRecipe.getId());
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id) {

        return recipeService.getRecipeById(id);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<String> deleteRecipe(@Valid @PathVariable long id) {

        if(recipeService.deleteRecipe(id, getLoggedInUser())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/recipe/{id}")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> updateRecipe(@Valid @RequestBody RecipeModel model,
                             @Valid @PathVariable long id) {

        if(recipeService.updateRecipeById(id, model, getLoggedInUser())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recipe/search")
    public List<Recipe> searchRecipe(@Valid @RequestParam(value = "category", required = false) String category,
                                     @Valid @RequestParam(value = "name", required = false) String name) {

        if((category == null && name == null) || (category != null && name != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else if(category != null) {
            return recipeService.searchByCategory(category);
        }else if(name != null) {
            return recipeService.searchByName(name);
        }
        return new ArrayList<>();
    }

    public String getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().toString();
    }

}
