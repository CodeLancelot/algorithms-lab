package Sorting;

import Fundamentals.Tools;

public class ElementarySorts {
    public static void main(String[] args) {
        Card[] cards = Card.generateDeckOfCards();
        Card.displayCards(cards);
        Tools.shuffle(cards);
        Card.displayCards(cards);
        deckSort(cards);
    }

    static void deckSort(Card[] cards) {
        Shell.sort(cards);
        Card.displayCards(cards);
    }
}
