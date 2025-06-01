public interface Model
{
	/**
	 * This method should implement \a training of the model.
	 *
	 * By training it is understood that you calculate
	 * prior and conditional probabilities from bbc_train.csv file.
	 */
	public void train(TokenizedFile trainingSet);

	/**
	 * Predict the topic of the tokenized text.
	 *
	 * By predict it is understood that you
	 * calculate the hypothesis for each topic
	 * and pick the most probable one.
	 */
	public String predict(TokenizedText query);
}
