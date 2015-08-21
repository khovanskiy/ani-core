import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author victor
 */
public class AniCoreMain implements Runnable {

    private ArrayList<Story> stories = new ArrayList<>();

    public static void main(String[] args) {
        new AniCoreMain().run();
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("target/result.html"))) {
            int min = 1;
            int max = 111;
            for (int i = min; i <= max; ++i) {
                try {
                    System.out.println("Handle page #" + i);
                    handlePage(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Iterator<Story> iterator = stories.stream()
                    .filter(o -> !o.isOngoing())
                    .filter(o -> !o.getGenre().contains("спорт") && !o.getGenre().contains("сёдзё") && !o.getGenre().contains("этти"))
                    .filter(o -> o.getYear() >= 2008)
                    .filter(o -> o.getRating() >= 4.0)
                    .sorted((o1, o2) -> {
                        int f = -Float.compare(o1.getRating(), o2.getRating());
                        if (f != 0) {
                            return f;
                        }
                        return -Integer.compare(o1.getYear(), o2.getYear());
                    })
                    .iterator();

            writer.println("<html><head><style>");
            writer.println(".story { width: 500px; } .story > table { width: 100%; } .story .poster { width:150px; }");
            writer.println("</style></head><body>");

            while (iterator.hasNext()) {
                Story story = iterator.next();
                writer.println("<div class=\"story\">");
                writer.println("<table>");
                writer.println("<tr><th colspan=\"3\">" + story.getTitle() + "</th></tr>");
                writer.println("<tr><td rowspan=\"4\"><img src=\"" + story.getImg() + "\" class=\"poster\"/></td><td>Рейтинг</td><td>" + story.getRating() + "</td></tr>");
                writer.println("<tr><td>Год</td><td>" + story.getYear() + "</td></tr>");
                writer.println("<tr><td>Жанр</td><td>" + story.getGenre() + "</td></tr>");
                writer.println("<tr><td>Количество эпизодов</td><td>" + story.getTotalEpisodes() + "</td></tr>");
                writer.println("</table>");
                writer.println("</div>");
            }

            writer.println("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handlePage(int pageNumber) throws IOException {
        String url = "http://tr.anidub.com/page/" + pageNumber + "/";
        //String url = "http://tr.anidub.com/xfsearch/%D1%81%D0%BF%D0%BE%D1%80%D1%82/page/" + pageNumber + "/";
        Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        //Document document = Jsoup.connect(url).timeout(10000).get();
        Elements content = document.select("article.story");
        for (Element element : content) {
            try {
                Element header = element.select(".story_h").get(0);
                if (!header.text().contains("Аниме TV")) {
                    continue;
                }
                Element title = header.select(".lcol h2").get(0);
                Story story = new Story(title.text());
                story.setOngoing(header.text().contains("Ongoing"));
                Element rating = header.select(".rcol sup").get(0);
                story.setRating(Float.parseFloat(rating.text().split(" ")[1]));
                Element info = element.select(".story_c .xfinfodata").get(0);
                Elements data = info.select("span");
                story.setImg(element.select(".story_c .poster img").get(0).attr("src"));
                story.setYear(Integer.parseInt(data.get(0).text().substring(0, 4)));
                story.setGenre(Arrays.asList(data.get(1).text().split(",\\s")));
                if (data.get(3).text().equals("100+")) {
                    story.setTotalEpisodes(100);
                } else {
                    story.setTotalEpisodes(Integer.parseInt(data.get(3).text()));
                }
                stories.add(story);
            } catch (Exception e) {
                System.out.println(element.html());
                e.printStackTrace();
            }
        }
    }
}
