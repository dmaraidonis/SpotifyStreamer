package gr.dmaraid.ud853.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

public class Top10 implements Parcelable {

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

    private Top10(Parcel in) {
        icon = in.readString();
        song = in.readString();
        album = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(song);
        dest.writeString(album);
    }

    public static final Parcelable.Creator<Top10> CREATOR
            = new Parcelable.Creator<Top10>() {
        public Top10 createFromParcel(Parcel in) {
            return new Top10(in);
        }

        public Top10[] newArray(int size) {
            return new Top10[size];
        }
    };

}
