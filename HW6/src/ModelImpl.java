import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelImpl implements Model {
	private int totalArticles = 0;
	private int vocabularySize = 0;
	private Map<String, Integer> topicFrequencies = new HashMap<>();
	private Map<String, Integer> totalWordCount = new HashMap<>();
	private Map<String, Map<String, Integer>> wordCounts = new HashMap<>();


	@Override
	public void train(TokenizedFile trainingSet) {
		for (TokenizedEntry entry : trainingSet.getEntries()) {
			String topic = entry.getTopic();
			List<String> words = entry.getText().getTokens();
			topicFrequencies.put(topic, topicFrequencies.getOrDefault(topic, 0) + 1);
			totalArticles++;
			wordCounts.putIfAbsent(topic, new HashMap<>());
			int totalWordsInTopic = totalWordCount.getOrDefault(topic, 0);

			for (String word : words) {
				wordCounts.get(topic).put(word, wordCounts.get(topic).getOrDefault(word, 0) + 1);
				totalWordsInTopic++;
			}
			totalWordCount.put(topic, totalWordsInTopic);
		}
		vocabularySize = (int) wordCounts.values().stream()
				.flatMap(map -> map.keySet().stream())
				.distinct()
				.count();
	}


	@Override
	public String predict(TokenizedText query) {
		String bestTopic = null;
		double bestLogProbability = Double.NEGATIVE_INFINITY;
		for (String topic : topicFrequencies.keySet()) {
			double logProbability = Math.log((double) topicFrequencies.get(topic) / totalArticles);
			for (String word : query.getTokens()) {
				int wordFrequency = wordCounts.getOrDefault(topic, new HashMap<>()).getOrDefault(word, 0);
				int totalWordsInTopic = totalWordCount.getOrDefault(topic, 0);
				double wordProbability = (double) (wordFrequency + 1) / (totalWordsInTopic + vocabularySize);
				logProbability += Math.log(wordProbability);
			}
			if (logProbability > bestLogProbability) {
				bestLogProbability = logProbability;
				bestTopic = topic;
			}
		}
		return bestTopic;
	}

}
