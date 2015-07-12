package gr.dmaraid.ud853.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.concurrent.ExecutionException;

public class Top10ActivitySSFragment extends Fragment {

    ActionBar actionBar;

    Top10Adapter mTop10Adapter;
    Top10 top10_data[] = null;

    private static final String TAG = Top10ActivitySSFragment.class.getSimpleName();
    private static final String KEY = TAG + "_KEY";

    public Top10ActivitySSFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top10_ss, container, false);

        String artistId = "";
        String artist = "";


        Intent receivedIntent = getActivity().getIntent();

        if (receivedIntent != null) {
            artistId = receivedIntent.getStringExtra(MainActivitySSFragment.EXTRA_ID);
            artist = receivedIntent.getStringExtra(MainActivitySSFragment.EXTRA_ARTIST);
            actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setSubtitle(artist);
        }

        try {
            if (savedInstanceState != null){
                top10_data = (Top10[]) savedInstanceState.getParcelableArray(KEY);
            } else {
                top10_data = new FetchTop10Task(getActivity()).execute(artistId).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ListView listTop10 = (ListView) rootView.findViewById(R.id.list_top10);

        mTop10Adapter = new Top10Adapter(getActivity(), R.layout.top_10_list_item, top10_data);

        listTop10.setAdapter(mTop10Adapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Log.v(TAG, "Saving in fragment");
        outState.putParcelableArray(KEY, top10_data);
    }
}
