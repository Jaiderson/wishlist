package com.books.wishlist.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class ItemDto {

    private String id;
    private VolumeInfoDto volumeInfo;

}
