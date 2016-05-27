package models;

/**
 * Created by Aviran on 13/05/16.
 */
public class Dream {

    private String title;
    private String content;
    private long timeOfCreation;

    public Dream(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Dream(String title, String content, long timeOfCreation) {
        this.title = title;
        this.content = content;
        this.timeOfCreation = timeOfCreation;
    }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setTimeOfCreation(long timeOfCreation) { this.timeOfCreation = timeOfCreation; }

    public String getTitle() {return title; }
    public String getContent() { return content; }
    public long getTimeOfCreation() { return timeOfCreation; }

}
