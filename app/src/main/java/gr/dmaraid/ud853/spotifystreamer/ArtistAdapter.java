package gr.dmaraid.ud853.spotifystreamer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ArtistAdapter extends ArrayAdapter<My_Artist> {

    private Context context;
    private int layoutResourceId;
    private My_Artist data[] = null;
    private int icon_size = 170;

    public ArtistAdapter(Context context, int layoutResourceId, My_Artist[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public void add(My_Artist my_artist) {
        data[data.length] = my_artist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image_artist);
            holder.text = (TextView) row.findViewById(R.id.text_artist);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        My_Artist myArtist = data[position];
        holder.text.setText(myArtist.getName());
        Picasso.with(context)
                .load(myArtist.getIcon())
                .resize(icon_size, icon_size)
                .centerCrop()
                .into(holder.image);

        return row;
    }

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

}
