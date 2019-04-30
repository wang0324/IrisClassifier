import processing.core.PApplet;

public class DataSetViz {
	private static final int TEXT_HEIGHT = 20;
	private static final int VERT_BUFFER = 10;
	private static final int ROW_HEIGHT = TEXT_HEIGHT + 2 * VERT_BUFFER;
	private DataSet data;
	private int x1, y1; // (x1, y1) upper left for display
	private int x2, y2; // (x2, y2) lower right for display
	private int n; // num points to display
	private int width;
	private int height;
	private int rowHighlight = -1;

	public DataSetViz(DataSet data, int x1, int y1, int x2, int y2) {
		this.data = data;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;

		this.width = (x2 - x1);
		this.height = (y2 - y1);

		this.n = height / ROW_HEIGHT;
	}

	public void draw(PApplet window) {
		window.textSize(TEXT_HEIGHT);

		for (int num = 0; num < n; num++) {
			rect(window, x1, y1 + num * ROW_HEIGHT);

			window.stroke(0);
			window.fill(0);
			String text = data.getData().get(num).toString();
			window.text(text, x1 + 5, y1 + num * ROW_HEIGHT - VERT_BUFFER);
		}

		highLightRow(window, rowHighlight);
	}

	private void highLightRow(PApplet window, int rowHighlight) {
		window.fill(window.color(255, 255, 255, 0));
		window.stroke(window.color(255, 255, 0));
		window.strokeWeight(3);
		window.rect(x1, y1 + (rowHighlight - 1) * ROW_HEIGHT, width, ROW_HEIGHT);
		window.strokeWeight(1);
	}

	private void rect(PApplet window, int x, int y) {
		window.fill(window.color(255, 255, 255, 0));
		window.stroke(0);
		window.strokeWeight(1);

		window.rect(x, y, width, ROW_HEIGHT);
	}

	public void setRowHighLight(int row) {
		if (row < 0 || row > n) {
			rowHighlight = -1;
		} else {
			rowHighlight = row;
		}
	}

	public int getN() {
		return n;
	}
}
