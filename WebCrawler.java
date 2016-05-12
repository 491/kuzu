import java.io.BufferedWriter;
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
	public static BufferedWriter out ;
	public static void main(String[] args) throws SQLException, IOException {

		db.runSql2("TRUNCATE search4;");
		processPage("http://www.ku.edu.tr");

	}

	public static void processPage(String URL) throws SQLException, IOException{
		String sql; 


		// add first link to both queue and set
		if (!marked.contains(URL)){
			queue.add(URL);
			marked.add(URL);
		}

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
					sql = "INSERT INTO  `kuzu`.`search4` " + "(`title`,`description`,`link`,`keywords`,`Outgoing`) VALUES " + "(?,?,?,?,?);";
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
						String subURL = link2.attr("abs:href");
						if(subURL.contains("ku.edu.tr") && !subURL.contains("mailto") 
								/*&& !subURL.contains("javascript:fbShare") 
								&& (subURL.length() < 250) */
								&& !link2.attr("href").contains("facebook.com") && !subURL.contains("twitter.com")
								&& !subURL.contains("google.com") 
								&& !subURL.contains("iku.edu.tr")
								&& !subURL.contains("linkedin.com") && !subURL.contains("digg.com")
								&& !subURL.contains("delicious.com") && !subURL.contains("reddit.com")
								&& !subURL.contains("mems.ku.edu.tr") && !subURL.contains("flickr.com")
								&& !subURL.contains("instagram.com") && !subURL.contains("youtube.com")
								&& !subURL.contains("myspace.com") && !subURL.contains("tumblr.com")
								&& !subURL.contains("incubation.ku.edu.tr") && !subURL.contains("vkontakte.ru")
								&& !subURL.contains("lms.ku.edu.tr") && !subURL.contains("elconline.ku.edu.tr")
								){
							
							if (subURL.endsWith("/#"))
								subURL = subURL.substring(0,subURL.length()-2);
							else if (subURL.endsWith("/tr"))
								subURL = subURL.substring(0,subURL.length()-3);
							else if (subURL.endsWith("/tr/home"))
								subURL = subURL.substring(0,subURL.length()-8);
							else if (subURL.endsWith("/home"))
								subURL = subURL.substring(0,subURL.length()-5);
							else if (subURL.endsWith("/tr/home"))
								subURL = subURL.substring(0,subURL.length()-8);
							else if (subURL.endsWith("#"))
								subURL = subURL.substring(0,subURL.length()-1);
							else if  (subURL.endsWith("/"))
								subURL = subURL.substring(0,subURL.length()-1);
							

							if (subURL.startsWith("https://")){
								subURL = "http" + subURL.substring(5);
							}else if (subURL.startsWith("www")){
								subURL = "http://" + subURL;
							}else if (subURL.startsWith("/")){
								subURL = URL + subURL;
							}
							if(subURL.length()>11){
								if (marked.contains("http://www." + subURL.substring(7)))
									subURL = "http://www." + subURL.substring(7);
								if (marked.contains("http://" + subURL.substring(11)))
									subURL = "http://" + subURL.substring(11);
							}
							outgoing += " " + subURL;

							if (!marked.contains(subURL)){
								marked.add(subURL);
								queue.add(subURL);

							}

						}

					}
					stmt.setString(5,outgoing);
					stmt.execute();

				}



			} catch (UnknownHostException e) {
				System.out.println("UnknownHostException in "+ URL);
				out.write("UnknownHostException in "+ URL + "\n");
			} catch (SQLIntegrityConstraintViolationException e) {
				System.out.println("Duplicate in "+ URL);
				out.write("Duplicate in "+ URL+ "\n");
				queue.remove(URL);
			} catch (SQLException e) {
				System.out.println("SQL error in "+ URL);
				out.write("SQL error in "+ URL+ "\n");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception in "+ URL);
				out.write("Exception in "+ URL+ "\n");
				out.write(e.getMessage());

			}

		}

	}
}
