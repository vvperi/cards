package com.nike.microservices.exercise.cards;

import com.nike.microservices.exercise.exceptions.DeckNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


@RestController
@PropertySource("classpath:dealer-algo-config.properties")
public class CardsController {

    protected Logger logger = Logger.getLogger(CardsController.class
            .getName());
    protected CardsRepository cardsRepository;

    @Value("${shuffle-algorithm}")
    private String shuffleAlgorithm;

    @Autowired
    public CardsController(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;

        logger.info("CardsRepository says system has "
                + cardsRepository.findDistinctDeckNames().size() + " decks");
    }

    @RequestMapping(value = "/fetchdeck/{deckName}", method = RequestMethod.GET)
    public List<Card> getDeckOfCards(@PathVariable("deckName") String deckName) {
        logger.info("Get Deck by deckName: " + deckName);
        List<Card> cards = cardsRepository.findByDeckName(deckName);
        logger.info("Deck found for: " + deckName);

        if (cards == null)
            throw new DeckNotFoundException(deckName);
        else {
            return cards;
        }
    }

    @RequestMapping(value = "/fetchalldecks", method = RequestMethod.GET)
    public List<String> getAllDecks() {
        logger.info("Get all Deck Names");
        List<String> decks = cardsRepository.findDistinctDeckNames();
        logger.info("All decks listed: " + decks);

        if (decks == null)
            throw new DeckNotFoundException("*");
        else {
            return decks;
        }
    }

    @RequestMapping(value = "/createdeck/{deckName}", method = RequestMethod.PUT)
    public List<Card> createNewDeck(@PathVariable("deckName") String deckName) {
        Deck deck = new Deck(createDeck(deckName), shuffleAlgorithm);
        List<Card> cards = deck.getDeck();
        cardsRepository.save(cards);
        return cards;
    }

    @RequestMapping(value = "/shuffledeck/{deckName}", method = RequestMethod.POST)
    public List<Card> shuffleDeck(@PathVariable("deckName") String deckName) {
        List<Card> cards = cardsRepository.findByDeckName(deckName);
        Deck deck = new Deck(cards, shuffleAlgorithm);
        List<Card> shuffledDeck = deck.shuffleDeck();
        cardsRepository.save(shuffledDeck);
        return shuffledDeck;
    }

    @RequestMapping(value = "/deletedeck/{deckName}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteDeck(@PathVariable("deckName") String deckName) {
        List<Card> deck = cardsRepository.findByDeckName(deckName);
        cardsRepository.delete(deck);
    }

    private List<Card> createDeck(String deckName) {
        final String[] SUITS = {
                "Club", "Diamond", "Heart", "Spade"
        };

        final String[] RANKS = {
                "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "Jack", "Queen", "King", "Ace"
        };


        int n = SUITS.length * RANKS.length;

        List<Card> cards = new LinkedList<>();
        for (int i = 0; i < RANKS.length; i++) {
            for (int j = 0; j < SUITS.length; j++) {
                int sequence = cards.size();
                cards.add(new Card(RANKS[i], SUITS[j], deckName, sequence));
            }
        }

        System.out.println("Initialized deck:");
        for (Iterator iter = cards.iterator(); iter.hasNext(); ) {
            System.out.println(iter.next());
        }
        return cards;
    }
}
