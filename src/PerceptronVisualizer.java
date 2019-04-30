import java.util.Collections;

import processing.core.PApplet;

public class PerceptronVisualizer extends PApplet {
	DataSetViz dataViz;
	DataSet data;
	int rowHighlight = 0;
	DataSet.DataPoint current;
	Perceptron nn;

	public void setup() {
		size(1200, 900);

		String[] headers = { "sepal length", "sepal width", "petal length", "petal width", "class" };
		data = DataReader.createDataSetFromCSV("iris.data", 0, headers);

		Collections.shuffle(data.getData());
		dataViz = new DataSetViz(data, 0, 0, 300, height);

		dataViz.setRowHighLight(rowHighlight);
		current = data.getData().get(rowHighlight);

		nn = new Perceptron(2, "setosa");
	}

	public void draw() {
		background(200);
		drawPerceptron();
		dataViz.draw(this);
		drawPredictions();
	}

	private void drawPredictions() {
		int n = dataViz.getN();
		for (int i = 0; i < n; i++) {
			DataSet.DataPoint p = data.getData().get(i);
			float[] input = {p.getData(0), p.getData(1)};
			
			int guess = nn.guess(input);

			int color = (guess == 1 && nn.getCorrectGuess(p.getLabelString())==1) ? color(0, 255, 0) : color(255, 0, 0);

			fill(color);
			ellipse(200, 20 + (i-1)*40, 20, 20);
		}
	}

	private void drawPerceptron() {
		int centerX = (int) (2 * 900 / 3.0f);
		int centerY = (int) (height / 2.0f);
		stroke(0);

		line(centerX - 50, centerY, centerX - 150, centerY - 200);
		line(centerX + 50, centerY, centerX + 150, centerY - 200);

		textSize(30);
		fill(color(255, 255, 0));
		text(format(current.getData(0)), centerX - 150 - 50, centerY - 200);
		text(format(current.getData(1)), centerX + 150 - 50, centerY - 200);

		fill(0);
		textSize(20);
		float[] weights = nn.getWeights();
		text("w0: " + format(weights[0]), centerX - 125 - 25, centerY - 100);
		text("w1: " + format(weights[1]), centerX + 125 - 65, centerY - 100);

		float result = current.getData(0) * weights[0] + current.getData(1) * weights[1];
		String displayString = format(current.getData(0)) + " * " + format(weights[0]);
		displayString += " + " + format(current.getData(1)) + " * " + format(weights[1]);
		displayString += "\n                 = " + format(result);

		line(centerX, centerY, centerX, centerY + 150);

		fill(255);
		ellipse(centerX, centerY, 300, 100);

		fill(0);
		text(displayString, centerX - 130, centerY);

		int guess = nn.guess(new float[] { current.getData(0), current.getData(1) });

		textSize(40);
		int color = (guess == 1 && nn.getCorrectGuess(current.getLabelString())==1) ? color(0, 180, 0) : color(255, 0, 0);
		fill(color);
		String dispString = (guess == 1)?nn.getTargetLabel():"not " + nn.getTargetLabel();
		text(guess + " : " + dispString, centerX - 60, centerY + 200);
	}

	private String format(float f) {
		return String.format("%.2f", f);
	}

	public void keyReleased() {
		if (key == CODED) {
			if (keyCode == UP) {
				if (rowHighlight > 0)
					rowHighlight--;
				dataViz.setRowHighLight(rowHighlight);
				System.out.println("getting " + rowHighlight);
				current = data.getData().get(rowHighlight);
			}
			if (keyCode == DOWN) {
				if (rowHighlight < dataViz.getN())
					rowHighlight++;
				dataViz.setRowHighLight(rowHighlight);
				System.out.println("getting " + rowHighlight);
				current = data.getData().get(rowHighlight);
			}
		}
		
		if (key == 't') {
			float[] input = {current.getData(0), current.getData(1)};
			nn.train(input, current.getLabelString());
		}
	}

	public static void main(String[] args) {
		PApplet.main("PerceptronVisualizer");
	}
}