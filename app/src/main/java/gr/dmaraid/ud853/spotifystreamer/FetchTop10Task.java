package gr.dmaraid.ud853.spotifystreamer;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

public class FetchTop10Task extends AsyncTask<String, Void, Top10[]> {

    private Context context;
    boolean hasTop10 = false;
    boolean hasInternetAccess = false;

    public FetchTop10Task(Context context) {
        this.context = context;
    }

    @Override
    protected Top10[] doInBackground(String... chosen_artist) {

        if (isOnline()) {
            hasInternetAccess = true;

            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();

            Map<String, Object> map = new HashMap<>();
            map.put("country", "GR");

            Tracks searchArtists = spotify.getArtistTopTrack(chosen_artist[0], map);
            Top10 top10[] = new Top10[0];

            List<Track> tracks = searchArtists.tracks;
            if (tracks.size() != 0) {
                hasTop10 = true;
                if (tracks.size() >= 10) {
                    top10 = new Top10[10];
                } else {
                    top10 = new Top10[tracks.size()];
                }
                for (int i = 0; i < tracks.size(); i++) {
                    String song = tracks.get(i).name;
                    String album = tracks.get(i).album.name;
                    String top10_image = "file:///android_asset/no_image_available.jpg";

                    List<Image> listOfImages = tracks.get(i).album.images;
                    if (listOfImages.size() > 0) {
                        for (int j = 0; j < listOfImages.size(); j++) {
                            if (listOfImages.get(j).height <= 300 && listOfImages.get(j).height >= 100) {
                                top10_image = listOfImages.get(j).url;
                            }
                        }
                    }
                    top10[i] = new Top10(top10_image, song, album);
                }
            }
            return top10;
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Top10[] top10s) {
        super.onPostExecute(top10s);
        if (!hasInternetAccess) {
            Toast.makeText(context, context.getString(R.string.no_internet_access), Toast.LENGTH_LONG).show();
        } else {
            if (!hasTop10) {
                Toast.makeText(context, context.getString(R.string.empty_top10), Toast.LENGTH_LONG).show();
            }
        }
    }

    boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

}
