package com.brognara.recipe_search_service.repository;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
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
}
