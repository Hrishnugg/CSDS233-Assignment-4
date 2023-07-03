import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class CMDBGroup{
    private static HashTable<CMDBProfile> userDB = new HashTable<CMDBProfile>();
    private ArrayList<String> userNames;
    public CMDBGroup (){
        userNames = new ArrayList<String>();
    }
    public static HashTable<CMDBProfile> registeredUsers (){
        return userDB;
    }
    public String[] group(){
        String[] groupNames = new String[userNames.size()];
        userNames.toArray(groupNames);
        return groupNames;
    }
    public void addMember(CMDBProfile member){
        userNames.add(member.getUserName());
        userDB.put(member.getUserName(), member);
    }
    public void addMembers(CMDBProfile[] members){
        for (CMDBProfile member : members){
            addMember(member);
        }
    }
    public static String favorite(String userName){
        CMDBProfile user;
        // Get user profile
        try{
            user = userDB.get(userName);
        } catch (NoSuchElementException e){
            return null;
        }
        String[] favorites = user.favorite();
        if (favorites == null){
            return null;
        }
        return favorites[0];
    }
    public String[] groupFavorites(){
        HashTable<Integer> movieVotes = new HashTable<Integer>();
        ArrayList<String> groupFavorites = new ArrayList<String>();
        for (String user: userNames){
            CMDBProfile userProfile = userDB.get(user);
            String[] favorites = userProfile.favorite();
            for (String movie: favorites){
                int votes = 0;
                // Get votes for movie
                try{
                    votes = movieVotes.get(movie);
                } catch (NoSuchElementException e){
                    groupFavorites.add(movie);
                }
                movieVotes.put(movie, votes + 1);
            }
        }
        // Find max votes for a movie
        int maxVotes = 0;
        for (String movie: groupFavorites){
            int votes = movieVotes.get(movie);
            if (votes > maxVotes){
                maxVotes = votes;
            }
        }
        // Find movies with max votes
        ArrayList<String> maxMovies = new ArrayList<String>();
        for (String movie: groupFavorites){
            int votes = movieVotes.get(movie);
            if (votes == maxVotes){
                maxMovies.add(movie);
            }
        }
        String[] groupFavoritesArray = new String[maxMovies.size()];
        maxMovies.toArray(groupFavoritesArray);
        return groupFavoritesArray;
    }
    String randomMovie(int k){
        int groupSize = userNames.size();
        int[] memberCounts = new int[groupSize];
        ArrayList<String> moviePool = new ArrayList<>();
        // Get random movies from each user
        for (int i = 0; i < k; i++){
            int randomIndex = (int) (Math.random() * userNames.size());
            memberCounts[randomIndex]++;
        }
        for (int i = 0; i < groupSize; i++){
            if (memberCounts[i] == 0){
                continue;
            }
            String userName = userNames.get(i);
            CMDBProfile user = userDB.get(userName);
            String[] favorites = user.favorite(memberCounts[i]);
            // Add movies to pool
            for (String movie: favorites){
                moviePool.add(movie);
            }
        }
        int randomIndex = (int) (Math.random() * moviePool.size());
        return moviePool.get(randomIndex);
    }

}