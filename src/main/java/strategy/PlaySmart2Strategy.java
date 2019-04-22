package strategy;

import game.Board;
import game.Card;
import game.Type;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class PlaySmart2Strategy extends Stragegy{

    private Type color1;
    private Type color2;
    private Type color3;
    private Type color4;
    private Type color5;
    private Type color6;

    public PlaySmart2Strategy(Type c1)
    {
        color1 = c1;
    }
    public PlaySmart2Strategy(Type c1, Type c2)
    {
        color1 = c1;
        color2 = c2;
    }
    public PlaySmart2Strategy(Type c1, Type c2, Type c3)
    {
        color1 = c1;
        color2 = c2;
        color3 = c3;
    }
    public PlaySmart2Strategy(Type c1, Type c2, Type c3, Type c4)
    {
        color1 = c1;
        color2 = c2;
        color3 = c3;
        color4 = c4;
    }
    public PlaySmart2Strategy(Type c1, Type c2, Type c3, Type c4, Type c5)
    {
        color1 = c1;
        color2 = c2;
        color3 = c3;
        color4 = c4;
        color5 = c5;
    }
    public PlaySmart2Strategy(Type c1, Type c2, Type c3, Type c4, Type c5, Type c6)
    {
        color1 = c1;
        color2 = c2;
        color3 = c3;
        color4 = c4;
        color5 = c5;
        color6 = c6;
    }


    public JSONObject action(List<Card> hand, List<Card> cardPlayed)
    {
        JSONObject res = new JSONObject();
        List<Card> color1Cards = new ArrayList<Card>();
        List<Card> color2Cards = new ArrayList<Card>();
        List<Card> color3Cards = new ArrayList<Card>();
        List<Card> color4Cards = new ArrayList<Card>();
        List<Card> color5Cards = new ArrayList<Card>();
        List<Card> color6Cards = new ArrayList<Card>();
        Boolean color1Card = false;
        Boolean color2Card = false;
        Boolean color3Card = false;
        Boolean color4Card = false;
        Boolean color5Card = false;
        Boolean color6Card = false;

        for(Card oneCard : hand)
        {
            Type type = oneCard.getType();
            String name = oneCard.getName();
            if(type == color1)
            {
                color1Card = true;
                color1Cards.add(oneCard);

                for(Card cardPlay : cardPlayed)
                {
                    if(name == cardPlay.getName())
                    {
                        color1Cards.remove(oneCard);
                    }
                }
            }
            else if(type == color2)
            {
                color2Card = true;
                color2Cards.add(oneCard);

                for(Card cardPlay : cardPlayed)
                {
                    if(name == cardPlay.getName())
                    {
                        color2Cards.remove(oneCard);
                    }
                }
            }
            else if(type == color3)
            {
                color3Card = true;
                color3Cards.add(oneCard);

                for(Card cardPlay : cardPlayed)
                {
                    if(name == cardPlay.getName())
                    {
                        color3Cards.remove(oneCard);
                    }
                }
            }
            else if(type == color4)
            {
                color4Card = true;
                color4Cards.add(oneCard);

                for(Card cardPlay : cardPlayed)
                {
                    if(name == cardPlay.getName())
                    {
                        color4Cards.remove(oneCard);
                    }
                }
            }
            else if(type == color5)
            {
                color5Card = true;
                color5Cards.add(oneCard);

                for(Card cardPlay : cardPlayed)
                {
                    if(name == cardPlay.getName())
                    {
                        color5Cards.remove(oneCard);
                    }
                }
            }
            else if(type == color6)
            {
                color6Card = true;
                color6Cards.add(oneCard);

                for(Card cardPlay : cardPlayed)
                {
                    if(name == cardPlay.getName())
                    {
                        color6Cards.remove(oneCard);
                    }
                }
            }
        }

        if(color1Card)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(color1Cards.size());
            Card chosenCard = color1Cards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", chosenCard.getName());
            System.out.println(chosenCard.getName() + " a ete joue");
            hand.remove(cardPlayed);
            cardPlayed.add((chosenCard));

        }
        else if(!color1Card && color2Card)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(color2Cards.size());
            Card chosenCard = color2Cards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", chosenCard.getName());
            System.out.println(chosenCard.getName() + " a ete joue");
            hand.remove(cardPlayed);
            cardPlayed.add(chosenCard);
        }
        else if(!color1Card && !color2Card && color3Card)
        {

            Random rand = new Random();
            int nbAlea = rand.nextInt(color3Cards.size());
            Card chosenCard = color3Cards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", chosenCard.getName());
            System.out.println(chosenCard.getName() + " a ete joue");
            hand.remove(cardPlayed);
            cardPlayed.add(chosenCard);
        }
        else if (!color1Card && !color2Card && !color3Card && color4Card)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(color4Cards.size());
            Card chosenCard = color4Cards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", chosenCard.getName());
            System.out.println(chosenCard.getName() + " a ete joue");
            hand.remove(cardPlayed);
            cardPlayed.add(chosenCard);
        }
        else if (!color1Card && !color2Card && !color3Card && !color4Card && color5Card)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(color5Cards.size());
            Card chosenCard = color5Cards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", chosenCard.getName());
            System.out.println(chosenCard.getName() + " a ete joue");
            hand.remove(cardPlayed);
            cardPlayed.add(chosenCard);
        }
        else if (!color1Card && !color2Card && !color3Card && !color4Card && !color5Card && color6Card)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(color6Cards.size());
            Card chosenCard = color6Cards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", chosenCard.getName());
            System.out.println(chosenCard.getName() + " a ete joue");
            hand.remove(cardPlayed);
            cardPlayed.add(chosenCard);
        }
        else
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(hand.size());
            Card sacrificed = hand.get(nbAlea);
            res.put("command", "DISCARD");
            res.put("card", sacrificed.getName());
            System.out.println(sacrificed.getName() + " a ete defausse");
            hand.remove(sacrificed);
        }
        return res;
    }
}
