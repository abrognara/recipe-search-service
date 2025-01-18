package com.brognara.recipe_search_service.model;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.brognara.recipe_search_service.SearchResponseUtils;
import com.brognara.recipe_search_service.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class RecipeDetailsService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeDetailsService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Mono<RecipeDocument> getRecipeDetails(final String recipeName) {
        return Mono.fromFuture(
                recipeRepository.searchRecipes(
                        new Query.Builder()
                                .term(buildQuery(recipeName))
                                .build()
                        )
                        .thenApply(response -> {
                            log.info("EVENT=GET_RECIPE_DETAILS_RESPONSE ; DATA={}", response);
                            List<RecipeDocument> returnedRecipeDocs =
                                    SearchResponseUtils.mapSearchResponseToRecipeDocs(response);
                            if (returnedRecipeDocs.isEmpty()) {
                                throw new RuntimeException("Details not found for recipe " + recipeName);
                            }
                            return returnedRecipeDocs.getFirst();
                        })
        );
    }

    private TermQuery buildQuery(final String recipeName) {
        // TODO add query - recipeName needs to be text and keyword so we can exact match
        return new TermQuery.Builder()
                .build();
    }

}
