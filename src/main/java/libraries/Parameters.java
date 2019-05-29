package libraries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;
import org.json.simple.JSONObject;

public class Parameters 
{
	Hashtable<String, String> testData = new Hashtable<String, String>();

	public Parameters() 
	{
		preferences();

		if (GetParameters("Aura_Update").equalsIgnoreCase("YES")) 
		{
			System.out.println("------------------------------------");
			System.out.println("ALM and JIRA Update enabled");
			System.out.println("------------------------------------");
		}
		else 
		{
			System.out.println("------------------------------------");
			System.out.println("ALM and JIRA Update disabled");
			System.out.println("------------------------------------");
		}
	}

	public String GetParameters(String key) 
	{
		if (testData.containsKey(key.toUpperCase())) 
		{
			String rVal = testData.get(key.toUpperCase());
			
			if(!key.equalsIgnoreCase("Continued_Execution") && !key.equalsIgnoreCase("ScreenShotPath"))
				System.out.println("GetParameters : Key : " + key + " ## Value : " + rVal );
			
			return rVal;
		}
		else 
		{
			return "";
		}
	}

	public void SetParameters(String key, String keyValue) 
	{
		if (!key.equals(null) && !key.equals("") /*&& !keyValue.equals(null) && !keyValue.equals("")*/) 
		{
			testData.put(key.toUpperCase(), keyValue);
		}
	}

	public void hashToString() 
	{
		SetParameters("TESTDATA", testData.toString());
	}

	@SuppressWarnings("unchecked")
	public String mapToJSON() 
	{
		JSONObject jobj = new JSONObject();
		jobj.putAll(testData);
		return jobj.toString();
	}

	public void preferences() 
	{
		BufferedReader br;
		try 
		{
			br = new BufferedReader(new FileReader("C:\\CCM\\Preference\\Preferences_CPE.txt"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) 
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}

			SetParameters("Preferences", sb.toString());
			br.close();

			Json_Parsor();
		} 
		catch (Exception e) 
		{
			System.out.println("Exception Caught :" + e);
		}
	}

	public void Json_Parsor() 
	{
		String s = new String(GetParameters("Preferences"));
		String[] Array = s.split("\n");
		int len = Array.length;

		for (int i = 2; i <= len - 3; i++) 
		{
			String[] ex = Array[i].split(":");
			// System.out.println(ex[0] + "=");

			String[] ex2 = ex[1].split(",");
			// System.out.println(ex2[0]);

			SetParameters(ex[0].replace("\"", " ").trim(), ex2[0].replace("\"", " ").trim());
		}
	}
}
