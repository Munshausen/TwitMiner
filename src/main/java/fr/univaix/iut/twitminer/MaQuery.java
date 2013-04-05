package fr.univaix.iut.twitminer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class MaQuery {

	
	public static void search (String qwords, int maxresults) throws InterruptedException
	{
		try
		{
			Twitter twitter = new TwitterFactory().getInstance();
			
			int TweetCounter = 0;
			
			// La Query est l'�lement recherch�
			Query query = new Query(qwords);
			
			QueryResult result;
			
			// Cr�ation du fichier CSV
			java.io.File csvfile = new java.io.File("results.csv");
			
			// Est-ce que mon fichier existe ? Si non, le cr�er
			if (! csvfile.exists())
				csvfile.createNewFile();
			
			// Est-ce que je peux �crire dedans ? Si non, EXCEPTION !
			if (! csvfile.canWrite())
			{
				System.out.println("Le fichier results.csv ne peut etre �cirt !");
				System.exit(1);
			}
				
			OutputStreamWriter w_csv = new OutputStreamWriter ( new FileOutputStream (csvfile) );
			
			for (;TweetCounter < maxresults ;)
			{
				try
				{
						
					do
					{
						result = twitter.search(query);
						List<Status> tweets = result.getTweets();
						for (Status tweet : tweets)
						{
							w_csv.write(ConvertToCSV.print(tweet));
							w_csv.write("\n");
							// System.out.println(TweetToCSV.print(tweet));
							// System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
							++TweetCounter;
							if (TweetCounter%250 == 0)
								System.out.println("Tweets recus : "+TweetCounter);
						}
						
						// Attendre 3 secondes entre chaque requ�te pour �viter le flood
						Thread.sleep(3000);
					} while ((query = result.nextQuery()) != null);
					
				}
				catch (TwitterException te)
				{
					System.out.println("Erreur survenue au Tweet " + TweetCounter + ", pause du programme pendant 5 secondes");
					Thread.sleep(5000);
				}
			}
				
			w_csv.close();
			
			System.exit(0);
		}
		catch (IOException e)
		{
			System.out.println("Erreur avec le fichier .csv");
		}
	}
}
