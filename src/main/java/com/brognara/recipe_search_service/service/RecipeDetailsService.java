package com.brognara.recipe_search_service.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.brognara.recipe_search_service.SearchResponseUtils;
import com.brognara.recipe_search_service.model.RecipeDocument;
import com.brognara.recipe_search_service.model.ToggleFavoriteRecipeRequest;
import com.brognara.recipe_search_service.repository.InMemoryFavoriteRecipesRepository;
import com.brognara.recipe_search_service.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipeDetailsService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeDetailsService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // TODO cleanup - get recipe details by recipe name, but trying by recipe doc id right now
//    public Mono<RecipeDocument> getRecipeDetails(final String recipeName) {
//        return Mono.fromFuture(
//                recipeRepository.searchRecipes(
//                        new Query.Builder()
//                                .term(buildQuery(recipeName))
//                                .build()
//                        )
//                        .thenApply(response -> {
//                            log.info("EVENT=GET_RECIPE_DETAILS_RESPONSE ; DATA={}", response);
//                            List<RecipeDocument> returnedRecipeDocs =
//                                    SearchResponseUtils.mapSearchResponseToRecipeDocs(response);
//                            if (returnedRecipeDocs.isEmpty()) {
//                                throw new RuntimeException("Details not found for recipe " + recipeName);
//                            }
//                            return returnedRecipeDocs.getFirst();
//                        })
//        );
//    }

    private TermQuery buildQuery(final String recipeName) {
        // TODO add query - recipeName needs to be text and keyword so we can exact match
        return new TermQuery.Builder()
                .build();
    }

    public Mono<List<RecipeDocument>> getFavoriteRecipes(final String userId) {
        return Mono.just(
                Optional.ofNullable(
                        InMemoryFavoriteRecipesRepository.table.get(userId)
                ).orElse(
                        Collections.emptyList()
                )
        );
    }

    public Mono<List<RecipeDocument>> toggleFavoriteRecipe(String userId, ToggleFavoriteRecipeRequest toggleFavoriteRecipeRequest) {
        return Mono.fromSupplier(() -> {
            if (toggleFavoriteRecipeRequest.isAdd()) {

                if (userMissingInTable(userId)) {
                    initUserInTable(userId);
                }
                appendRecipeToFavoritesForUser(userId, toggleFavoriteRecipeRequest.getFavoriteRecipe());

            } else {

                if (userMissingInTable(userId)) {
                    // Fail quietly
                    log.error(
                            "Failed to delete favorite recipe {} for user {}: user missing from table",
                            toggleFavoriteRecipeRequest.getFavoriteRecipe().getName(),
                            userId
                    );
                    return Collections.emptyList();
                }
                removeRecipeFromFavoritesForUser(userId, toggleFavoriteRecipeRequest.getFavoriteRecipe());

            }
            // success
            return InMemoryFavoriteRecipesRepository.table.get(userId);
        });
    }

    private boolean userMissingInTable(final String userId) {
        return InMemoryFavoriteRecipesRepository.table.get(userId) == null;
    }

    private void appendRecipeToFavoritesForUser(final String userId, final RecipeDocument recipe) {
        InMemoryFavoriteRecipesRepository.table.get(userId).add(recipe);
    }

    private void removeRecipeFromFavoritesForUser(final String userId, final RecipeDocument recipe) {
        InMemoryFavoriteRecipesRepository.table.get(userId)
                .removeIf(r -> r.getName().equals(recipe.getName()));
    }

    private void initUserInTable(final String userId) {
        InMemoryFavoriteRecipesRepository.table.put(userId, new LinkedList<>());
    }
}
