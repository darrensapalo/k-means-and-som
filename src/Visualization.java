import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import kmeans.ColorPicker;
import kmeans.NeuronsToInstance;
import kohonen.LearningData;
import kohonen.WTMLearningFunction;
import learningFactorFunctional.LinearFunctionalFactor;
import metrics.EuclidesMetric;
import network.DefaultNetwork;
import topology.GaussNeighbourhoodFunction;
import topology.MatrixTopology;

import weka.core.Instances;

public class Visualization {

	Kmeans kmeansalgo;
	private String datasetTitle;
	private int width;
	private int height;
	private LearningData fileData;
	private LearningData labelledData;
	private int inputs;

	public Visualization(String datasetTitle, int width, int height,
			LearningData fileData, LearningData labelledData, int inputs, int maxIterations) {

		this.datasetTitle = datasetTitle;
		this.width = width;
		this.height = height;
		this.fileData = fileData;
		this.labelledData = labelledData;
		this.inputs = inputs;

		int g = 11;

		// Creates a Matrix Topology with width columns and height rows
		MatrixTopology topology = new MatrixTopology(width, height);

		// Defines max value of neuron weights
		double[] maxWeight = new double[inputs];

		// Creates a network with a # of inputs, max weight of neurons and a
		// topology
		DefaultNetwork network = new DefaultNetwork(inputs, maxWeight, topology);

		// Create LearningFactorFunctionalModel
		LinearFunctionalFactor linearFunctionalFactor = new LinearFunctionalFactor(
				0.8, maxIterations);

		// Set the Gauss Neighborhood Function
		GaussNeighbourhoodFunction gaussFunction = new GaussNeighbourhoodFunction(
				1);

		// Set learning Function - Winner Take Most Function
		WTMLearningFunction learning = new WTMLearningFunction(network,
				maxIterations, new EuclidesMetric(), fileData,
				linearFunctionalFactor, gaussFunction);

		// Start learning process
		learning.learn();
		
		
		// Create EuclidesMetric
		EuclidesMetric metric = new EuclidesMetric();

		// Store all labels of instances
		int[] labels = storeInstanceLabels(fileData, labelledData);

		int[] label = new int[width * height];
		double[] lowestValues = new double[g];

		// Loop for each neuron
		for (int currentNeuronIdx = 0; currentNeuronIdx < network.getNumbersOfNeurons(); currentNeuronIdx++) {

			// Initialize containers
			double[] distances = new double[fileData.getDataSize()];
			Stack<Integer> indexes = new Stack<Integer>();
			double[] weightOfCurrentNeuron = network
					.getNeuron(currentNeuronIdx).getWeight();
			Arrays.fill(lowestValues, Integer.MAX_VALUE);

			distances = getEuclideanDistances(distances, fileData, metric, weightOfCurrentNeuron);
			lowestValues = getLowestValues(lowestValues, fileData, distances, indexes, g);
			int[] cl = getLabels(indexes, g, labels);
			HashMap<Integer, Integer> frequencyCount = getFrequencyCount(cl);
			label[currentNeuronIdx] = getMostFrequent(frequencyCount);
		}
		
		Instances instances = new NeuronsToInstance().convert(network, "Dataset", inputs);
        int k = 7;
        Kmeans kmeans = new Kmeans(instances, k);
        int[] clusters = kmeans.getAssignments();

		
		produceHTMLvisualization(clusters, label, datasetTitle);
  	}

	private void produceHTMLvisualization(int[] clusters, int[] label, String datasetTitle) {

		StringBuilder head = new StringBuilder();
		head.append("<head>\n")
			.append("<title>" + datasetTitle + " Data set</title>\n")
			.append("<style>table, th, td {border: 1px solid black;}</style>\n")
			.append("</head>\n");

		StringBuilder body = new StringBuilder();
		int ctr = 0;
		body.append("<table>\n");
		for (int y = 0; y < height; y++) {
			body.append("<tr>");
			for (int x = 0; x < width; x++) {
				String color = ColorPicker.get( clusters[ctr] );
				body.append(color);
				body.append("<td style='color: " + color + "'>");
				body.append(label[ctr]);
				body.append("</td>");
				ctr++;
			}
			body.append("</tr>");
		}
		body.append("</table>");

		// Write the file
		File file = new File("warehouse/" + datasetTitle + "/" + datasetTitle + "-visualization.html");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("<html>" + head + body + "</html>");
			bw.close();
		} catch (IOException e) {
			System.out.println("Cannot write file " + file.getName() + ": " + e.getMessage());
		}
	}

	private static int[] storeInstanceLabels(LearningData fileData,
			LearningData labelledData) {
		int[] labels = new int[fileData.getDataSize()];

		for (int i = 0; i < fileData.getDataSize(); i++) {

			double[] f = labelledData.getData(i);
			labels[i] = (int) f[f.length - 1];
		}
		return labels;
	}

	private static double[] getLowestValues(double[] lowestValues,
			LearningData fileData, double[] distances, Stack<Integer> indexes,
			int g) {
		// Get the g lowest values and store their indexes
		for (int k = 0; k < fileData.getDataSize(); k++) {
			if (distances[k] < lowestValues[g - 1]) {
				indexes.push(k);
				lowestValues[g - 1] = distances[k];
				Arrays.sort(lowestValues);
			}
		}
		return lowestValues;
	}

	private static double[] getEuclideanDistances(double[] distances,
			LearningData fileData, EuclidesMetric metric,
			double[] weightOfCurrentNeuron) {
		// Get Euclidean distance of all instances from the current neuron
		for (int i = 0; i < fileData.getDataSize(); i++) {
			double[] d = fileData.getData(i);
			distances[i] = metric.getDistance(d, weightOfCurrentNeuron);
		}
		return distances;
	}

	private static int[] getLabels(Stack<Integer> indexes, int g, int[] labels) {
		int[] cl = new int[g];

		// Get the labels using the indexes
		for (int a = 0; a < g; a++) {
			cl[a] = labels[indexes.pop()];
		}
		return cl;
	}

	private static HashMap<Integer, Integer> getFrequencyCount(int[] cl) {
		// Frequency count
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();

		for (int a : cl) {
			Integer freq = m.get(a);
			m.put(a, (freq == null) ? 1 : freq + 1);
		}
		return m;
	}

	private static int getMostFrequent(Map<Integer, Integer> m) {
		int max = -1;
		int mostFrequent = -1;

		for (Map.Entry<Integer, Integer> b : m.entrySet()) {
			if (b.getValue() > max) {
				mostFrequent = b.getKey();
				max = b.getValue();
			}
		}
		return mostFrequent;
	}

}
