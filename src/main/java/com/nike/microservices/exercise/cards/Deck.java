package com.nike.microservices.exercise.cards;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Deck {

    private List<Card> deck;
    private String shuffleAlgorithm = "";

    public Deck(List<Card> deck) {
        this.deck = deck;
    }

    public Deck(List<Card> deck, String shuffleAlgorithm) {
        this.deck = deck;
        this.shuffleAlgorithm = shuffleAlgorithm;
    }

    public List<Card> getDeck() {
        return this.deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public String getShuffleAlgorithm() {
        return this.shuffleAlgorithm;
    }

    public void setShuffleAlgorithm(String shuffleAlgorithm) {
        this.shuffleAlgorithm = shuffleAlgorithm;
    }

    public List<Card> shuffleDeck() {
        switch (shuffleAlgorithm) {
            case "handShuffle":
                this.deck = handShuffle(this.deck);
                break;
            default:
                this.deck = simpleShuffle(this.deck);
        }
        return this.deck;
    }

    private List<Card> handShuffle(List<Card> deck) {
        int deckSize = deck.size();
        int fromIndex = 0;
        int midIndex = deckSize % 2 == 0 ? deckSize / 2 : (deckSize + 1) / 2;
        List<Card> firstHalfDeck = deck.subList(fromIndex, midIndex);
        List<Card> secondHalfDeck = deck.subList(midIndex, deckSize);
        List<Card> shuffledDeck = new LinkedList<Card>();
        for (int i = 0; i < midIndex; i++) {
            int currentSize = shuffledDeck.size();
            Card cardFromFirstHalf = firstHalfDeck.get(i);
            cardFromFirstHalf.setSequenceInDeck(currentSize);
            shuffledDeck.add(cardFromFirstHalf);

            Card cardFromSecondHalf = secondHalfDeck.get(i);
            cardFromSecondHalf.setSequenceInDeck(currentSize + 1);
            shuffledDeck.add(cardFromSecondHalf);
        }
        return shuffledDeck;
    }

    private List<Card> simpleShuffle(List<Card> deck) {
        Card[] cardsArray = deck.toArray(new Card[deck.size()]);
        int numberOfCards = cardsArray.length;
        for (int i = 0; i < numberOfCards; i++) {
            int r = i + (int) (Math.random() * (numberOfCards - i));
            Card temp = cardsArray[r];
            cardsArray[r] = cardsArray[i];
            cardsArray[i] = temp;
        }

        System.out.println("\n \n \nShuffled deck:");
        for (int i = 0; i < numberOfCards; i++) {
            System.out.println(cardsArray[i]);
        }

        return Arrays.asList(cardsArray);
    }
}
