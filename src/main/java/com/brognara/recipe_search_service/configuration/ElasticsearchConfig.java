package com.brognara.recipe_search_service.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.brognara.recipe_search_service.repository.ElasticRecipeRepository;
import com.brognara.recipe_search_service.repository.RecipeRepository;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    private static final String API_KEY = "bVc0Y1JaTUJVN0RWeEJONmI5SnU6TnlqREU1X2NTYm1URlZrRVlDZG4xQQ==";

    @Bean
    public ElasticsearchAsyncClient elasticsearchAsyncClient() {
        final BasicHeader apiKeyHeader = new BasicHeader(HttpHeaders.AUTHORIZATION, "ApiKey " + API_KEY);
        return new ElasticsearchAsyncClient(
                new RestClientTransport(
                        RestClient.builder(
                                new HttpHost("localhost", 9200, "http")
                        )
                                .setDefaultHeaders(new BasicHeader[]{apiKeyHeader})
                                .build(),
                        new JacksonJsonpMapper()
                )
        );
    }

    @Bean
    public RecipeRepository recipeRepository(final ElasticsearchAsyncClient elasticsearchAsyncClient) {
        return new ElasticRecipeRepository(elasticsearchAsyncClient);
    }

}
