package com.brognara.recipe_search_service;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.brognara.recipe_search_service.model.RecipeDocument;

import java.util.List;

public class SearchResponseUtils {

    private SearchResponseUtils() {}

    public static List<RecipeDocument> mapSearchResponseToRecipeDocs(
            final SearchResponse<RecipeDocument> searchResponse
    ) {
        return searchResponse
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .toList();
    }
}
