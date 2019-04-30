import processing.core.PApplet;

public class Display {
	private float screenXMin, screenYMin, screenXMax, screenYMax;
	private float dataXMin, dataXMax, dataYMin, dataYMax;

	public Display(float sx1, float sy1, float sx2, float sy2, float dxmin, float dymin, float dxmax, float dymax, float scale) {
        float xmid = (dxmin+dxmax)/2.0f;
        float ymid = (dymin+dymax)/2.0f;
		dxmin = scale(dxmin, xmid, scale);
        dxmax = scale(dxmax, xmid, scale);
        dymin = scale(dymin, ymid, scale);
        dymax = scale(dymax, ymid, scale);

		setBounds(sx1, sy1, sx2, sy2, dxmin, dymin, dxmax, dymax);
	}

    private float scale(float dxmin, float xmid, float scale) {
        return (dxmin - xmid)*scale + xmid;
    }

    public void setBounds(float sx1, float sy1, float sx2, float sy2, float dxmin, float dymin, float dxmax,
						  float dymax) {
		this.screenXMin = sx1;
		this.screenYMin = sy1;
		this.screenXMax = sx2;
		this.screenYMax = sy2;

		this.dataXMin = dxmin;
		this.dataYMin = dymin;
		this.dataXMax = dxmax;
		this.dataYMax = dymax;
	}

	public static float map(float val, float s1, float e1, float s2, float e2) {
		return ((val - s1) / (e1 - s1)) * (e2 - s2) + s2;
	}

	public float dataXToScreen(float dx) {
		return map(dx, dataXMin, dataXMax, screenXMin, screenXMax);
	}

	public float dataYToScreen(float dy) {
		return map(dy, dataYMin, dataYMax, screenYMin, screenYMax);
	}

	public float screenXToData(float sx) {
		return map(sx, screenXMin, screenXMax, dataXMin, dataXMax);
	}

	public float sccreenYToData(float sy) {
		return map(sy, screenYMin, screenYMax, dataYMin, dataYMax);
	}

	public boolean plotScreenCoords(PApplet window, float x, float y, float r, int stroke, int fill, int strokeWeight,
									boolean okToPlotOutOfBounds) {

		if (outOfBounds(x, y) && !okToPlotOutOfBounds)
			return false;

		window.stroke(stroke);
		window.fill(fill);
		window.strokeWeight(strokeWeight);

		window.ellipse(x, y, r, r);

		return true;
	}

	private boolean outOfBounds(float x, float y) {
		return (x < screenXMin || x > screenXMax || y < screenYMin || y > screenYMax);
	}

	public boolean plotDataCoords(PApplet window, float x, float y, float r, int stroke, int fill, int strokeWeight,
								  boolean okToPlotOutOfBounds) {

		float sx = dataXToScreen(x);
		float sy = dataYToScreen(y);

		return plotScreenCoords(window, sx, sy, r, stroke, fill, strokeWeight, okToPlotOutOfBounds);
	}

	public void plotScreenCoords(PApplet window, float x, float y, float r, int stroke, int fill, int strokeWeight) {
		plotScreenCoords(window, x, y, r, stroke, fill, strokeWeight, false);
	}

	public void plotDataCoords(PApplet window, float x, float y, float r, int stroke, int fill, int strokeWeight) {
		plotDataCoords(window, x, y, r, stroke, fill, strokeWeight, false);
	}
}