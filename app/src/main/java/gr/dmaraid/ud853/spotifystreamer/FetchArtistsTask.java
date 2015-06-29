package gr.dmaraid.ud853.spotifystreamer;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;

public class FetchArtistsTask extends AsyncTask<String, Void, My_Artist[]> {

    private Context context;
    //private String TAG = FetchArtistsTask.class.getSimpleName();
    private int searchSize = 0;
    boolean hasInternetAccess = false;

    public FetchArtistsTask(Context context) {
        this.context = context;
    }

    @Override
    protected My_Artist[] doInBackground(String... given_artist) {

        if (isOnline()) {
            hasInternetAccess = true;
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();

            ArtistsPager searchArtists = spotify.searchArtists(given_artist[0]);

            My_Artist my_artists[] = null;

            searchSize = searchArtists.artists.total;
            if (searchSize > 0) {
                if (searchSize > searchArtists.artists.limit) {
                    my_artists = new My_Artist[searchArtists.artists.limit];
                } else {
                    my_artists = new My_Artist[searchSize];
                }

                List<Artist> listOfArtists = searchArtists.artists.items;

                String artist_name;
                String artist_id;
                for (int i = 0; i < listOfArtists.size(); i++) {

                    artist_name = listOfArtists.get(i).name;
                    artist_id = listOfArtists.get(i).id;
                    List<Image> listOfImages = listOfArtists.get(i).images;
                    String artist_image = "file:///android_asset/no_image_available.jpg";
                    if (listOfImages.size() > 0) {
                        for (int j = 0; j < listOfImages.size(); j++) {
                            if (listOfImages.get(j).height <= 300 && listOfImages.get(j).height >= 100) {
                                artist_image = listOfImages.get(j).url;
                            }
                        }
                    }

                    my_artists[i] = new My_Artist(artist_image, artist_name, artist_id);
                    //Log.i(TAG, "Name: " + my_artists[i].getName() + ", Image URL: " + my_artists[i].getIcon());
                }
            }

            return my_artists;
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(My_Artist[] my_artists) {
        if (!hasInternetAccess) {
            Toast.makeText(context, context.getString(R.string.no_internet_access), Toast.LENGTH_LONG).show();
        } else {
            if (searchSize == 0) {
                Toast.makeText(context, context.getString(R.string.empty_search), Toast.LENGTH_LONG).show();
            }
        }
    }

    boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

}
