package com.lbranco.tv_tracker_api.provider.anilist.mapper;

import com.lbranco.tv_tracker_api.model.Media;
import com.lbranco.tv_tracker_api.model.Title;
import com.lbranco.tv_tracker_api.provider.anilist.dto.AniListMediaResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AniListMapper {

    public List<Media> mapToMedia(AniListMediaResponse response) {

        return response.getData()
                       .getPage()
                       .getMedia()
                       .stream()
                       .map(this::mapToMedia)
                       .collect(Collectors.toList());
    }

    private Media mapToMedia(AniListMediaResponse.Media aniMedia) {
        Media media = new Media();

        media.setId("anilist:" + aniMedia.getId());

        Title title = new Title();
        title.setEnglish(aniMedia.getTitle().getEnglish());
        title.setOriginal(aniMedia.getTitle().getRomaji());
        media.setTitle(title);

        media.setImageUrl(
                aniMedia.getCoverImage() != null ? aniMedia.getCoverImage().getLarge() : null
        );
        media.setType(Media.Type.ANIME);
        media.setSource(Media.Source.ANILIST);
        media.setReleaseYear(
                aniMedia.getStartDate() != null ? aniMedia.getStartDate().getYear() : null
        );
        media.setScore(
                aniMedia.getAverageScore() != null
                        ? aniMedia.getAverageScore().floatValue()
                        : null
        );

        if (aniMedia.getMediaFormat() != null) {
            try {
                media.setMediaFormat(
                        Media.MediaFormat.valueOf(aniMedia.getMediaFormat().name())
                );
            } catch (IllegalArgumentException e) {
                media.setMediaFormat(Media.MediaFormat.UNKNOWN);
            }
        } else {
            media.setMediaFormat(Media.MediaFormat.UNKNOWN);
        }

        return media;
    }
}
