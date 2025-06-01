import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Confusion Matrix, you do not need to understand this code.
 */
public class ConfusionMatrix {
    private Map<String, Map<String, Integer>> data = new HashMap<>();
    private static final String ACTUAL_LABEL = "V ACTUAL";

    public void storePrediction(String actual, String prediction) {
        actual = actual.toLowerCase();
        prediction = prediction.toLowerCase();

        this.data.putIfAbsent(actual, new HashMap<>());
        Map<String, Integer> actualCounts = this.data.get(actual);
        actualCounts.putIfAbsent(prediction, 0);
        actualCounts.put(prediction, actualCounts.get(prediction) + 1);
    }

    @Override
    public String toString() {
        Set<String> keys = this.data.keySet();
        int longestKeyLength = this.getLongestKeyLength();
        if (ACTUAL_LABEL.length() > longestKeyLength) longestKeyLength = ACTUAL_LABEL.length();

        Map<String, String> formats = this.getFormats();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-" + longestKeyLength + "s ", ACTUAL_LABEL));
        for (String key : keys) {
            sb.append(key);
            sb.append(" ");
        }
        sb.append("\n");

        for (String actual : keys) {
            sb.append(String.format("%-" + longestKeyLength + "s", actual));
            sb.append(" ");
            for (String pred : keys) {
                int v = this.data.get(actual).getOrDefault(pred, 0);
                sb.append(String.format(formats.get(pred), v));
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private Map<String, String> getFormats() {
        Map<String, String> ret = new HashMap<>();

        for (String key : this.data.keySet()) {
            ret.put(key, "%" + key.length() + "d");
        }
        return ret;

    }

    private int getLongestKeyLength() {
        return this
                .data
                .keySet()
                .stream()
                .mapToInt(s -> s.length())
                .max()
                .getAsInt()
                ;
    }
}
