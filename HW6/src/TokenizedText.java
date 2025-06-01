import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TokenizedText
{
	private List<String> tokens = new ArrayList<>();
	private static final List<String> FILTER_OUT = Arrays.asList(
		"\"",
		"\\.",
		","
	);

	public TokenizedText(String text)
	{
		for (String f: FILTER_OUT)
		{
			text = text.replaceAll(f, "");
		}

		for (String token: text.split(" "))
		{
			this.processToken(token);
			if (token == "") continue;

			this.tokens.add(token);
		}
	}

	private String processToken(String token)
	{
		return token.strip();
	}

	public List<String> getTokens()
	{
		return Collections.unmodifiableList(this.tokens);
	}
}
