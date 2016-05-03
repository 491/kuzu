import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.mysql.jdbc.StringUtils;


public class WebCrawler {
	// queue and set are needed for BFS
	public static Queue<String> queue = new LinkedList<String>();
	public static HashSet<String> marked = new HashSet<String>();
	public static DataBaseConnect db = new DataBaseConnect();

	public static void main(String[] args) throws SQLException, IOException {
		db.runSql2("TRUNCATE search2;");
		processPage("http://www.ku.edu.tr");
		processPage("https://eng.ku.edu.tr/ekibimiz/personel-detay/?user=dunat");
		processPage("https://eng.ku.edu.tr/ekibimiz/personel-detay/?user=agursoy");
		processPage("https://eng.ku.edu.tr/ekibimiz/personel-detay/?user=akupcu");
		processPage("https://eng.ku.edu.tr/ekibimiz/personel-detay/?user=oozkasap");
		processPage("https://eng.ku.edu.tr/ekibimiz/personel-detay/?user=isalahor");
		processPage("https://eng.ku.edu.tr/ekibimiz/personel-detay/?user=mtsezgin");
		processPage("https://eng.ku.edu.tr/ekibimiz/personel-detay/?user=stasiran");
		processPage("https://eng.ku.edu.tr/ekibimiz/personel-detay/?user=yyemez");
		processPage("https://eng.ku.edu.tr/ekibimiz/personel-detay/?user=dyuret");
		processPage("https://it.ku.edu.tr/content/sitemap");

	}

	public static void processPage(String URL) throws SQLException, IOException{
		String sql; 

		// add first link to both queue and set
		queue.add(URL);
		marked.add(URL);

		// start BFS
		while (!queue.isEmpty()){
		// remove current URL only from queue
		URL = queue.remove();

		try {
			// get only html type
			Connection conn = Jsoup.connect(URL).timeout(1000000).validateTLSCertificates(false).ignoreHttpErrors(true).ignoreContentType(true);
			String type = conn.execute().contentType();

			if (type.startsWith("text/html")){
				System.out.println(URL);
				//prepare sql statement
				sql = "INSERT INTO  `kuzu`.`search2` " + "(`title`,`description`,`link`,`keywords`,`Outgoing`) VALUES " + "(?,?,?,?,?);";
				PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				String outgoing = "";
				String title = "";

				//connection
				Document doc2 = conn.get();

				// get and set title
				if(!StringUtils.isNullOrEmpty(doc2.select("title").text()))
					title =  doc2.select("title").text();
				else 
					title = URL;
				stmt.setString(1, title);

				// get and set description, keywords
				int l = doc2.text().length();
				if (l<200)
					stmt.setString(2, doc2.text().substring(0, l));
				else
					stmt.setString(2, doc2.text().substring(0, 200));
				stmt.setString(3, URL);
				stmt.setString(4,doc2.text());

				// get outgoing links
				Elements questions2 = doc2.select("a[href]");
				for(Element link2: questions2){

					if(link2.attr("href").contains("ku.edu.tr") && !link2.attr("href").contains("mailto") 
							/*&& !link2.attr("href").contains("javascript:fbShare") 
								&& (link2.attr("href").length() < 250) */
							&& !link2.attr("href").contains("facebook.com") && !link2.attr("href").contains("twitter.com")
							&& !link2.attr("href").contains("google.com") 
							&& !link2.attr("href").contains("iku.edu.tr")
							&& !link2.attr("href").contains("linkedin.com") && !link2.attr("href").contains("digg.com")
							&& !link2.attr("href").contains("delicious.com") && !link2.attr("href").contains("reddit.com")
							&& !link2.attr("href").contains("mems.ku.edu.tr") && !link2.attr("href").contains("flickr.com")
							&& !link2.attr("href").contains("instagram.com") && !link2.attr("href").contains("youtube.com")
							&& !link2.attr("href").contains("myspace.com") && !link2.attr("href").contains("tumblr.com")
							&& !link2.attr("href").contains("incubation.ku.edu.tr") && !link2.attr("href").contains("vkontakte.ru")
							){

						outgoing += " " + link2.attr("abs:href");
						String  temp;
						String subURL = link2.attr("abs:href");
						if (!marked.contains(subURL) && !marked.contains(subURL + "/")
								//&& !marked.contains("http://www." + subURL.substring(7))
								//&& !marked.contains("https://www." + subURL.substring(8))
								){
							
							if (subURL.startsWith("www")){
								temp = "http://" + subURL;
								marked.add(temp);
								queue.add(temp);
							/*}else if (subURL.startsWith("//")){
								temp = "http:" + subURL;
								marked.add(temp);
								queue.add(temp);
							}else if (subURL.startsWith("/")){
								marked.add(URL + subURL);
								queue.add(URL + subURL);*/
							}else{
								marked.add(subURL);
								queue.add(subURL);
							}
						}

					}

				}
				stmt.setString(5,outgoing);
				stmt.execute();
			}
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException in "+ URL);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Duplicate in "+ URL);
			queue.remove(URL);
		} catch (SQLException e) {
			System.out.println("SQL error in "+ URL);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in "+ URL);
		}

		}

	}
}
