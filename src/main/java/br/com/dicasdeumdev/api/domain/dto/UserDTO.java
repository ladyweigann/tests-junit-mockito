package br.com.dicasdeumdev.api.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    private String name;

    private String email;
}
