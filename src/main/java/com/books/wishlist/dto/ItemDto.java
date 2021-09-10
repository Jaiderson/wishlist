package com.books.wishlist.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter @ToString
public class ItemDto {

	@ApiModelProperty(position=1, dataType="String", value="Id del libro en el API books de Google.<br>", 
			                                         example="zug36aj0JWIC", required=true)
    private String id;

	@ApiModelProperty(position=2, dataType="VolumeInfoDto", value="Detalle del libro encontrado.", 
			                                                example="10", required=true)
    private VolumeInfoDto volumeInfo;

}
