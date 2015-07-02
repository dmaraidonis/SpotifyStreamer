package gr.dmaraid.ud853.spotifystreamer;


import android.os.Parcel;
import android.os.Parcelable;

public class My_Artist implements Parcelable {

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

    private My_Artist(Parcel in) {
        icon = in.readString();
        name = in.readString();
        id = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(name);
        dest.writeString(id);
    }

    public static final Parcelable.Creator<My_Artist> CREATOR
            = new Parcelable.Creator<My_Artist>() {
        public My_Artist createFromParcel(Parcel in) {
            return new My_Artist(in);
        }

        public My_Artist[] newArray(int size) {
            return new My_Artist[size];
        }
    };
}
