package com.brognara.recipe_search_service.service;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.brognara.recipe_search_service.model.RecipeDocument;
import com.brognara.recipe_search_service.model.RecipePublishRequest;
import com.brognara.recipe_search_service.model.RecipeSearchRequest;
import com.brognara.recipe_search_service.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
@Service
public class RecipeSearchService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeSearchService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Mono<String> publishRecipe(final RecipePublishRequest recipePublishRequest) {
        return Mono.fromFuture(
                recipeRepository.publishRecipe(
                        convertRequestToDoc(recipePublishRequest)
                )
        );
    }

    private RecipeDocument convertRequestToDoc(final RecipePublishRequest recipePublishRequest) {
        return RecipeDocument.builder()
                .dishName(recipePublishRequest.getDishName())
                .name(recipePublishRequest.getRecipeName())
                .cuisine(recipePublishRequest.getCuisine())
                .course(recipePublishRequest.getCourse())
                .dishType(recipePublishRequest.getDishType())
                .ingredients(recipePublishRequest.getIngredients())
                .instructions(recipePublishRequest.getInstructions())
                .preparationMethod(recipePublishRequest.getPreparationMethod())
                .cookingTime(recipePublishRequest.getCookingTime())
                .servings(recipePublishRequest.getServings())
                .author(recipePublishRequest.getAuthor())
                .description(recipePublishRequest.getDescription())
                .difficulty(recipePublishRequest.getDifficulty())
                .createdTimestamp(recipePublishRequest.getTimestamp())
                .thumbnailUri(recipePublishRequest.getThumbnailLocation())
                .dietTags(
                        getOrEmptyList(recipePublishRequest.getDietTags())
                )
                .dietRestrictionTags(
                        getOrEmptyList(recipePublishRequest.getDietRestrictionTags())
                )
                .otherTags(
                        getOrEmptyList(recipePublishRequest.getOtherTags())
                )
//                .nutritionalContents(
//                        getOrEmptyList(recipePublishRequest.getNutritionalContents())
//                )
                .totalReviews(0)
                .reviewScore(0)
                .build();
    }

    private <T> List<T> getOrEmptyList(List<T> list) {
        return Optional.ofNullable(list)
                .orElse(Collections.emptyList());
    }

    public Mono<List<RecipeDocument>> searchRecipes(final RecipeSearchRequest recipeSearchRequest) {
        return Mono.fromFuture(
                recipeRepository.searchRecipes(
                                new Query.Builder()
                                        .bool(buildQuery(recipeSearchRequest))
                                        .build()
                        )
                        .thenApply(
                                searchResponse -> {
                                    log.info("EVENT=SEARCH_RESPONSE ; DATA={}", searchResponse);
                                    return searchResponse
                                            .hits()
                                            .hits()
                                            .stream()
                                            .map(Hit::source)
                                            .toList();
                                }
                        )
        );
    }

    private BoolQuery buildQuery(final RecipeSearchRequest searchRequest) {
        final String recipeNameFieldName = "name";

        final BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder()
                .must(
                        new Query.Builder()
                                .match(
                                        new MatchQuery.Builder()
                                        .field(recipeNameFieldName)
                                        .query(searchRequest.getRecipeName())
                                        .operator(Operator.And)
                                        .build()
                                )
                                .build()
                );

        // add filters if present in search request
        List<Query> filters = new LinkedList<>();
        if (StringUtils.hasText(searchRequest.getCourse())) {
            filters.add(
              new Query.Builder()
                      .term(
                              new TermQuery.Builder()
                                      .field("course")
                                      .value(searchRequest.getCourse())
                                      .build()
                      )
                      .build()
            );
        }
        if (StringUtils.hasText(searchRequest.getCuisine())) {
            filters.add(
                    new Query.Builder()
                            .term(
                                    new TermQuery.Builder()
                                            .field("cuisine")
                                            .value(searchRequest.getCuisine())
                                            .build()
                            )
                            .build()
            );
        }
        if (StringUtils.hasText(searchRequest.getDishType())) {
            filters.add(
                    new Query.Builder()
                            .term(
                                    new TermQuery.Builder()
                                            .field("dish_type")
                                            .value(searchRequest.getDishType())
                                            .build()
                            )
                            .build()
            );
        }

        if (!filters.isEmpty()) {
            boolQueryBuilder.filter(filters);
        }

        return boolQueryBuilder.build();
    }
}
