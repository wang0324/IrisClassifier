import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSet {
    private ArrayList<DataPoint> points;
    private DataPoint minValsPoint, maxValsPoint;  // NOT part of data set.
    // points store min/vax values

    // TODO:  HACK HACK HACK!  Did this to fix training, but it means we can only have ONE data set per program =(
    static private String[] columnFeatureNames;                    // name for each feature
    static private ArrayList<String> labels = new ArrayList<>();

    public DataSet(String[] columnFeatureNames) {
        points = new ArrayList<DataPoint>();
        this.columnFeatureNames = columnFeatureNames;

        maxValsPoint = new DataPoint(new float[]{Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE},
                "");
        minValsPoint = new DataPoint(new float[]{Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE},
                "");
    }

    public static String getFeatureNameForIndex(int c) {
        if (c < 0 || c >= columnFeatureNames.length) return null;
        return columnFeatureNames[c];
    }

    public int countNumWithLabel(String label) {
        int count = 0;
        for (DataPoint p : getData()) {
            if (p.getLabelString().equals(label)) count++;
        }

        return count;
    }

    public static int getIndexForFeatureName(String label) {
        for (int i = 0; i < columnFeatureNames.length; i++) {
            if (columnFeatureNames[i].equals(label)) {
                return i;
            }
        }
        return -1;
    }

    public static int getClassForLabel(String labelName) {
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).equals(labelName)) return i;
        }

        return -1;
    }

    public void add(float[] vals, String label) {
        DataPoint p = new DataPoint(vals, label);

        addLabel(label);

        // Set new min/max values if necessary
        for (int i = 0; i < p.getData().length; i++) {
            float val = p.getData(i);
            if (val > maxValsPoint.getData(i))
                maxValsPoint.setData(i, val);
            if (val < minValsPoint.getData(i))
                minValsPoint.setData(i, val);
        }

        points.add(p);
    }

    private void addLabel(String label) {
        if (!labels.contains(label)) {
            labels.add(label);
        }
    }

    public float getMinVal(int i) {
        return minValsPoint.getData(i);
    }

    public float getMaxVal(int i) {
        return maxValsPoint.getData(i);
    }

    public List<DataPoint> getData() {
        return points;
    }

    public int size() {
        return points.size();
    }

    public String toString() {
        String ret = "Points: " + points.size();
        ret += "\n\n" + points.toString();
        ret += "\n------------------------\n";
        ret += "Max: " + this.maxValsPoint + "\nMin: " + this.minValsPoint + "\n";

        return ret;
    }

    public void info() {
        System.out.println("Data points: " + points.size());
        System.out.println("Features: " + Arrays.toString(DataSet.columnFeatureNames));
        System.out.println("Min vals: " + this.minValsPoint);
        System.out.println("Max vals: " + this.maxValsPoint);

    }

    public class DataPoint {
        private float[] data;
        private String label;

        public DataPoint(float[] data, String label) {
            if (data.length != columnFeatureNames.length) {
                System.err.println("WARNING: adding point with data length different from data set labels");
            }

            this.data = data;
            this.label = label;
        }

        public float[] getData() {
            return data;
        }

        public float getData(String name) {
            return data[getIndexForFeatureName(name)];
        }

        public float[] getData(String ... columns) {
            float[] retdata = new float[columns.length];

            for (int i = 0; i < columns.length; i++) {
                retdata[i] = data[getIndexForFeatureName(columns[i])];
            }

            return retdata;
        }

        private void setData(float[] data) {
            this.data = data;
        }

        public String getLabelString() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public float getData(int i) {
            return data[i];
        }

        public void setData(int i, float val) {
            data[i] = val;
        }

        public String toString() {
            return Arrays.toString(data) + ": " + label + "\n";
        }
    }
}