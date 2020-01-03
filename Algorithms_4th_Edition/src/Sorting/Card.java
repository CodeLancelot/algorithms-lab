package Sorting;

import libraries.*;

public class Card implements Comparable<Card> {
    private Suit suit;
    private Name name;

    Card(Suit s, Name n) {
        suit = s;
        name = n;
    }

    public static Card[] generateDeckOfCards() {
        Card[] cards = new Card[52];
        int index = 0;
        for (Suit suit : Suit.values()) {
            for (Name name : Name.values()) {
                Card card = new Card(suit, name);
                cards[index] = card;
                index++;
            }
        }
        return cards;
    }

    public static void displayCards(Card[] cards) {
        int len = cards.length;
        int col = len / 4;
        for (int i = 0; i < len; i++) {
            Card card = cards[i];
            if (col > 0 && i % col == 0) {
                StdOut.println();
            }
            StdOut.printf("%-4s", card.suit.toString().substring(0, 1) + (card.name.ordinal() + 1));
        }

        StdOut.println();
    }

    // return { -1, 0, +1 } if this < that, this = that, or this > that
    public int compareTo(Card that) {
        if (this.suit.ordinal() < that.suit.ordinal()) return -1;
        if (this.suit.ordinal() > that.suit.ordinal()) return +1;
        return Integer.compare(this.name.ordinal(), that.name.ordinal());
    }

    enum Suit {
        DIAMOND, CLUB, HEART, SPADE
    }

    enum Name {
        ACE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING
    }
}
