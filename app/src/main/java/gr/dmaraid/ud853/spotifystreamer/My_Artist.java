package gr.dmaraid.ud853.spotifystreamer;


public class My_Artist {

    public My_Artist() {
        super();
    }

    private String icon;
    private String name;
    private String id;

    public My_Artist(String icon, String name, String id) {
        super();
        this.icon = icon;
        this.name = name;
        this.id = id;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

}
