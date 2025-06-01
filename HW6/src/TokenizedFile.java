import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenizedFile
{
	private List<TokenizedEntry> data = new ArrayList<>();

	public TokenizedFile(String filepath)
	{
		File f = new File(filepath);
		try
		(
			BufferedReader br = new BufferedReader(new FileReader(f));
		)
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				this.data.add(new TokenizedEntry(line));
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public List<TokenizedEntry> getEntries()
	{
		return Collections.unmodifiableList(this.data);
	}
}
