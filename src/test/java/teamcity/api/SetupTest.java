package teamcity.api;

import teamcity.api.enums.Endpoint;
import teamcity.api.models.AuthSettings;
import teamcity.api.requests.CheckedRequests;
import teamcity.api.spec.Specifications;
import org.junit.jupiter.api.BeforeAll;
import org.testng.annotations.Test;

import static teamcity.api.enums.Endpoint.AUTH_SETTINGS;
import static teamcity.api.enums.Endpoint.USERS;

@Test
public class SetupTest extends BaseApiTest {
//    @Test
//    public void addRolesToAccount() {
//        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
//        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
//        userCheckRequests.getRequest(AUTH_SETTINGS).update("", testData.getAuthSettings());
//    }
}
