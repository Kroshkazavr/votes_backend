package com.votes.data.dao;

import com.votes.data.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;

@Entity
@ToString
@Builder
public class Game {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer amount;

    @Column(name = "key_word")
    @Getter
    @Setter
    private String keyWord;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVE','CLOSED')")
    @Getter
    @Setter
    private Status status;

    @Column(name = "round_number")
    @Getter
    @Setter
    private Integer roundNumber;

    @CreationTimestamp
    @Getter
    @Setter
    private Timestamp created;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "game")
    @Getter
    @Setter
    private List<Player> players;

}
