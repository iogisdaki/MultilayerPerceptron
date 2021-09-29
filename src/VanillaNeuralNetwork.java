public class VanillaNeuralNetwork {

	private double learningRate;
	private int numberOfLayers;
	private Layer[] layers;
	private int[] structure;

	public VanillaNeuralNetwork(int structure[], double learningRate) {
		if (structure.length < 3) throw new RuntimeException("Illegal network dimensions.The network must have at least an input, a hidden and an output layer");
		for (int i = 0; i < structure.length; i++)
			if (structure[i] < 1) throw new RuntimeException("Illegal layer dimensions.Each layer must have at least one neuron");

		this.learningRate = learningRate;
		this.structure = structure;
		numberOfLayers = structure.length;
		layers = new Layer[numberOfLayers];

		for (int i = 0; i < numberOfLayers; i++)
			layers[i] = (i == 0) ? new Layer(structure[i]) : new Layer(structure[i], structure[i - 1]);
	}

	public double[] feedforward(double[] inputArray) {
		if (inputArray.length != structure[0]) throw new RuntimeException("Illegal input dimensions.The input number must match the input layer neurons");

		for (int i = 0; i < numberOfLayers; i++)
			if (i == 0)
				layers[i].setNeurons(Matrix.fromArray(inputArray));
			else
				layers[i].calculateNeurons(layers[i - 1].getNeurons());
		Matrix output = layers[numberOfLayers - 1].getNeurons();
		return output.toArray();
	}

	public void train(double[] inputArray, double[] targetArray) {
		if (inputArray.length != structure[0]) throw new RuntimeException("Illegal input dimensions.The input number must match the input layer neurons");
		if (targetArray.length != structure[numberOfLayers - 1]) throw new RuntimeException("Illegal target dimensions.The target number must match the output layer neurons");

		Matrix targets = Matrix.fromArray(targetArray);

		//feedforward to calculate the values of the neurons of each layer for the givan input
		this.feedforward(inputArray);

		for (int i = numberOfLayers - 1; i > 0; i--)
			if (i == numberOfLayers - 1)
				layers[i].setErrors(Matrix.subtract(targets, layers[i].getNeurons()));
			else
				layers[i].calculateErrors(layers[i + 1].getWeights(), layers[i + 1].getErrors());

		for (int i = numberOfLayers - 1; i > 0; i--)
			layers[i].correctWeights(layers[i - 1].getNeurons(), learningRate);
	}
}
