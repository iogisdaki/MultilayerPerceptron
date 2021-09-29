public class Layer {
    private Matrix neurons;
    private Matrix weights;
    private Matrix biases;
    private Matrix errors;

    Layer(int numberOfNeurons, int numberOfPreviousLayerNeurons) {
        neurons = new Matrix(numberOfNeurons, 1);
        weights = new Matrix(numberOfNeurons, numberOfPreviousLayerNeurons);
        biases = new Matrix(numberOfNeurons, 1);
        errors = new Matrix(numberOfNeurons, 1);

        weights.randomize();
        biases.randomize();
    }

    //input layer case where no weights are needed
    Layer(int numberOfNeurons) {
        neurons = new Matrix(numberOfNeurons, 1);
    }

    public void calculateNeurons(Matrix previousLayerNeurons) {
        neurons = Matrix.dotProduct(weights, previousLayerNeurons);
        neurons.add(biases);
        neurons.passThroughSigmoid();
    }

    public void calculateErrors(Matrix nextLayerWeights, Matrix nextLayerErrors) {
        errors = Matrix.dotProduct(Matrix.transpose(nextLayerWeights), nextLayerErrors);
    }

    public void correctWeights(Matrix previousLayerNeurons, double learningRate) {
        //calculate the delta weights 
        //dw = dot product of (lr * error * derivative of sigmoid of neurons) and (previous layer neurons transposed)
        Matrix gradient = neurons.derivativeOfSigmoid();
        gradient.hadamardMultiply(errors);
        gradient.scalarMultiply(learningRate);
        Matrix deltaWeights = Matrix.dotProduct(gradient, Matrix.transpose(previousLayerNeurons));

        weights.add(deltaWeights);
        biases.add(gradient);
    }

    public void setNeurons(Matrix value) {
        neurons = value;
    }

    public Matrix getNeurons() {
        return neurons;
    }

    public void setErrors(Matrix value) {
        errors = value;
    }

    public Matrix getErrors() {
        return errors;
    }
    public Matrix getWeights() {
        return weights;
    }
}
