import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        // Run all tests
        Result result = JUnitCore.runClasses(TokenizerTest.class, HashTableTest.class, WordStatTest.class,
                PriorityQueueTest.class, CMDBProfileTest.class, CMDBGroupTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
