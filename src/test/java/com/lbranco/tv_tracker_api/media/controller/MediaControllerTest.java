package com.lbranco.tv_tracker_api.media.controller;

import com.lbranco.tv_tracker_api.config.SecurityConfig;
import com.lbranco.tv_tracker_api.media.model.Media;
import com.lbranco.tv_tracker_api.media.model.MediaDetails;
import com.lbranco.tv_tracker_api.media.model.SeasonDetails;
import com.lbranco.tv_tracker_api.media.service.MediaDetailsService;
import com.lbranco.tv_tracker_api.media.service.MediaSearchService;
import com.lbranco.tv_tracker_api.media.service.SeasonDetailsService;
import com.lbranco.tv_tracker_api.shared.enums.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MediaController.class)
@Import({MediaControllerAdvice.class, SecurityConfig.class})
class MediaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MediaSearchService mediaSearchService;

    @MockitoBean
    private MediaDetailsService mediaDetailsService;

    @MockitoBean
    private SeasonDetailsService seasonDetailsService;

    @Test
    void searchBindsTypeAndReturnsResults() throws Exception {
        Media media = new Media();
        media.setId("tmdb:1399");
        media.setType(MediaType.TV);

        when(mediaSearchService.search("dark", MediaType.TV)).thenReturn(List.of(media));

        mockMvc.perform(get("/media/search")
                        .param("query", "dark")
                        .param("type", "TV"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("tmdb:1399"))
                .andExpect(jsonPath("$[0].type").value("TV"));

        verify(mediaSearchService).search("dark", MediaType.TV);
    }

    @Test
    void searchWithInvalidQueryTypeReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/media/search")
                        .param("query", "dark")
                        .param("type", "cartoon"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Invalid value for 'type': cartoon."));
    }

    @Test
    void detailsWithInvalidPathTypeReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/media/documentary/123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Invalid media type: documentary. Supported values are movie, tv, anime."));
    }

    @Test
    void detailsUsesParsedMediaType() throws Exception {
        MediaDetails mediaDetails = new MediaDetails();
        mediaDetails.setId("tmdb:550");
        mediaDetails.setType(MediaType.MOVIE);

        when(mediaDetailsService.details(MediaType.MOVIE, 550)).thenReturn(mediaDetails);

        mockMvc.perform(get("/media/movie/550"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("tmdb:550"))
                .andExpect(jsonPath("$.type").value("MOVIE"));

        verify(mediaDetailsService).details(MediaType.MOVIE, 550);
    }

    @Test
    void seasonDetailsReturnsServiceResponse() throws Exception {
        SeasonDetails seasonDetails = new SeasonDetails();
        seasonDetails.setId(101);
        seasonDetails.setSeasonNumber(1);

        when(seasonDetailsService.seasonDetails(1399, 1)).thenReturn(seasonDetails);

        mockMvc.perform(get("/media/tv/1399/season/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.seasonNumber").value(1));

        verify(seasonDetailsService).seasonDetails(1399, 1);
    }
}
