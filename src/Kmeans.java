import java.util.HashMap;

import kohonen.WTMLearningFunction;
import network.DefaultNetwork;
import topology.MatrixTopology;

public class Kmeans {
	private MatrixTopology topology;
	private WTMLearningFunction learning;
	private int height, width;
	private int[][] clustersClassification;
	private HashMap<Integer, String> colorMap;
	private int k;
	private DefaultNetwork network;

	public Kmeans(MatrixTopology topology, WTMLearningFunction learning, DefaultNetwork network, int k) {
		this.topology = topology;
		this.learning = learning;
		this.network = network;
		this.k = k;
		this.height = topology.getColNumber();
		this.width = topology.getRowNumber();
		this.clustersClassification = new int[this.width][this.height];
		this.colorMap = new HashMap<Integer, String>();
		
		doClustering();
		mapColorsPerNode();
	}

	private void doClustering() {

		// update clusterClassification field
	}

	private void mapColorsPerNode() {
		// create colors for colorMap

		for (int i = 0; i < k; i++) {
			colorMap.put(i, "red"); // put a random color
		}
	}

	private String getColor(double[] weights) {
		// What do with weights to get color?
		
		return "blue";
	}

	public String getColorFromNodeAt(int i) {
		return getColor(network.getNeuron(i).getWeight());
	}

}
