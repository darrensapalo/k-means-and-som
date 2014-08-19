import java.util.HashMap;

import network.DefaultNetwork;

public class Kmeans {
	private HashMap<Integer, String> colorMap;
	private int k;
	private DefaultNetwork network;

	public Kmeans(DefaultNetwork network, int k) {
		this.network = network;
		this.k = k;
		this.colorMap = new HashMap<Integer, String>();
		
	}

	private String getColor(double[] weights) {
		// How to get color from the weights?
		return "blue";
	}

	public String getColorFromNodeAt(int i) {
		return getColor(network.getNeuron(i).getWeight());
	}

}
