package com.nike.microservices.exercise.cards;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardsRepository extends CrudRepository<Card, Long> {

    List<Card> findByDeckName(String deckName);

    @Query("SELECT DISTINCT deckName from Card")
    List<String> findDistinctDeckNames();
}
