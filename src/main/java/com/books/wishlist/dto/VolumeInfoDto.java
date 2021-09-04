package com.books.wishlist.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class VolumeInfoDto {

    private String title;
    private String subtitle;
    private String publisher;
    private List<String> authors;

}
