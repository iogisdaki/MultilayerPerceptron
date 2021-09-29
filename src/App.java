import java.util.concurrent.ThreadLocalRandom;

public class App {
    public static void main(String[] args) throws Exception {

        // create an array and provide the number of nodes in each layer
        // this example has 2 input nodes, 3 hidden nodes in the first hidden layer, 4 in the second hidden layer, 1 output nodes 
        // you can have multiple hidden layers
        int structure[] = {2, 3, 4, 1};

        VanillaNeuralNetwork vanilla = new VanillaNeuralNetwork(structure, 0.01);

        //training and solving a simple XOR
        double[][] trainingInput = 
        {
            {1, 1},
            {0, 0},
            {0, 1},
            {1, 0}
        };

        double[][] trainingTargets =  
        {
            {0},
            {0},
            {1},
            {1},
        };

        for (int j = 0; j < 50000; j++) {
            for (int i = 0; i < trainingInput.length; i++) {
                // nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
                vanilla.train(trainingInput[randomNum], trainingTargets[randomNum]);
            }
        }

        for (int j = 0; j < trainingInput.length; j++) {
            double[] output = vanilla.feedforward(trainingInput[j]);
            for (int i = 0; i < structure[structure.length - 1]; i++)
                System.out.println(output[i]);
            System.out.println();
        }
    }
}