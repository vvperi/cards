package com.nike.microservices.exercise.cards;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_CARDS")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Long nextId = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;


    @Column(name = "name")
    private String name;

    @Column(name = "rank")
    private String rank;

    @Column(name = "suit")
    private String suit;

    @Column(name = "deckname")
    private String deckName;

    @Column(name = "sequence")
    private int sequenceInDeck;


    public Card() {}

    public Card(String rank, String suit, String deckName, int SequenceInDeck) {
        this.rank = rank;
        this.suit = suit;
        this.deckName = deckName;
        this.name = generateName(rank, suit);
        this.sequenceInDeck = SequenceInDeck;
    }

    private String generateName(String rank, String suit) {
        return new StringBuffer(rank).append("-").append(suit).toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getSuit() {
        return this.suit;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public int getSequenceInDeck() {
        return sequenceInDeck;
    }

    public void setSequenceInDeck(int sequenceInDeck) {
        this.sequenceInDeck = sequenceInDeck;
    }
}
