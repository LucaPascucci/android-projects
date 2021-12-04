package it.lucapascucci.materialdesing.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.lucapascucci.materialdesing.R;
import it.lucapascucci.materialdesing.adapters.AdapterBoxOffice;
import it.lucapascucci.materialdesing.callbacks.BoxOfficeMoviesLoadedListener;
import it.lucapascucci.materialdesing.extras.MovieSorter;
import it.lucapascucci.materialdesing.extras.SortListener;
import it.lucapascucci.materialdesing.logging.L;
import it.lucapascucci.materialdesing.materialtest.MyApplication;
import it.lucapascucci.materialdesing.pojo.Movie;
import it.lucapascucci.materialdesing.task.TaskLoadMoviesBoxOffice;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment implements SortListener,BoxOfficeMoviesLoadedListener, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_MOVIES =  "state_movies";

    private String mParam1;
    private String mParam2;
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private RecyclerView listMovieHits;
    private AdapterBoxOffice adapterBoxOffice;
    private MovieSorter movieSorter = new MovieSorter();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, listMovies);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeMovieHits);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);

        if (savedInstanceState != null){
            listMovies = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
        }else {
            listMovies = MyApplication.getWritableDatabase().getAllMoviesBoxOffice();
            if (listMovies.isEmpty()){
                L.t(getActivity(),"executing task from fragment");
                new TaskLoadMoviesBoxOffice(this).execute();
            }

        }
        adapterBoxOffice.setMovieList(listMovies);
        return view;
    }


    @Override
    public void onSortByName() {
        movieSorter.sortMoviesByName(listMovies);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onSortByDate() {
        movieSorter.sortMoviesByDate(listMovies);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onSortByRating() {
        movieSorter.sortMoviesByRating(listMovies);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onBoxOfficeMovieLoaded(ArrayList<Movie> listMovies) {
        L.t(getActivity(),"onBoxOfficeMoviesLoaded Fragment");
        adapterBoxOffice.setMovieList(listMovies);
    }

    @Override
    public void onRefresh() {

    }
}
