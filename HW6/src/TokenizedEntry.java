public class TokenizedEntry
{
	private String topic;
	private TokenizedText text;

	public TokenizedEntry(String rawLine)
	{
		int firstComma = rawLine.indexOf(',');
		this.topic = rawLine.substring(0, firstComma).toLowerCase();
		this.text = new TokenizedText(rawLine.substring(firstComma+1));
	}

	public String getTopic()
	{
		return this.topic;
	}

	public TokenizedText getText()
	{
		return this.text;
	}
}
