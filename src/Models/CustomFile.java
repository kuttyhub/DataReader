package Models;

public class CustomFile {
    private String type;
    private String filename;

    public CustomFile(String type, String filename) {
        this.type = type;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
