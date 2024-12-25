package com.brognara.recipe_search_service.repository;

import com.brognara.recipe_search_service.model.RecipeDocument;

import java.util.concurrent.CompletableFuture;

public interface RecipeRepository {
    CompletableFuture<String> publishRecipe(RecipeDocument recipeDocument);
}
