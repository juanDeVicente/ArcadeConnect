package properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Properties
{
	public static Properties values = new Properties();

	private JSONObject propertiesObject;

	private Properties()
	{
		try(BufferedReader reader= Files.newBufferedReader(Paths.get("data/properties.json")))
		{
			JSONTokener tokener = new JSONTokener(reader);
			this.propertiesObject = new JSONObject(tokener);
		}
		catch(IOException e)
		{
			createDefaultProperties();
		}
	}

	public Object get(String key)
	{
		try
		{
			return this.propertiesObject.get(key);
		}
		catch(JSONException e)
		{
			this.createDefaultProperties();
			return this.get(key);
		}
	}
	public void set(String key, Object value)
	{
		this.propertiesObject.put(key, value);
	}
	public void save()
	{
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("data/properties.json")))
		{
			this.propertiesObject.write(writer);
			writer.write("\n");
		}
		catch (IOException e)
		{
			createDefaultProperties();
		}
	}

	private void createDefaultProperties()
	{
		this.propertiesObject = new JSONObject();
		this.set("maximized", false);
		this.set("width", 1600);
		this.set("height", 900);
		if (!Files.exists(Paths.get("data/")))
		{
			try
			{
				Files.createDirectory(Paths.get("data/"));
				Files.createFile(Paths.get("data/properties.json"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if (!Files.exists(Paths.get("data/properties.json")))
		{
			try
			{
				Files.createFile(Paths.get("data/properties.json"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		this.save();
	}
}
