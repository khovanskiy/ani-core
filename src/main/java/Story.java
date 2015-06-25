import java.util.ArrayList;
import java.util.List;

/**
 * @author victor
 */
public class Story {

    private String title;
    private String russianTitle;
    private String englishTitle;
    private float rating;
    private int year;
    private int readyEpisodes;
    private int totalEpisodes;
    private boolean ongoing;
    private String img;
    private List<String> genre = new ArrayList<>();

    public Story(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return title + " " + ongoing + " " + totalEpisodes + " " + rating + " " + year + " " + genre;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getReadyEpisodes() {
        return readyEpisodes;
    }

    public void setReadyEpisodes(int readyEpisodes) {
        this.readyEpisodes = readyEpisodes;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    public String getRussianTitle() {
        return russianTitle;
    }

    public void setRussianTitle(String russianTitle) {
        this.russianTitle = russianTitle;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
