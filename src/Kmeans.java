import java.util.HashMap;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.gui.streams.InstanceLoader;
import network.DefaultNetwork;

public class Kmeans {
	private HashMap<Integer, String> colorMap;
	private int k;
	private DefaultNetwork network;
	private Instances instances;
	
	public Kmeans(Instances instances, int k) {
		this.k = k;
		this.colorMap = new HashMap<Integer, String>();
		this.instances = instances;
		
	}
	
	public int[] getAssignments() {
	    SimpleKMeans kmeans = new SimpleKMeans();

        kmeans.setSeed(10);
        
        kmeans.setPreserveInstancesOrder(true);
        try {
            kmeans.setNumClusters(k);
            kmeans.buildClusterer(instances);
            return kmeans.getAssignments();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	    return new int[0];
	}

	private String getColor(double[] weights) {
		// How to get color from the weights?
		return "blue";
	}

	public String getColorFromNodeAt(int i) {
		return getColor(network.getNeuron(i).getWeight());
	}

}
