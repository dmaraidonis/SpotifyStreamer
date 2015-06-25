package gr.dmaraid.ud853.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivitySSFragment extends Fragment {

    public static final String EXTRA_ID = MainActivitySSFragment.class.getSimpleName() + "ID";
    public static final String EXTRA_ARTIST = MainActivitySSFragment.class.getSimpleName() + "ARTIST";
    //private static final String TAG = MainActivitySSFragment.class.getSimpleName();
    static ArtistAdapter mArtistAdapter;
    ListView list_artists;
    EditText editText;

    My_Artist myArtist_data[];

    public MainActivitySSFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_ss, container, false);

        editText = (EditText) rootView.findViewById(R.id.edit_search);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();

                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) getActivity().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        list_artists = (ListView) rootView.findViewById(R.id.list_artists);

        list_artists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToTop10 = new Intent(getActivity(), Top10ActivitySS.class);
                goToTop10.putExtra(EXTRA_ID, myArtist_data[position].getId());
                goToTop10.putExtra(EXTRA_ARTIST, myArtist_data[position].getName());
                startActivity(goToTop10);
            }
        });

        return rootView;
    }

    private void performSearch() {
        myArtist_data = null;
        try {
            myArtist_data = new FetchArtistsTask(getActivity())
                    .execute(editText.getText().toString()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (myArtist_data != null) {
            mArtistAdapter = new ArtistAdapter(getActivity(),
                    R.layout.list_item_ss,
                    myArtist_data);
            list_artists.setAdapter(mArtistAdapter);
        }
    }

}