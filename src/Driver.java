import kohonen.LearningData;

public class Driver {
	public enum DataSet 
	{
		TREE, GENERAL_AND_TECH
	}
	public static void main(String[] args) {
		String datasetTitle = "";
		int width = 1, height = 1, inputs = 1, maxIterations = 10;
		
		LearningData fileData = null;
		LearningData labelledData = null;

		DataSet useDataset = DataSet.TREE;

		switch(useDataset){
		case TREE:
			datasetTitle = "Tree";
			width = 10;
			height = 10;
			inputs = 54;
			maxIterations = 100;
			
		break;
		case GENERAL_AND_TECH:
			datasetTitle = "GenAndTech";
			width = 10;
			height = 10;
			inputs = 567;
			maxIterations = 100;
			break;
		}
		

		fileData = new LearningData("warehouse/" + datasetTitle + "/" + datasetTitle + "DataSet.csv");
		labelledData = new LearningData("warehouse/" + datasetTitle + "/" + datasetTitle + "LabelledDataSet.csv");
		
		new Visualization(datasetTitle, width, height, fileData, labelledData, inputs, maxIterations);
		System.out.println("Done");
	}
	
	
}
