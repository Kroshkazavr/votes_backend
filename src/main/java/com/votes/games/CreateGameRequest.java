package com.votes.games;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ValidCreateGameRequest
public class CreateGameRequest {

    @Size(min = 3, max = 15)
    @NotNull
    private String gameName;

    @Size(min = 3, max = 15)
    @NotNull
    private String keyWord;

    @NotEmpty
    @Size(min = 2)
    private List<String> playersNames;

}
