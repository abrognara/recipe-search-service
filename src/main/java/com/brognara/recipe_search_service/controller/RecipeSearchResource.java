package com.brognara.recipe_search_service.controller;

import com.brognara.recipe_search_service.model.*;
import com.brognara.recipe_search_service.service.RecipeSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/recipe/v1/")
public class RecipeSearchResource {

    private final RecipeSearchService recipeSearchService;
    private final RecipeDetailsService recipeDetailsService;

    @Autowired
    public RecipeSearchResource(RecipeSearchService recipeSearchService, RecipeDetailsService recipeDetailsService) {
        this.recipeSearchService = recipeSearchService;
        this.recipeDetailsService = recipeDetailsService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/search")
    public Mono<ResponseEntity<List<RecipeDocument>>> searchRecipe(
            @RequestBody final RecipeSearchRequest recipeSearchRequest
    ) {
        log.info("EVENT=SEARCH_RECIPE ; DATA={}", recipeSearchRequest);
        return recipeSearchService.searchRecipes(recipeSearchRequest)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/publish")
    public Mono<ResponseEntity<String>> publishRecipe(@RequestBody final RecipePublishRequest recipePublishRequest) {
        log.info("EVENT=PUBLISH_RECIPE ; DATA={}", recipePublishRequest);
        return recipeSearchService.publishRecipe(recipePublishRequest)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/details/{recipeName}")
    public Mono<ResponseEntity<RecipeDocument>> getRecipeDetails(@PathVariable final String recipeName) {
        log.info("EVENT=GET_RECIPE_DETAILS ; RECIPE_NAME={}", recipeName);
        return recipeDetailsService.getRecipeDetails(recipeName)
                .map(result -> ResponseEntity.status(HttpStatus.OK).body(result));
    }

}
