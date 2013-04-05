package fr.univaix.iut.twitminer;
import twitter4j.Status;

public class ConvertToCSV{
	
	public static String print(Status tweet) 
	{
		// Recupére les mots d'un tweet qui sont séparé par un espace.
		String[] TabMots = tweet.getText().split(" "); 
		
		// Convertir le texte en forme CSV
		String finaltext = new String();
		for (int i = 0; i < TabMots.length ; ++i)
			finaltext += "\"" + TabMots[i] + "\";";
		
		// Retrait du dernier ";"
		finaltext = finaltext.substring(0, finaltext.length()-1);
		
		// Eviter l'exception du getCountry() == null
		String country = new String();
		
		if (tweet.getPlace() != null)
			country = tweet.getPlace().getCountry();
		
		// Forme du CSV : "Date";"User";"Country";"Text1";"Text2"...
		return "\"" + tweet.getCreatedAt() + "\";\"@" + tweet.getUser().getName() + "\";\"" + country + "\";" + finaltext;
	}

}
