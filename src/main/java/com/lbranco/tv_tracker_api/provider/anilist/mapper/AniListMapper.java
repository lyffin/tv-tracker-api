package com.lbranco.tv_tracker_api.provider.anilist.mapper;

import com.lbranco.tv_tracker_api.media.model.Media;
import com.lbranco.tv_tracker_api.media.model.Title;
import com.lbranco.tv_tracker_api.shared.enums.MediaFormat;
import com.lbranco.tv_tracker_api.shared.enums.MediaSource;
import com.lbranco.tv_tracker_api.shared.enums.MediaType;
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
        media.setType(MediaType.ANIME);
        media.setSource(MediaSource.ANILIST);
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
                        MediaFormat.valueOf(aniMedia.getMediaFormat().name())
                );
            } catch (IllegalArgumentException e) {
                media.setMediaFormat(MediaFormat.UNKNOWN);
            }
        } else {
            media.setMediaFormat(MediaFormat.UNKNOWN);
        }

        return media;
    }
}
