package com.lbranco.tv_tracker_api.provider.anilist;

import com.lbranco.tv_tracker_api.provider.anilist.dto.AniListMediaResponse;
import com.lbranco.tv_tracker_api.provider.anilist.dto.AniListRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AniListClient {

    private final WebClient webClient;

    public AniListClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://graphql.anilist.co")
                .build();
    }

    public AniListMediaResponse searchAnime(String search) {

        String query = """
            query ($search: String!) {
              Page {
                media(search: $search, type: ANIME) {
                  id
                  title {
                    romaji
                    english
                  }
                  averageScore
                  coverImage {
                    large
                  }
                  startDate {
                    year
                  }
                  format
                }
              }
            }
        """;

        AniListRequest request = new AniListRequest();
        request.setQuery(query);

        AniListRequest.Variables variables = new AniListRequest.Variables();
        variables.setSearch(search);
        request.setVariables(variables);

        return webClient.post()
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(AniListMediaResponse.class)
                        .block();
    }
}
