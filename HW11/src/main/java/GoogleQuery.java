import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.URL;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

public class GoogleQuery

{

	public String searchKeyword;

	public String url;

	public String content;

// public PriorityQueue<WebNode> heap;

	public GoogleQuery(String searchKeyword)

	{

		this.searchKeyword = searchKeyword;
		this.url = "http://www.google.com/search?q=" + "薇閣" + searchKeyword + "&oe=utf8&num=10";

	}

	private String fetchContent() throws IOException

	{
		String retVal = "";

		URL u = new URL(url);

		URLConnection conn = u.openConnection();

		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");

		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while ((line = bufReader.readLine()) != null) {
			retVal += line;

		}
		return retVal;
	}

	public HashMap<String, String> query() throws IOException

	{

		if (content == null)

		{

			content = fetchContent();

		}

		HashMap<String, String> retVal = new HashMap<String, String>();

		Document doc = Jsoup.parse(content);
		System.out.println(doc.text());
		Elements lis = doc.select("div");
//   System.out.println(lis);
		lis = lis.select(".kCrYT");
		// System.out.println(lis.size());

		for (Element li : lis) {
			try

			{
				String citeUrl = li.select("a").get(0).attr("href");
				String title = li.select("a").get(0).select(".vvjwJb").text();
				if (title.equals("")) {
					continue;
				}
				if (citeUrl.contains("&sa=U")) {
					citeUrl = citeUrl.substring(citeUrl.indexOf("https"), citeUrl.indexOf("&sa=U"));
				}
				if (citeUrl.contains("wikipedia")) {
					citeUrl = citeUrl.substring(citeUrl.indexOf("https"), citeUrl.indexOf("wiki/"));
				}
				if (citeUrl.contains("youtube")) {
					citeUrl = citeUrl.substring(citeUrl.indexOf("https"), citeUrl.indexOf("/watch"));
				}

				// citeUrl = citeUrl.substring(citeUrl.indexOf("http"),
				// citeUrl.indexOf("&sa=U"));
				WebPage web = new WebPage(citeUrl, title);
				ArrayList<Keyword> keywords = new ArrayList<Keyword>();
				keywords.add(new Keyword("薇閣", 3));
				keywords.add(new Keyword("薇閣高中", 5));
				keywords.add(new Keyword("旅館", -5));
				keywords.add(new Keyword("房", -5));
				keywords.add(new Keyword("Wego", 1));
				keywords.add(new Keyword("薇閣中學", 5));
				keywords.add(new Keyword("貴族學校", 2));
				keywords.add(new Keyword("田園教學", 4));
				keywords.add(new Keyword("大教室課程", 2));
				keywords.add(new Keyword("升學", 1));
				keywords.add(new Keyword("學測", 1));
				keywords.add(new Keyword("指考", 1));
				keywords.add(new Keyword("榜單", 2));
				keywords.add(new Keyword("直升", 2));
				keywords.add(new Keyword("高中", 1));
				keywords.add(new Keyword("台北", 2));
				keywords.add(new Keyword("國中", 1));
				keywords.add(new Keyword("私校", 1));
				keywords.add(new Keyword("國際班", 2.5));
				keywords.add(new Keyword("小學", 1));
				keywords.add(new Keyword("幼稚園", 1));
				System.out.println(web.name + "," + web.url + "(" + web.score + ")");
				retVal.put(web.name, web.url);
				// for(WebPage p:pages) {
				// System.out.println(p.name+","+p.url+"("+p.score+")");
				// }
				// System.out.println(web.name+","+web.url+"("+web.score+")");
			} catch (IndexOutOfBoundsException e) {

//    e.printStackTrace();

			}

		}

		return retVal;

	}

}