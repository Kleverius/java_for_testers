package ru.stqa.mantis.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.model.DeveloperMailUser;

import java.time.Duration;
import java.util.regex.Pattern;

public class UserRegistrationTests extends TestBase {

    @Test
    void canRegisterUser() {
        var username = CommonFunctions.randomString(8);
        var password = "password";
        var email = String.format("%s@localhost", username);
        app.jamesApi().addUser(email, password);
        app.session().register(username, email);
        var messages = app.mail().receive(email, password, Duration.ofSeconds(10));
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        if (matcher.find()) {
            var url = text.substring(matcher.start(), matcher.end());
            app.driver().get(url);
        }
        var realName = "My_real_name";
        app.session().setNewPassword(realName, password);
        app.http().login(username, password);
        Assertions.assertTrue(app.http().isLoggedIn());
    }


    DeveloperMailUser user;
    @Test
    void canCreateUser() {
        user = app.developerMail().addUser();
        var password = "password";
        var email = String.format("%s@developermail.com", user.name());
        app.session().register(user.name(), email);
        var message = app.developerMail().receive(user, Duration.ofSeconds(10));
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(message);
        if (matcher.find()) {
            var url = message.substring(matcher.start(), matcher.end());
            app.driver().get(url);
        }
        var realName = "My_real_name";
        app.session().setNewPassword(realName, password);
        app.http().login(user.name(), password);
        Assertions.assertTrue(app.http().isLoggedIn());
    }

    @AfterEach
    void deleteMailUser() {
        app.developerMail().deleteUser(user);
    }
}
