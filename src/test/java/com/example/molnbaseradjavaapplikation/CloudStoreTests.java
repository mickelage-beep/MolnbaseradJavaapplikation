package com.example.molnbaseradjavaapplikation;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CloudStoreTests {

    String externalUrl = "http://newenviroment-env.eba-c5pzacqa.eu-north-1.elasticbeanstalk.com/";

    static Playwright playwright;
    static Browser browser;
    BrowserContext browserContext;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        boolean headless = System.getenv("HEADLESS") != null;

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(headless)
                        .setSlowMo(headless ? 0 : 2000)
                        .setArgs(List.of("--start-maximized"))
        );
    }

    @BeforeEach
    void setUp() {
        browserContext = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(null)
        );
        page = browserContext.newPage();
        page.navigate(externalUrl);
    }

    @AfterEach
    void tearDown() {
        browserContext.close();
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @Test
    void pageShouldLoad() {
        assertThat(page).hasTitle("CloudStore");
    }


    @Test
    void shouldShowNotLoggedInUser() {
        assertThat(page.locator("#currentUser"))
                .hasText("Inte inloggad");
    }

    @Test
    void shouldShowRegisterView() {

        page.locator("nav button")
                .filter(new Locator.FilterOptions().setHasText("Registrera"))
                .first()
                .click();

        Locator registerSection = page.locator("#register");

        assertThat(registerSection).not().hasClass("hidden");
    }

    @Test
    void shouldFillRegisterForm() {

        page.locator("nav button")
                .filter(new Locator.FilterOptions().setHasText("Registrera"))
                .first()
                .click();

        page.locator("#registerName").fill("testuser");
        page.locator("#registerPassword").fill("testpass");

        assertEquals("testuser", page.locator("#registerName").inputValue());
        assertEquals("testpass", page.locator("#registerPassword").inputValue());
    }

    @Test
    void shouldShowLoginView() {

        page.locator("nav button")
                .filter(new Locator.FilterOptions().setHasText("Logga in"))
                .first()
                .click();

        Locator login = page.locator("#login");

        assertThat(login).not().hasClass("hidden");
    }

    @Test
    void shouldFillLoginForm() {

        page.locator("nav button")
                .filter(new Locator.FilterOptions().setHasText("Logga in"))
                .first()
                .click();

        page.locator("#loginUsername").fill("user1");
        page.locator("#loginPassword").fill("pass1");

        assertEquals("user1", page.locator("#loginUsername").inputValue());
        assertEquals("pass1", page.locator("#loginPassword").inputValue());
    }

    @Test
    void shouldShowProductsSection() {
        assertThat(page.locator("#productsSection")).isVisible();
    }

    @Test
    void shouldShowOrdersSection() {

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Mina beställningar")
        ).click();

        Locator orders = page.locator("#ordersSection");

        assertThat(orders).not().hasClass("hidden");
    }

    @Test
    void shouldClickPlaceOrderButton() {

        Locator button = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Spara order")
        );

        assertThat(button).isVisible();
        button.click();
    }

    @Test
    void shouldHaveLogoutButton() {

        Locator logout = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Logga ut")
        );

        assertThat(logout).isVisible();
    }

    @Test
    void shouldLoadJavascriptApp() {
        assertThat(page.locator("#products")).isVisible();
    }
}