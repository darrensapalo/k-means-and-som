import java.util.ArrayList;

import kohonen.LearningData;

public class Driver {
	public enum DataSet 
	{
		TREE, GENERAL_AND_TECH
	}
	
	
	public static void main(String[] args) {
		String datasetTitle = "";
		int width = 1, height = 1, inputs = 1, maxIterations = 10, k = 4;
		
		LearningData fileData = null;
		LearningData labelledData = null;

		ArrayList<ConfigSet> configs = new ArrayList<ConfigSet>();
		
		configs.add(new ConfigSet(10, 10, 20));
		configs.add(new ConfigSet(16, 16, 20));
		
		configs.add(new ConfigSet(10, 10, 50));
		configs.add(new ConfigSet(16, 16, 50));
		
		configs.add(new ConfigSet(10, 10, 100));
		configs.add(new ConfigSet(16, 16, 100));

		DataSet useDataset = DataSet.GENERAL_AND_TECH;
		for (int i = 0; i < 2; i++){
			ArrayList<ConfigSet> configsInstance = (ArrayList<ConfigSet>)configs.clone();
			
			for (ConfigSet set : configsInstance){
				System.out.println("Doing " + set.width + " by "  + set.height + " with " + set.maxIterations + " iterations.");
				
				switch(useDataset){
				case TREE:
					datasetTitle = "Tree";
					width = 10;
					height = 10;
					inputs = 54;
					k = 7;
					maxIterations = 20;
					
				break;
				case GENERAL_AND_TECH:
					datasetTitle = "GenAndTech";
					width = 10;
					height = 10;
					inputs = 567;
					k = 4;
					maxIterations = 20;
					break;
				}
				
				
				// use configs
				width = set.width;
				height = set.height;
				maxIterations = set.maxIterations;
				

				fileData = new LearningData("warehouse/" + datasetTitle + "/" + datasetTitle + "DataSet.csv");
				labelledData = new LearningData("warehouse/" + datasetTitle + "/" + datasetTitle + "LabelledDataSet.csv");
				
				new Visualization(datasetTitle, width, height, fileData, labelledData, inputs, maxIterations, k);
				System.out.println("Done");
			}
			useDataset = DataSet.GENERAL_AND_TECH;
		}
		
		

	}
	
	
}
