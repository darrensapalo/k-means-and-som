import kohonen.LearningData;

public class Driver {
	public static void main(String[] args) {
		String datasetTitle = "TreeDataset";
		int width = 10, height = 10, inputs = 54, maxIterations = 100;

		// Create LearningDataModel
		LearningData fileData = new LearningData("SOMtest.csv");
		LearningData labelledData = new LearningData("LabelledDataset.csv");

		new Visualization(datasetTitle, width, height, fileData, labelledData, inputs, maxIterations);

	}
}
