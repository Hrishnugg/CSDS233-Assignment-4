import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class CMDBGroupTest {
    private CMDBGroup group;
    private CMDBProfile[] members;

    @Before
    public void setUp() {
        group = new CMDBGroup();
        members = new CMDBProfile[]{
                new CMDBProfile("JohnDoe"),
                new CMDBProfile("JaneDoe"),
                new CMDBProfile("Alice"),
                new CMDBProfile("Bob")
        };
        for (CMDBProfile member : members) {
            member.rate("Movie1", 9);
            member.rate("Movie2", 8);
            member.rate("Movie3", 7);
        }
        group.addMembers(members);
    }

    @Test
    public void testRegisteredUsers() {
        HashTable<CMDBProfile> registeredUsers = CMDBGroup.registeredUsers();
        assertNotNull(registeredUsers);
        assertEquals(4, registeredUsers.size());
    }

    @Test
    public void testGroup() {
        // Add members to the group
        String[] expected = {"JohnDoe", "JaneDoe", "Alice", "Bob"};
        assertArrayEquals(expected, group.group());
    }

    @Test
    public void testAddMember() {
        CMDBProfile newMember = new CMDBProfile("Charlie");
        group.addMember(newMember);
        assertEquals(5, group.group().length);
    }

    @Test
    public void testFavorite() {
        // Retrieve the favorite movie of a user
        String favorite = CMDBGroup.favorite("JohnDoe");
        assertEquals("Movie1", favorite);
    }

    @Test
    public void testGroupFavorites() {
        // Retrieve the favorite movie of a group
        String[] groupFavorites = group.groupFavorites();
        assertArrayEquals(new String[]{"Movie1"}, groupFavorites);
    }

    @Test
    public void testRandomMovie() {
        int k = 2;
        String randomMovie = group.randomMovie(k);
        Set<String> possibleMovies = new HashSet<>();
        possibleMovies.add("Movie1");
        possibleMovies.add("Movie2");
        possibleMovies.add("Movie3");
        assertTrue(possibleMovies.contains(randomMovie));
    }
}
