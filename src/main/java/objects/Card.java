package objects;

public class Card {
    private int id;
    private String word;
    private String translation;
    private String status;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Card() {
    }

    public Card(int id, String word, String translation, String status, int user_id) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.status = status;
        this.user_id = user_id;
    }
}
