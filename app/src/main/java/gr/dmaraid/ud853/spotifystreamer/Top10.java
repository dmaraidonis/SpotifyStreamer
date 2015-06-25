package gr.dmaraid.ud853.spotifystreamer;

public class Top10 {

    public Top10() {
        super();
    }

    private String icon;
    private String song;
    private String album;

    public Top10(String icon, String song, String album) {
        super();
        this.icon = icon;
        this.song = song;
        this.album = album;
    }

    // Getters
    public String getSong() {
        return song;
    }

    public String getIcon() {
        return icon;
    }

    public String getAlbum() {
        return album;
    }

}
