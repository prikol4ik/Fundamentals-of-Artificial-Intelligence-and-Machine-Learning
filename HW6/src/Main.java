public class Main
{
	public static void main(String[] args)
	{
		TokenizedFile trainSet = new TokenizedFile("ex5/bbc_train.csv");
		TokenizedFile testSet = new TokenizedFile("ex5/bbc_test.csv");

		Model m = new ModelImpl();

		m.train(trainSet);

		ConfusionMatrix confucius = new ConfusionMatrix();

		for (TokenizedEntry entry: testSet.getEntries())
		{
			String correctAnswer = entry.getTopic();
			TokenizedText text = entry.getText();
			String prediction = m.predict(text);
			confucius.storePrediction(correctAnswer, prediction);
		}

		System.out.println(confucius);
	}
}
