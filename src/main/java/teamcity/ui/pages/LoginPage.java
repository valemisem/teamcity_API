package teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.User;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {

    private static final String LOGIN_URL = "/login.html";

    private SelenideElement inputUsername = $("#username");
    private SelenideElement inputPassword = $("#password");
    private SelenideElement inputSubmitLogin = $(".loginButton");

    public static LoginPage open() {
        return Selenide.open(LOGIN_URL, LoginPage.class);
    }

    public ProjectPage login(User user) {
        // Метод val содержит в себя 2 метода: clear, sendKeys
        inputUsername.val(user.getUsername());
        inputPassword.val(user.getPassword());
        inputSubmitLogin.click();

        return Selenide.page(ProjectPage.class);
    }


}