package kmeans;

import network.DefaultNetwork;
import network.NeuronModel;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class NeuronsToInstance {

    
    public Instances convert(DefaultNetwork network, String datasetName, int inputs) {
        
        Instances instances = new Instances(datasetName,
            createAttributes(inputs), network.getNumbersOfNeurons());

        for (int i = 0; i < network.getNumbersOfNeurons(); i++) {
            NeuronModel neuron = network.getNeuron(i);
            Instance instance = new Instance(inputs);

            double[] weights = neuron.getWeight();

            for (int j = 0; j < weights.length; j++) {
                instance.setValue(j, weights[j]);
            }

            instances.add(instance);
        }

        return instances;
    }

    protected FastVector createAttributes(int inputs) {
        FastVector attributes = new FastVector(inputs);
        for(int i = 1; i <= inputs; i++){
            Attribute currSegmentAttribute = new Attribute("Segment"+i);
            attributes.addElement(currSegmentAttribute);
        }
        
        return attributes;
    }
}

