package com.brognara.recipe_search_service.service;

import com.brognara.recipe_search_service.model.RecipeDocument;
import com.brognara.recipe_search_service.model.RecipePublishRequest;
import com.brognara.recipe_search_service.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                .nutritionalContents(
                        getOrEmptyList(recipePublishRequest.getNutritionalContents())
                )
                .totalReviews(0)
                .reviewScore(0)
                .build();
    }

    private <T> List<T> getOrEmptyList(List<T> list) {
        return Optional.ofNullable(list)
                .orElse(Collections.emptyList());
    }

    // Search by cuisine
//    public List<RecipeDocument> searchBySomething(String something) {
//        return recipeRepository.findByCuisine(cuisine, PageRequest.of(0, 10)).getContent();
//    }

    // More complex search
//    public List<RecipeDocument> searchByName(String name) {
//        var searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchQuery("name", name))
//                .build();
//
//        SearchHits<RecipeDocument> searchHits =
//                elasticsearchOperations.search(searchQuery, RecipeDocument.class);
//
//        return searchHits.getSearchHits().stream()
//                .map(hit -> hit.getContent())
//                .toList();
//    }
}
