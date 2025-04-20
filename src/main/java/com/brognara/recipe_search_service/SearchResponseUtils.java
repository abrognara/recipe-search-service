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
                .map(hit -> {
                    final RecipeDocument doc = hit.source();
                    if (doc == null) {
                        // TODO should this return null?
                        return null;
                    }
                    doc.setId(hit.id());
                    return doc;
                })
                .toList();
    }
}
