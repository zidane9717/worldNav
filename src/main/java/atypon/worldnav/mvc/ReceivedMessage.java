package atypon.worldnav.mvc;

public class ReceivedMessage {

    private String name;
    private String content;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;

    public ReceivedMessage() {
    }

    public ReceivedMessage(String name, String content, String number) {
        this.name = name;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
