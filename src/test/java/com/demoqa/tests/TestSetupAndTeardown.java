package com.demoqa.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.CredsConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestSetupAndTeardown {

    @BeforeAll
    static void setUp() {
        CredsConfig config = ConfigFactory.create(CredsConfig.class);
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        String login = config.login();
        String password = config.password();
        String remoteDriver = System.getProperty("remoteDriver", "selenoid.autotests.cloud");
        String browserSize = System.getProperty("browserSize", "1920x1080");
        String baseUrl = System.getProperty("baseUrl", "https://demoqa.com");
        String browser = System.getProperty("browser", "chrome");

        Configuration.baseUrl = baseUrl;
        Configuration.browserSize = browserSize;
        Configuration.browser = browser;
        Configuration.remote = "https://" + login + ":" + password + "@" + remoteDriver + "/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Screen");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
}
