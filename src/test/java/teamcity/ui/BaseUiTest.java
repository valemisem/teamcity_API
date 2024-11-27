package teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import teamcity.BaseTest;
import teamcity.api.config.Config;
import teamcity.api.enums.Endpoint;
import teamcity.api.models.BuildType;
import teamcity.api.models.Project;
import teamcity.api.models.User;
import teamcity.ui.components.Footer;
import teamcity.ui.components.Header;
import teamcity.ui.pages.LoginPage;
import cteamcity.ui.pages.admin.CreateProjectPage;
import teamcity.ui.pages.admin.EditProjectPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Arrays;
import java.util.Map;

import static teamcity.api.enums.Endpoint.USERS;
import static teamcity.api.generators.TestDataGenerator.generate;

public class BaseUiTest extends BaseTest {
    protected static final String REPO_URL = "https://github.com/AlexPshe/spring-core-for-qa";
    protected  static final Project firstProject = generate(Project.class);
    protected static Footer footer;
    protected static Header header;

    @BeforeSuite(alwaysRun = true)
    public void setupUiTest() {
        Configuration.browser = Config.getProperty("browser");
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");
        Configuration.browserSize = Config.getProperty("browserSize");

        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true,
                        "enableLog", true));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    protected void loginAs(User user) {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        LoginPage.open().login(testData.getUser());
    }

    protected void createProject() {
        CreateProjectPage.open("_Root")
                .createFormManually(testData.getProject().getName(),testData.getProject().getId());
        EditProjectPage.checkSuccessMessageText(testData.getProject().getName());
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS)
                .read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);
    }

    protected void createFirstProject() {
        superUserCheckRequests.getRequest(Endpoint.PROJECTS).create(firstProject);
    }

    protected void createBuildType() {
        var firstBuildType = generate(Arrays.asList(testData.getProject()), BuildType.class);
        superUserCheckRequests.getRequest(Endpoint.BUILD_TYPES).create(firstBuildType);
    }

    protected void loggedInWithCreatedProjectAndBuildType (User user) {
        loginAs(user);
        createFirstProject();
        createProject();
        createBuildType();
    }

    protected void loggedInWithCreatedProject (User user) {
        loginAs(user);
        createFirstProject();
        createProject();
    }
}