package cinema.show;

import cinema.show.Movie;

import java.util.HashMap;
import java.util.Map;

public class MovieService {

    private final Map<Integer,Movie> movies;

    private static MovieService _instance=null;

    public static MovieService getInstance(){
        if(_instance==null){
            _instance = new MovieService();
        }

        return _instance;
    }

    private MovieService() {
        movies = new HashMap<>();
        movies.put(1, new Movie(1,"Shreck",120,false));
        movies.put(2, new Movie(2,"Shreck2",160,false));
        movies.put(3, new Movie(3,"Minions",130,false));
    }

    public Movie getMovie(int movieId){
        return movies.get(movieId);
    }

}
