import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class CMDBProfileTest {
    private CMDBProfile profile;

    @Before
    public void setUp() {
        profile = new CMDBProfile("JohnDoe");
        profile.rate("Movie1", 8);
        profile.rate("Movie2", 10);
        profile.rate("Movie3", 6);
    }

    @Test
    public void testChangeAndGetUserName() {
        assertEquals("JohnDoe", profile.getUserName());
        profile.changeUserName("JaneDoe");
        assertEquals("JaneDoe", profile.getUserName());
    }

    @Test
    public void testRate() {
        assertTrue(profile.rate("Movie4", 7));
        assertFalse(profile.rate("Movie5", 11));
    }

    @Test
    public void testChangeRating() {
        assertTrue(profile.changeRating("Movie1", 9));
        assertFalse(profile.changeRating("Movie1", 11));
    }

    @Test
    public void testRemoveRating() {
        assertTrue(profile.removeRating("Movie1"));
        assertFalse(profile.removeRating("NonExistentMovie"));
    }

    @Test
    public void testFavorite() {
        String[] favorites = profile.favorite();
        assertArrayEquals(new String[]{"Movie2"}, favorites);
    }

    @Test
    public void testFavoriteK() {
        String[] top2Favorites = profile.favorite(2);
        assertArrayEquals(new String[]{"Movie2", "Movie1"}, top2Favorites);
    }

    @Test
    public void testProfileInformation() {
        String expected = "JohnDoe(3)\nFavorite movie: Movie2";
        assertEquals(expected, profile.profileInformation());
    }

    @Test
    public void testEquals() {
        CMDBProfile otherProfile = new CMDBProfile("JohnDoe");
        assertTrue(profile.equals(otherProfile));

        otherProfile.changeUserName("JaneDoe");
        assertFalse(profile.equals(otherProfile));
    }
}
