package com.votes.data.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@ToString
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    @Getter
    @Setter
    @ToString.Exclude
    private Game game;

    @Column(name = "player_name")
    @Getter
    @Setter
    private String playerName;

    @Column(name = "player_number")
    @Getter
    @Setter
    private Integer playerNumber;

    @Getter
    @Setter
    private Integer score;

    @Getter
    @Setter
    private Boolean assigned;

    @Column(name = "selected_number")
    @Getter
    @Setter
    private Integer selectedNumber;

    @Column(name = "own_number")
    @Getter
    @Setter
    private Integer ownNumber;

}
