import processing.core.PApplet;

public class Main extends PApplet {
    private static final int NO_CATEGORY_COLOR = 0xFFFFFF00;
    private static final int YES_CATEGORY_COLOR = 0xFFFF00FF;
    private static final int CORRECT_CLASSIFICAITON_COLOR = 0xFF00FF00;
    private static final int INCORRECT_CLASSIFICATION_COLOR = 0xFFFF0000;

    DataSet d;
    Perceptron nn;
    Display display;
    static String[] features = {"petal width", "sepal length"};
    int x, y;

    int currentIndex = 0;

    public void setup() {
        size(800, 800);

        String[] headers = {"sepal length", "sepal width", "petal length", "petal width", "class"};
        d = DataReader.createDataSetFromCSV("iris.data", 0, headers);

        nn = new Perceptron(2, "setosa");

        x = DataSet.getIndexForFeatureName(features[0]);
        y = DataSet.getIndexForFeatureName(features[1]);

        d.info();

        display = new Display(0, 0,
                width, height, d.getMinVal(x),
                d.getMinVal(y), d.getMaxVal(x),
                d.getMaxVal(y), 2f);
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

            String displayString = (guess == 1) ? "setosa" : "NOT setosa";
            System.out.println("Guessed: " + guess + " real: " + correctLabel);
        }

        System.out.println("Right: " + numRight + " / " + d.getData().size());
    }

    private static void runTrainingData(Perceptron nn, DataSet d) {
        for (int epochs = 0; epochs < 169; epochs++) {
            for (DataSet.DataPoint p : d.getData()) {
                String correctLabel = p.getLabelString();

                float[] input = p.getData(features);
                nn.train(input, correctLabel);
            }
        }
    }

    public void draw() {
        background(200);
        drawFullField(20);
        drawPoints();
        displayNNInfo(nn, 30, 30);
        //drawMouseInfo();
    }

    private void displayNNInfo(Perceptron nn, int x, int y) {
        float[] weights = nn.getWeights();
        float threshold = nn.getThreshold();

        String w1 = String.format("%.2f", weights[0]);
        String w2 = String.format("%.2f", weights[1]);
        String thresh = String.format("%.2f", threshold);
        String display = features[0] + "*" + w1 + " + "
                + features[1] + "*" + w2 + " >= " + thresh;

        strokeWeight(1);
        fill(255);
        rect(0, y - 24, width, 35);

        textSize(20);
        fill(0);
        stroke(0);
        text(display, x, y);
    }

    public void drawPoints() {
        for (DataSet.DataPoint point : d.getData()) {
            String label = point.getLabelString();

            int weight = 0;
            weight = 6;

            float[] inputs = point.getData(features);
            int guess = nn.guess(inputs);

            int color = (nn.isGuessCorrect(guess, label)) ? CORRECT_CLASSIFICAITON_COLOR : INCORRECT_CLASSIFICATION_COLOR;
            int stroke = (label.equals(nn.getTargetLabel())) ? YES_CATEGORY_COLOR : NO_CATEGORY_COLOR;
            display.plotDataCoords(this, point.getData(x), point.getData(y), 16, stroke, color, weight);
        }
    }

    private void drawFullField(int STEP) {
        for (int x = 0; x < width; x += STEP) {
            for (int y = 0; y < height; y += STEP) {
                float dx = display.screenXToData(x);
                float dy = display.sccreenYToData(y);

                int guess = nn.guess(new float[]{dx, dy});
                int color = (guess == 1) ? YES_CATEGORY_COLOR : NO_CATEGORY_COLOR;

                display.plotDataCoords(this, dx, dy, STEP / 2, color, color, 1);
            }
        }
    }

    public void mouseReleased() {
        boolean noChange = true;
        do {
            DataSet.DataPoint point = d.getData().get(currentIndex);
            String label = point.getLabelString();

            float[] inputs = {point.getData(x), point.getData(y)};
            noChange = !nn.train(inputs, point.getLabelString());

            currentIndex++;
            if (currentIndex >= d.getData().size())
                currentIndex = 0;
        } while (noChange);
    }

   public static void main(String[] args) {
        PApplet.main("Main");
    }
}