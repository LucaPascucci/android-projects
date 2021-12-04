package it.lucapascucci.materialdesing.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.lucapascucci.materialdesing.R;
import it.lucapascucci.materialdesing.anim.AnimationUtils;
import it.lucapascucci.materialdesing.extras.Constants;
import it.lucapascucci.materialdesing.network.VolleySingleton;
import it.lucapascucci.materialdesing.pojo.Movie;

/**
 * Created by Luca on 19/03/15.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice>{

    ArrayList<Movie> listMovie = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int previousPosition = 0;

    public AdapterBoxOffice(Context context){
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setMovieList (ArrayList<Movie> movieList){
        this.listMovie = movieList;
        notifyItemRangeChanged(0,movieList.size());
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_movie_box_office, parent, false);
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        Movie currentMovie = listMovie.get(position);
        holder.movieTitle.setText(currentMovie.getTitle());

        Date movieReleaseDate = currentMovie.getReleaseDateTheather();
        if (movieReleaseDate != null){
            String formattedDate = dateFormat.format(movieReleaseDate);
            holder.movieReleaseDate.setText(formattedDate);
        }else {
            holder.movieReleaseDate.setText(Constants.NA);
        }

        int audienceScore = currentMovie.getAudienceScore();
        if (audienceScore != -1){
            holder.movieAudienceScore.setRating(audienceScore/20.0f);
            holder.movieAudienceScore.setAlpha(1.0f);
        }else{
            holder.movieAudienceScore.setRating(0.0f);
            holder.movieAudienceScore.setAlpha(0.5f) ;
        }

        if (position > previousPosition){
            AnimationUtils.animate(holder,true);
        }else{
            AnimationUtils.animate(holder,false);
        }
        previousPosition = position;
        String urlThumbnail = currentMovie.getUrlThumbnail();
        loadImages(urlThumbnail,holder);
    }

    private void loadImages (String urlThumbnail, final ViewHolderBoxOffice holder){
        if (!urlThumbnail.equals(Constants.NA)){
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder{

        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieReleaseDate;
        private RatingBar movieAudienceScore;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);

            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
        }
    }
}
