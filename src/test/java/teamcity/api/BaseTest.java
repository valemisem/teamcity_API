package teamcity.api;

import teamcity.api.generators.TestDataStorage;
import teamcity.api.models.TestData;
import teamcity.api.requests.CheckedRequests;
import teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import static teamcity.api.generators.TestDataGenerator.generate;

public class BaseTest {

    protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserSpec());
    protected SoftAssert softy;
    protected TestData testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        softy = new SoftAssert();
        testData = generate(); // подготовка
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softy.assertAll();
        TestDataStorage.getStorage().deleteCreatedEntities();
    }
}