import java.util.*;
import java.io.*;

class BlackJack {
    public static void main(String[] args) throws IOException {
        String[][] bankCards = {};
        String[][] playerCards = {};

        bankCards = BlackJack.addCardToHand(bankCards);
        playerCards = BlackJack.addCardToHand(playerCards);
        playerCards = BlackJack.addCardToHand(playerCards);

        Console console = System.console();

        System.out.println("*************");
        System.out.println("* BLACKJACK *");
        System.out.println("*************");
        System.out.println("Welcome on the table");

        boolean newCard = true;

        while (newCard == true) {
            System.out.print(BlackJack.drawTable(bankCards, playerCards));

            String playerNewCardChoice = console.readLine("\nnew Card ? (y, n) :");

            if (playerNewCardChoice.equals("y")) {
                playerCards = BlackJack.addCardToHand(playerCards);
            }

            if (playerNewCardChoice.equals("n") || BlackJack.playerTotal(playerCards) > 21) {
                newCard = false;
            }
        }

        if (BlackJack.playerTotal(playerCards) > 21) {
            System.out.print(BlackJack.drawTable(bankCards, playerCards));
            System.out.println("You loose");
            System.exit(0);
        }

        while (BlackJack.playerTotal(bankCards) <= 16) {
            System.out.print("\nBank add a card");
            bankCards = BlackJack.addCardToHand(bankCards);
        }

        System.out.print(BlackJack.drawTable(bankCards, playerCards));

        boolean victory = BlackJack.playerVictory(bankCards, playerCards);
        System.out.println("");
        if (victory==true) {
            System.out.println("You win :-)");
        } else {
            System.out.println("Bank wins :-(");
        }
    }

    private static String[][] addCardToHand(String[][] cards) {
        String[][] tmpPlayerCards = Arrays.copyOf(cards, cards.length + 1);
        String[] newCard = BlackJack.getCard();

        Console console = System.console();
        if(newCard[0].equals("A")) {
            String asCardValue = console.readLine("\nWhich value for the As ? (1 or 11) :");
            newCard[1] = asCardValue;
        }
        tmpPlayerCards[tmpPlayerCards.length -1] = newCard;
        return tmpPlayerCards;
    }

    private static boolean playerVictory(String[][]bankCards, String[][]playerCards) {
        return BlackJack.playerTotal(playerCards) > BlackJack.playerTotal(bankCards) || BlackJack.playerTotal(bankCards) > 21;
    }

    private static String drawCards(String[][] cards, String player) {
        String output = "";
        output += "\033[48;5;22m\n\n    "+player+"("+ BlackJack.playerTotal(cards)+")\n\n   ";

        for (String[] card : cards) {
            output += " \033[0m\033[1;31;22m   \033[0m\033[48;5;22m ";
        }
        output += "\n   ";
        for (String[] card : cards) {
            output += " \033[0m\033[1;31;22m "+ card[0] +" \033[0m\033[48;5;22m ";
        }
        output += "\n   ";

        for (String[] card : cards) {
            output += " \033[0m\033[1;31;22m   \033[0m\033[48;5;22m ";
        }
        output += "\n\033[0m";

        return output;
    }

    private static String drawTable(String[][] bankCards, String[][] playerCards)
    {
         return BlackJack.drawCards(bankCards, "Bank") + BlackJack.drawCards(playerCards, "Player1");
    }

    private static String[] getCard() {
        int cardRandom = (int)(Math.random()*14);
        String[] cardNames = {"A","2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        int[] cardValues = {1,2,3,4,5,6,7,8,9,10,10,10,10,11};
        String[] card = {cardNames[cardRandom], Integer.toString(cardValues[cardRandom])};
        return card;
    }

    private static int playerTotal(String[][] cards) {
        int total = 0;
        for (String[] card : cards) {
            total += Integer.parseInt(card[1]);;
        }

        return total;
    }
}
