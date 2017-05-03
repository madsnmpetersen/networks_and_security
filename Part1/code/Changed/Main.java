

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		//runProblemFromSlides();
		runReferenceProblemFromSlides();
	}

	private static void runProblemFromSlides() {

		HashMap<Integer,Integer> neighboursFor0 = new HashMap<Integer, Integer>();
		neighboursFor0.put(1,2);
		neighboursFor0.put(2,7);
		Router router0 = new Router(0,3,neighboursFor0);

		HashMap<Integer,Integer> neighboursFor1 = new HashMap<Integer, Integer>();
		neighboursFor1.put(0,2);
		neighboursFor1.put(2,1);
		Router router1 = new Router(1,3,neighboursFor1);

		HashMap<Integer,Integer> neighboursFor2 = new HashMap<Integer, Integer>();
		neighboursFor2.put(0,7);
		neighboursFor2.put(1,1);
		Router router2 = new Router(2,3,neighboursFor2);

		ArrayList<Router> routers = new ArrayList<Router>();
		routers.add(router0);
		routers.add(router1);
		routers.add(router2);

		HashMap<Integer, HashMap<Integer,Integer>> graph = new HashMap<Integer, HashMap<Integer,Integer>>();
		graph.put(0, neighboursFor0);
		graph.put(1, neighboursFor1);
		graph.put(2, neighboursFor2);

		System.out.println("Initial distance matrices");
		printAllDistanceMatrices(routers);

		System.out.println("Final distance matrices");
		runAlgorithm(routers, graph);
	}

	private static void runReferenceProblemFromSlides() {
		HashMap<Integer,Integer> neighboursFor0 = new HashMap<Integer, Integer>();
		neighboursFor0.put(1,4);
		neighboursFor0.put(2,50);
		Router router0 = new Router(0,3,neighboursFor0);

		HashMap<Integer,Integer> neighboursFor1 = new HashMap<Integer, Integer>();
		neighboursFor1.put(0,4);
		neighboursFor1.put(2,1);
		Router router1 = new Router(1,3,neighboursFor1);

		HashMap<Integer,Integer> neighboursFor2 = new HashMap<Integer, Integer>();
		neighboursFor2.put(0,50);
		neighboursFor2.put(1,1);
		Router router2 = new Router(2,3,neighboursFor2);

		ArrayList<Router> routers = new ArrayList<Router>();
		routers.add(router0);
		routers.add(router1);
		routers.add(router2);

		HashMap<Integer, HashMap<Integer,Integer>> graph = new HashMap<Integer, HashMap<Integer,Integer>>();
		graph.put(0, neighboursFor0);
		graph.put(1, neighboursFor1);
		graph.put(2, neighboursFor2);


		System.out.println("Initial distance matrices");
		printAllDistanceMatrices(routers);

		System.out.println("Final distance matrices");
		runAlgorithm(routers, graph);

		System.out.println("Updating weight from 0 to 1 to new lower value = good news");
		router0.updateWeightToNeighbours(1, 1);
		router1.updateWeightToNeighbours(0, 1);
                System.out.println("Initial distance matrices");
		printAllDistanceMatrices(routers);
                System.out.println("Final distance matrices");
		runAlgorithm(routers, graph);

		System.out.println("Updating weight from 0 to 1 to new higher value = bad news");
		router0.updateWeightToNeighbours(1, 60);
		router1.updateWeightToNeighbours(0, 60);
                System.out.println("Initial distance matrices");
		printAllDistanceMatrices(routers);
                System.out.println("Final distance matrices");
		runAlgorithm(routers, graph);

	}

	private static void runAlgorithm(ArrayList<Router> routers, HashMap<Integer, HashMap<Integer,Integer>> graph ) {
		int count = 0;
		boolean changes;
		do {
			changes = false;
			count++;
			// Poll outgoing distance vectors, and deliver them to neighbours
			for( Router router : routers ) {
				Integer[] distanceVector = router.outgoingDistanceVector();
				if( distanceVector != null) {
					changes = true;
					for( Integer neighbour : graph.get(router.getRouterId()).keySet() ) {
						routers.get(neighbour).incomingDistanceVector(router.getRouterId(), distanceVector);
					}
				}
			}
			// Ask all routers to calculate new distance vector
			for( Router router : routers ) {
				router.calculateDistanceMatrix();
			}

			//System.out.println("\nIteration: " + count);      // For debugging, this can be handy
			//printAllDistanceMatrices(routers);                // For debugging, this can be handy
		} while(changes);

		printAllDistanceMatrices(routers);
		System.out.println( count + " iterations before algorithm stabilizes");
	}

	private static void printAllDistanceMatrices(ArrayList<Router> routers) {
		for( Router r : routers) {
			r.printDistanceMatrix();
		}
	}
}
