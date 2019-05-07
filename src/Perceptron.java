public class Perceptron {
    private int numInputs;
    private float[] weights;
    private float THRESHOLD = 0;

    private String classifyForLabel; // this is a classifier for eg "virginica"
    private float learningRate = 0.005f;

    public Perceptron(int numInputs, String whatToClassify) {
        this.classifyForLabel = whatToClassify;
        this.numInputs = numInputs;
        weights = initWeights(numInputs);
    }

    private float[] initWeights(int numInputs) {
        weights = new float[numInputs];

        for (int i = 0; i < numInputs; i++) {
            weights[i] = 0;//(float)(Math.random()*0.2);
        }

        return weights;
    }

    /***
     * Train the perceptron using the input feature vector and its correct label.
     * Return true if there was a non-zero error and training occured (weights got adjusted)
     *
     * @param input
     * @param correctLabel
     * @return
     */
    public boolean train(float[] input, String correctLabel) {
        int guess = guess(input);
        System.out.println(guess + " " + correctLabel);

        if (!isGuessCorrect(guess, correctLabel)) {
            float actual = getCorrectGuess(correctLabel);
            float error = guess - actual;
            for (int i = 0; i < weights.length; i++) {
                weights[i] -= error * input[i] * learningRate;
            }
            THRESHOLD -= error * learningRate;

            return true;
        }
        // run the perceptron on the input
        // compare the guess with the correct label (can use already-made helper method for this).

        // If guess was incorrect
        //    update weights and THRESHOLD using learning rule

        return false;
    }

    public int guess(float[] input) {
        // TODO:  Implement this.
        // Do a linear combination of the inputs multiplied by the weights.
        // Run the sum through the activiationFunction and return the result
        float sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += weights[i] * input[i];
        }
        return activationFunction(sum);
    }

    private int activationFunction(float sum) {
        if (sum > THRESHOLD) {
            return 1;
        } else {
            return 0;
        }
    }

    public float[] getWeights() {
        return weights;
    }

    public String getTargetLabel() {
        return this.classifyForLabel;
    }

    public boolean isGuessCorrect(int guess, String correctLabel) {
        return guess == getCorrectGuess(correctLabel);
    }

    /***
     * Return the correct output for a given class label.  ie returns 1 if input label matches
     * what this perceptron is trying to classify.
     * @param label
     * @return
     */
    public int getCorrectGuess(String label) {
        if (label.equals(this.classifyForLabel))
            return 1;
        else
            return 0;
    }

    public float getThreshold() {
        return THRESHOLD;
    }
}