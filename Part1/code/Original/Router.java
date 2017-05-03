import java.util.HashMap;

public class Router {

	private int routerId;
	private Integer[][] distanceMatrix;
	private HashMap<Integer,Integer> neighbours;
	private Integer[] outgoingDistanceVector;
	private int totalRouters;

	public Router(int routerId, int totalRouters, HashMap<Integer,Integer> neighbours) {
		this.routerId = routerId;
		this.totalRouters = totalRouters;
		this.neighbours = neighbours;
		initializeDistanceMatrix();
		createOutgoingVector();
	}

	// Updates a weight on an edge to a neighbour
	public void updateWeightToNeighbours(Integer neighbour, Integer newWeight) {
		neighbours.put(neighbour, newWeight);
		distanceMatrix[neighbour][routerId] = newWeight;
	}

	// Setting up initial distance matrix
	private void initializeDistanceMatrix() {
		distanceMatrix = new Integer[totalRouters][totalRouters];
		distanceMatrix[routerId][routerId] = 0;
		for( Integer router : neighbours.keySet()) {
			distanceMatrix[router][routerId] = neighbours.get(router);
		}
	}

	// Extracts the distance vector from the distance matrix
	private void createOutgoingVector() {
		outgoingDistanceVector = new Integer[totalRouters];
		for( int i = 0; i < totalRouters; i++) {
			outgoingDistanceVector[i] = distanceMatrix[i][routerId];
		}
	}

	/* Receive a distance vector from another router.
	 * Uses this to update distanceMatrix. Later calculateDistanceMatrix() will use the new matrix
	 * to calculate new distance vector for this router
	 * i.e. multiple distance vectors can be received, before the router calculates and 'sends'
	 * its distance vector
	 */
	public void incomingDistanceVector(Integer from, Integer[] distances) {

		// TODO Must be implemented

	}

	/* Calculates the new distance vector, and examines if there are changes that
	 * should be sent for the other routers. If there are, prepare the distance vector for sending,
	 * i.e. it is polled in outgoingDistanceVector()
	 */
	public void calculateDistanceMatrix() {

		// Calculate own distance vector using Bellmann Ford equation
		// If there are changes, prepare outgoingDistanceVector with the new vector, otherwise, set it to null

		// TODO Must be implemented

	}

	/* Returns a distance vector, if there are changes since last call to this method.
	 * If there are no changes, null must be returned
	 */
	public Integer[] outgoingDistanceVector() {

		// TODO Must be implemented

		return null;
	}

	public int getRouterId() {
		return routerId;
	}

	public void printDistanceVector() {
		System.out.println("----- Distance matrix ------");
		System.out.print("id:" + routerId + "\t");
		for( int i = 0; i < distanceMatrix.length; i++) {
			System.out.print( i + "\t");
		}
		System.out.println("");
		for( int i = 0; i < distanceMatrix.length; i++) {
			System.out.print( i + "\t\t");
			for( int j = 0; j < distanceMatrix.length; j++) {
				if( distanceMatrix[j][i] == null ) {
					System.out.print("-\t");
				} else {
					System.out.print( distanceMatrix[j][i] + "\t");
				}
			}
			System.out.println("");
		}
	}



}
