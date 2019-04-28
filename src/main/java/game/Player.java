package game;

import java.util.List;
import java.util.Map;

public class Player {
	private Board playerBoard;
	private List<Card> hand;
	private List<Card> oldHand;
	private Map<Resources, Integer> resources;
	private List<Card> cardsPlayed;
	private int gold;
	private int victory;
	private int battle;
	private Player playerLeft;
	private Player playerRight;
	private int id;

	public int getId() {
		return id;
	}

	public Player(int id, Board playerBoard, List<Card> hand, List<Card> oldHand, Map<Resources, Integer> res, List<Card> played, int gold, int victory, int battle, Player playerLeft, Player playerRight) {
		this.id = id;
		this.playerBoard = playerBoard;
		this.hand = hand;
		this.oldHand = oldHand;
		this.resources = res;
		this.cardsPlayed = played;
		this.gold = gold;
		this.victory = victory;
		this.battle = battle;
		this.playerLeft = playerLeft;
		this.playerRight = playerRight;
	}

	public List<Card> getHand() {
		return hand;
	}

	public List<Card> getOldHand() { return oldHand;}

	public List<Card> getCardsPlayed() {
		return cardsPlayed;
	}

    public int getGold(){
    	return gold;
    }

	public Map<Resources, Integer> getResources() {
		return resources;
	}

	public void changeAmountGold(int val) {
		if(gold + val >= 0) {
			gold += val;
		} else {
			gold = 0;
		}
	}

	public void addResources(Resources ownres, int qte){
		boolean exist = false;
		if(ownres == null) {

		}else {
			for (Map.Entry<Resources, Integer> map: resources.entrySet()) { // On parcourt la map de resources
				if(ownres == map.getKey()) { // On v�rifie si la resources existe d�j� et on ajoute 1
					resources.put(ownres, map.getValue() + qte);
					exist = true;
				}
			}

			if(exist ==  false) {
				resources.put(ownres, qte); // Sinon, on cr�e la resources
			}
		}
	}

	public void setHand(List<Card> list) {
		this.hand = list;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PLAYER INFO :\n");
		sb.append(playerBoard.toString());
		sb.append("Gold: " + gold + "\n");
		sb.append("Victory points: " + victory + "\n");
		sb.append("Battle points: " + battle + "\n");
		if(resources != null && !resources.isEmpty()) {
			sb.append("\nRESOURCES: \n");
			for (Map.Entry<Resources, Integer> map: resources.entrySet()
					) {
				sb.append(map.getKey() + ": " + map.getValue() + "\n");
			}
		}
		if(hand != null && !hand.isEmpty()) {
			sb.append("\nHAND: \n");
			for (Card c: hand
					) {
				sb.append(c.toString());
			}
		}

		if(cardsPlayed != null) {
			sb.append("Cards played: ");
			for (Card c: this.cardsPlayed
			) {
				sb.append(c.toString() + "\n");
			}
		}
		return sb.toString();
	}
}
