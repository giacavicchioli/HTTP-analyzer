

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Bootstrapper {


	public static void main(String[] args) {
		BufferedReader br;
		String plainUrl;
		String method;
		String aux;
		boolean showBody;

		URL url;
		HttpURLConnection connection = null;

		System.out.println("--- HTTP header examiner ---");

		br = new BufferedReader(new InputStreamReader(System.in));

		try {

			//choosing url
			System.out.println("Select URL:");
			plainUrl = br.readLine();
			System.out.println("URL: " + plainUrl);

			//choosing methods
			System.out.println("Select method (GET/POST/DELETE):");
			method = br.readLine();
			while(!method.equals("GET") && !method.equals("POST") && !method.equals("DELETE"))	{
				System.out.println(method + " not allowed!\nSelect method (GET/POST): ");
				method = br.readLine();
			}
			System.out.println("method: " + method);

			//choosing show show body
			System.out.println("Show body response (y/n):");
			aux = br.readLine();
			while(!aux.equals("y") && !aux.equals("n"))	{
				System.out.println(aux + " not allowed!\nShow body response (y/n): ");
				aux = br.readLine();
			}
			showBody = aux.equals("y");
			if(showBody)
				System.out.println("Showing response body");
			else
				System.out.println("Not showing response body");

			//Create connection
			url = new URL(plainUrl);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			//Get Response
			Map<String, List<String>> map = connection.getHeaderFields();

			System.out.println("Printing Response Header...\n");

			for (Map.Entry<String, List<String>> entry : map.entrySet())
				System.out.println(entry.getKey() + ": " + entry.getValue());

			System.out.println("\nDone");

			if(showBody)	{
				System.out.println("\nPrinting Response Body...\n");

				BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer response = new StringBuffer();
				while((line = rd.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				rd.close();
				System.out.println(response);

				System.out.println("\nDone)");
			}


		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());

		} finally	{
			if(connection != null)
				connection.disconnect();
		}


	}

}
