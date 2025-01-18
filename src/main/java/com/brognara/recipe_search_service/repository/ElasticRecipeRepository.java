package com.brognara.recipe_search_service.repository;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.brognara.recipe_search_service.model.RecipeDetailsResponse;
import com.brognara.recipe_search_service.model.RecipeDocument;

import java.util.concurrent.CompletableFuture;

public class ElasticRecipeRepository implements RecipeRepository {

    private final String indexName = "recipe"; // TODO make property
    private final ElasticsearchAsyncClient elasticsearchAsyncClient;

    public ElasticRecipeRepository(ElasticsearchAsyncClient elasticsearchAsyncClient) {
        this.elasticsearchAsyncClient = elasticsearchAsyncClient;
    }

    @Override
    public CompletableFuture<String> publishRecipe(final RecipeDocument recipeDocument) {
        return elasticsearchAsyncClient.index(
                IndexRequest.of(builder -> builder
                        .index(indexName)
                        .document(recipeDocument)
                )
        ).thenApply(indexResponse -> indexResponse.result().jsonValue()); // Returns CREATED or UPDATED
    }

    @Override
    public CompletableFuture<SearchResponse<RecipeDocument>> searchRecipes(final Query query) {
        return elasticsearchAsyncClient.search(
                SearchRequest.of(builder -> builder
                        .index(indexName)
                        .query(query)
                ),
                RecipeDocument.class
        );
    }
}
