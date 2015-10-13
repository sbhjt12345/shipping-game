package student;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import game.Board;
import game.Edge;
import game.Game;
import game.Main;
import game.Parcel;
import game.Truck;
import game.Node;

public class MyManager extends game.Manager {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Game started");
		
	}

	@Override
	public void truckNotification(Truck t, Notification message) {
		// TODO Auto-generated method stub
		if (this.getParcels().size() == 0 && message == Notification.WAITING) {
			LinkedList<Node> path1 = dij(t.getLocation(), this.getBoard().getTruckDepot());
			t.setTravelPath(path1);
		}
		else if (message == Notification.WAITING && t.getLoad() == null) {
			if (t.getUserData() == null) {
				Parcel temp = Main.randomElement(this.getParcels());
				t.setUserData(temp);
				LinkedList<Node> path1 = dij(t.getLocation(), temp.getLocation());
				t.setTravelPath(path1);
			}
			else {
				t.pickupLoad((Parcel)(t.getUserData()));
				LinkedList<Node> path2 = dij(t.getLocation(), t.getLoad().destination);
				t.setTravelPath(path2);
				System.out.println("picked");
			}
				
			
		}

		else if (message == Notification.WAITING && t.getLoad() != null) {
			t.dropoffLoad();
			t.setUserData(null);
			System.out.println("drop");
		}
		
		


	}

	

	public LinkedList<Node> dij (Node start, Node end) {
		MyHeap<Node> frontier = new MyHeap<Node>();
		frontier.add(start, 0);
		HashMap<Node, Integer> distance = new HashMap<Node, Integer>();
		HashMap<Node, Node> previous = new HashMap<Node, Node>();
		previous.put(start, null);

		Board b = start.getBoard();
		for (Node temp : b.getNodes()) 
			distance.put(temp,Integer.MAX_VALUE);
		distance.put(start, 0);

		while (frontier.peek() != end) {
			Node temp = frontier.poll();
			for (Map.Entry<Node, Integer> entry: temp.getNeighbors().entrySet()) {
				if (distance.get(entry.getKey()) == Integer.MAX_VALUE) {
					frontier.add(entry.getKey(), distance.get(temp) + entry.getValue());
					distance.put(entry.getKey(), distance.get(temp) + entry.getValue());
					previous.put(entry.getKey(), temp);
				}
				else if (distance.get(entry.getKey()) > distance.get(temp) + entry.getValue()) {
					frontier.updatePriority(entry.getKey(), distance.get(temp) + entry.getValue());
					distance.put(entry.getKey(), distance.get(temp) + entry.getValue());
					previous.put(entry.getKey(), temp);
				}
			}
		}
		LinkedList<Node> result = new LinkedList<Node>();
		for (Node temp = end; temp != null; temp = previous.get(temp))
			result.push(temp);

		return result;
	}

	public void testDijkstraSimple() {
		Game game= getGame();
		Board board= game.getBoard();
		Node truckDepot= board.getTruckDepot();
		LinkedList<Node> ll= dij(truckDepot, truckDepot);
		System.out.println("Path from " + truckDepot + " to " + truckDepot + ": " + ll);

		Set<Node> nodes = this.getNodes();
		for (Node e : nodes) {
			ll= dij(truckDepot, e);
			System.out.println("Path from " + truckDepot + " to " + e + ": " + ll);
		}
	}

}

