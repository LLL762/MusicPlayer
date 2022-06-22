package com.example.musicplayer.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * 22/06/2022.
 *
 * @author Laurent Lamiral
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class AudioFile {

    String name;
    String path;


}
