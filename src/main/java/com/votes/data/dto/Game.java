package com.votes.data.dto;

import com.votes.data.Status;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Does not contain key word.
 */
@Data
public class Game {

    private Integer id;

    private String name;

    private Status status;

    private Integer roundNumber;

    private Timestamp created;

    private List<Player> players;

}
