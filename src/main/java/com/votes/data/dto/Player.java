package com.votes.data.dto;

import lombok.Data;

@Data
public class Player {

    private Integer id;

    private String playerName;

    private Integer playerNumber;

    private Integer score;

    private Boolean assigned;

    private Integer selectedNumber;

    private Integer ownNumber;

}
