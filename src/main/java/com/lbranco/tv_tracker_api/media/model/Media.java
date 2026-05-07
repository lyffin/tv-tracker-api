package com.lbranco.tv_tracker_api.media.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.lbranco.tv_tracker_api.shared.enums.MediaType;

@Getter
@Setter
@NoArgsConstructor
public class Media {

    private String id;
    private Title title;
    private String imageUrl;
    private MediaType type;
    private Integer releaseYear;
    private Float score;
}
