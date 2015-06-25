package gr.dmaraid.ud853.spotifystreamer;


import android.os.AsyncTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

public class FetchTop10Task extends AsyncTask<String, Void, Top10[]> {

    @Override
    protected Top10[] doInBackground(String... chosen_artist) {

        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("country", "GR");

        Tracks searchArtists = spotify.getArtistTopTrack(chosen_artist[0], map);
        Top10 top10[] = null;

        List<Track> tracks = searchArtists.tracks;
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

        return top10;
    }
}
