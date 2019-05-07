public class RunMe {
    public static final String WHAT_TO_CLASSIFY = "virginica";
    public static final String[] features = {"sepal length", "sepal width", "petal length", "petal width"};
    public static void main(String[] args) {
        DataSet dataset;
        Perceptron nn;

        String[] headers = {"sepal length", "sepal width", "petal length", "petal width", "class"};
        dataset = DataReader.createDataSetFromCSV("iris.data", 0, headers);

        int numInputs = features.length;
        nn = new Perceptron(numInputs, WHAT_TO_CLASSIFY);

        runTrainingData(nn, dataset);
        testPerceptronOnData(nn, dataset);
    }

    private static void testPerceptronOnData(Perceptron nn, DataSet d) {
        int numRight = 0;
        for (DataSet.DataPoint p : d.getData()) {
            String correctLabel = p.getLabelString();

            float[] input = p.getData(features);
            int guess = nn.guess(input);

            if (nn.isGuessCorrect(guess, correctLabel)) {
                numRight++;
            }

            String displayString = (guess == 1) ? WHAT_TO_CLASSIFY : "NOT " + WHAT_TO_CLASSIFY;
            System.out.println("Guessed: " + displayString + "\t\t real: " + correctLabel);
        }

        System.out.println("Right: " + numRight + " / " + d.getData().size());
    }

    private static void runTrainingData(Perceptron nn, DataSet d) {
        for (int epochs = 0; epochs < 500; epochs++) {
            for (DataSet.DataPoint p : d.getData()) {
                String correctLabel = p.getLabelString();
                float[] input = p.getData(features);

                nn.train(input, correctLabel);
            }
        }
    }
}