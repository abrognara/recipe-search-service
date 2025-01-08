package com.brognara.recipe_search_service.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.brognara.recipe_search_service.model.RecipeDocument;

import java.util.concurrent.CompletableFuture;

public interface RecipeRepository {
    CompletableFuture<String> publishRecipe(RecipeDocument recipeDocument);

    CompletableFuture<SearchResponse<RecipeDocument>> searchRecipes(Query query);
}
