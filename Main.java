import java.util.regex.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class Main {
	private Map<String, Boolean> sites = new HashMap<String, Boolean>();
	public static int entriesTried = 0;
	public static int entries = 0;
	
	public Main() {;}
	
	// Adds a string address to the map if it's a new address
	public void addAddress(String a) {
		if (sites.get(a) == null) {
			sites.put(a, false);
			entries++;
		}
	}
	
	// returns the next unused URL or null if none remain.
	public String nextURL() {
		for (Map.Entry<String, Boolean> entry : sites.entrySet()) {
			if (!entry.getValue()) {
				entry.setValue(true);
				return entry.getKey();
			}				
		}
		return null;
	}
	
	// opens the url, sifts through it with Regex, then adds to sites map
	public void tryURL(String u) {
		try {
			URL url = new URL(u);
			BufferedReader rdr = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = rdr.readLine();
            while (line != null) {
            	Pattern newURL = Pattern.compile("<a\\s*?href=\"(http:.*?)\"");
            	Matcher matcher = newURL.matcher(line);
            	if (matcher.find())
            		this.addAddress(matcher.group(1));
            	line = rdr.readLine();
            }
		}
		catch (Exception ex) { System.out.printf("Whoops! %s\n", ex.toString()); }
		entriesTried++;
	}
	
	// Lists all sites
	public void listTried() {
		System.out.println("Sites checked:");
		for (Map.Entry<String, Boolean> entry : sites.entrySet()) {
			//if (entry.getValue())
				System.out.println(entry.getKey());
		}
	}
	
	// Main function
	public static void main(String[] args) {
		Main m = new Main();
		m.addAddress("http://www.espn.com/");
		String site;
		do {
			site = m.nextURL();
			m.tryURL(site);
		} while (Main.entriesTried <= 100 && site != null);
		
		m.listTried();
	}
}
