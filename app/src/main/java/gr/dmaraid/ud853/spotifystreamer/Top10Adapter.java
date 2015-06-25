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

public class Top10Adapter extends ArrayAdapter<Top10> {

    private Context context;
    private int layoutResourceId;
    private Top10 data[] = null;
    private int icon_size = 170;

    public Top10Adapter(Context context, int layoutResourceId, Top10[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public void add(Top10 top10) {
        data[data.length] = top10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image_top10);
            holder.song = (TextView) row.findViewById(R.id.text_song);
            holder.album = (TextView) row.findViewById(R.id.text_album);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Top10 top10 = data[position];
        holder.album.setText(top10.getAlbum());
        holder.song.setText(top10.getSong());
        Picasso.with(context)
                .load(top10.getIcon())
                .resize(icon_size, icon_size)
                .centerCrop()
                .into(holder.image);

        return row;
    }

    static class ViewHolder {
        public TextView song;
        public TextView album;
        public ImageView image;
    }

}
