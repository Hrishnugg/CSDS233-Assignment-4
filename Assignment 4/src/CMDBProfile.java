import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CMDBProfile{
    private String userName;
    private PriorityQueue<Integer, String> movieRatings;
    public CMDBProfile(String userName){
        movieRatings = new PriorityQueue<>();
        this.userName = userName;
    }
    public void changeUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }
    public boolean rate (String movie, int rating){
        // Check if rating is valid
        if (rating < 1 || rating > 10){
            return false;
        }
        movieRatings.add(rating, movie);
        return true;
    }
    public boolean changeRating(String movie, int newRating){
        if (newRating < 1 || newRating > 10){
            return false;
        }
        movieRatings.update(newRating, movie);
        return true;
    }
    public boolean removeRating(String movie) {
        // Check if movie exists and remove it if it does
        try{
            movieRatings.poll(movie);
        } catch (NoSuchElementException e){
            return false;
        }
        return true;
    }
    public String[] favorite(){
        ArrayList<KeyValuePair<Integer, String>> topFavorites = new ArrayList<>();
        int prevPriority = -1;
        if (movieRatings.size() == 0){
            return null;
        }
        // Find the highest priority
        while (movieRatings.size() != 0){
            KeyValuePair<Integer, String> curr = movieRatings.pollKV();
            if (curr.getKey() != prevPriority && prevPriority != -1){
                movieRatings.add(curr.getKey(), curr.getValue());
                break;
            }
            topFavorites.add(curr);
            prevPriority = curr.getKey();
        }
        String[] favorites = new String[topFavorites.size()];
        int index = 0;
        // Add the highest priority movies to the favorites array
        for (KeyValuePair<Integer, String> p : topFavorites){
            favorites[index++] = p.getValue();
            movieRatings.add(p.getKey(), p.getValue());
        }
        return favorites;
    }
    public String[] favorite(int k){
        return movieRatings.peek(k);
    }
    public String profileInformation(){
        try {
            String favoriteMovie = movieRatings.peek();
            return userName + "(" + movieRatings.size() + ")\n" + "Favorite movie: " + favoriteMovie;
        }
        catch (NoSuchElementException e){
            return userName + "(" + movieRatings.size() + ")\n" + "Favorite movie: None";
        }
    }
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        // Check if o is an instance of CMDBProfile or any of its subclasses
        if (o.getClass() != this.getClass()){
            return false;
        }
        CMDBProfile other = (CMDBProfile) o;
        return this.getUserName().equals(other.getUserName());
    }
}