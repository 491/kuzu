import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.mysql.jdbc.StringUtils;


public class WebCrawler {
        public static Queue<String> queue = new LinkedList<String>();
        public static HashSet<String> marked = new HashSet<String>();


        public static DataBaseConnect db = new DataBaseConnect();
        public static void main(String[] args) throws SQLException, IOException {
                db.runSql2("TRUNCATE search2;");
                //marked.add("https://gsssh.ku.edu.tr/en/about/student-guidelines/ders-kayitbirakma-formu");
                //processPage("https://gsssh.ku.edu.tr/en/about/student-guidelines/not-degisikligi-formu/");
                //processPage("https://gsssh.ku.edu.tr/en/about/student-guidelines/ders-kayitbirakma-formu");
                //processPage("https://gsssh.ku.edu.tr/en/about/student-guidelines/ders-kayitbirakma-formu");
                processPage("http://www.ku.edu.tr");
                //processPage("https://gsssh.ku.edu.tr/en/about/");
                //Document doc = Jsoup.connect("https://www.ku.edu.tr").userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                //      .referrer("http://www.google.com").ignoreHttpErrors(true).get();
                //Elements questions = doc.select("a[href]"); //Get total links of URL
                //for(Element link: questions){
                //if(link.attr("href").contains("ku.edu.tr") && !link.attr("href").contains("mailto") && !link.attr("href").contains(".pdf")){
                //processPage("http://www.ku.edu.tr");

                //}
                //}
        }
        public static void processPage(String URL) throws SQLException, IOException{
                BufferedWriter out = new BufferedWriter(new FileWriter("file.txt"));
                //check if the given URL is already in database
                String sql; /* = "select * from search where link = '"+URL+"'";
                ResultSet rs = db.runSql(sql);
                if(rs.next()){
                }else{
                        //store the URL to database to avoid parsing again
                        //sql = "INSERT INTO  `sql2107813`.`Record` " + "(`URL`) VALUES " + "(?);";
                 */
                // list of web pages to be examined
                //if (!marked.contains(URL) && !marked.contains(URL + "/") ){
                queue.add(URL);
                // set of examined web pages
                marked.add(URL);

                while (!queue.isEmpty() ){
                        //for (int i = 0; i < 168; i++) {

                        URL = queue.remove();

                        sql = "INSERT INTO  `kuzu`.`search2` " + "(`title`,`description`,`link`,`keywords`,`Outgoing`) VALUES " + "(?,?,?,?,?);";
                        PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                        System.out.println(URL);

                        String outgoing = "";
                        String title = "";

                        try {

                                Response res = Jsoup.connect(URL).timeout(1000000).validateTLSCertificates(false).ignoreHttpErrors(true).ignoreContentType(true).execute();
                                String type = res.contentType();
                                if (type.startsWith("text/html")){


                                Document doc2 = Jsoup.connect(URL).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X; de-de) AppleWebKit/523.10.3 (KHTML, like Gecko) Version/3.0.4 Safari/523.10")
                                                //.header("Content-Type","text/html")
                                                .timeout(1000000).validateTLSCertificates(false).ignoreHttpErrors(true).ignoreContentType(true).get();

                                //System.out.print(doc2.select("head"));
                                if(!StringUtils.isNullOrEmpty(doc2.select("title").text()))
                                        title =  doc2.select("title").text();
                                else
                                        title = URL;

                                stmt.setString(1, title);
                                int l = doc2.text().length();
                                if (l<200)
                                stmt.setString(2, doc2.text().substring(0, l));
                                else
                                stmt.setString(2, doc2.text().substring(0, 200));
                                stmt.setString(3, URL);
                                stmt.setString(4,doc2.text());

                                //get all links and recursively call the processPage method
                                Elements questions2 = doc2.select("a[href]");
                                for(Element link2: questions2){

                                        if(link2.attr("href").contains("ku.edu.tr") /*&& !link2.attr("href").contains("mailto") && !link2.attr("href").contains("javascript:fbShare")
                                                        && !link2.attr("href").contains(".pdf") && !link2.attr("href").contains(".PDF")
                                                        && !link2.attr("href").contains(".gif") && !link2.attr("href").contains(".GIF")
                                                        && !link2.attr("href").contains(".docx") && !link2.attr("href").contains(".DOCX")
                                                        && !link2.attr("href").contains(".jpg") && !link2.attr("href").contains(".JPG")
                                                        && !link2.attr("href").contains(".png") && !link2.attr("href").contains(".PNG")
                                                        && !link2.attr("href").contains(".ppt") && !link2.attr("href").contains(".PPT")
                                                        && !link2.attr("href").contains(".xlsx") && !link2.attr("href").contains(".XLSX")
                                                        && !link2.attr("href").contains(".JPEG") && !link2.attr("href").contains(".jpeg")
                                                        && !link2.attr("href").contains(".xls")  && !link2.attr("href").contains(".XLS")
                                                        && !link2.attr("href").contains(".doc") && !link2.attr("href").contains(".DOC")
                                                        && !link2.attr("href").contains(".wma") && !link2.attr("href").contains(".WMA")
                                                        && !link2.attr("href").contains(".swf") && !link2.attr("href").contains(".SWF")
                                                        && !link2.attr("href").contains(".mp4") && !link2.attr("href").contains(".MP4")
                                                        && !link2.attr("href").contains(".avi") && !link2.attr("href").contains(".AVI")
                                                        && !link2.attr("href").contains(".zip") && !link2.attr("href").contains(".ZIP")
                                                        && !link2.attr("href").contains(".rar") && !link2.attr("href").contains(".RAR")
                                                        && !link2.attr("href").contains(".HLP") && !link2.attr("href").contains(".hlp")
                                                        && !link2.attr("href").contains("facebook.com") && !link2.attr("href").contains("twitter.com")
                                                        && !link2.attr("href").contains("google.com") //&& !link2.attr("href").contains("iku.edu.tr")
                                                        && (link2.attr("href").length() < 250)
                                                        && !link2.attr("href").contains("https://gsssh.ku.edu.tr/en/about/student-guidelines/not-degisikligi-formu/")
                                                        && !link2.attr("href").contains("https://gsssh.ku.edu.tr/hakkimizda/ogrenci-klavuzu/not-degisikligi-formu/")
                                                        */){

                                                outgoing += " " + link2.attr("abs:href");

                                                String subURL = link2.attr("abs:href");
                                                if (!marked.contains(subURL) && !marked.contains(subURL + "/")
                                                                //&& !marked.contains("http://www." + subURL.substring(7))
                                                                //&& !marked.contains("https://www." + subURL.substring(8))
                                                                && !subURL.equals(URL)
                                                                ){
                                                        if (subURL.startsWith("www")){
                                                                marked.add("http://" + subURL);
                                                                queue.add("http://" + subURL);
                                                        }else if (subURL.startsWith("//")){
                                                                marked.add("http:" + subURL);
                                                                queue.add("http:" + subURL);
                                                        }else if (subURL.startsWith("/")){
                                                                marked.add(URL + subURL);
                                                                queue.add(URL + subURL);
                                                        }else{
                                                                marked.add(link2.attr("abs:href"));
                                                                queue.add(link2.attr("abs:href"));
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
                out.close();
        }
}