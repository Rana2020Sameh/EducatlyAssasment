import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchTests {
    public WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized");
        }
        driver.get("https://www.educatly.com/");

    }

    @Test
    public void SearchFunctionalityusingAutoSelect() {

        WebElement whatToStudy = driver.findElement(By.xpath("//input[@id='rc_select_0']"));
        whatToStudy.click();
        List<WebElement> whatToStudyCategory = driver.findElements(By.xpath("//div[@class='ant-select-item-option-content']"));
        whatToStudyCategory.get(3).click();
        WebElement whereToStudy = driver.findElement(By.xpath("//input[@aria-owns='rc_select_1_list']"));
        whereToStudy.click();
        List<WebElement> whereToStudyCategory = driver.findElements(By.xpath("//div[@class='rc-virtual-list-holder-inner']"));
        whereToStudyCategory.get(1).click();
        WebElement searchButton = driver.findElement(By.xpath("//div[@class='styles_header__start__search__btn__Faur6']"));
        searchButton.click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        String url = driver.getCurrentUrl();
        System.out.println(url);
        Assert.assertEquals(url, "https://www.educatly.com/programs/computer-science-it/north-america");

    }

    @Test
    public void SearchFunctionalityUsingInsertDataIntoWhatToStudy() {

        WebElement whatToStudy = driver.findElement(By.xpath("//input[@id='rc_select_0']"));
        whatToStudy.sendKeys("Arts");
        List<WebElement> whatToStudyCategory = driver.findElements(By.xpath("//div[@class='ant-select-item-option-content']"));
        whatToStudyCategory.get(0).click();
       /* WebElement whereToStudy = driver.findElement(By.xpath("//input[@aria-owns='rc_select_1_list']"));
        whereToStudy.sendKeys("North America");
        List<WebElement> whereToStudyCategory = driver.findElements(By.xpath("//div[@class='rc-virtual-list-holder-inner']"));
        whereToStudyCategory.get(0).click();*/
        WebElement searchButton = driver.findElement(By.xpath("//div[@class='styles_header__start__search__btn__Faur6']"));
        searchButton.click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        WebElement searchData = driver.findElement(By.xpath("//div[@class='styles_criteriaCards__card__DWCA1']"));
        String searchInput = searchData.getText();
        Assert.assertEquals(searchInput,"Arts");

    }

    @Test
    public void SearchFunctionalityUsingInsertDataIntoWhereToStudy() {
        WebElement whereToStudy = driver.findElement(By.xpath("//input[@aria-owns='rc_select_1_list']"));
        whereToStudy.sendKeys("North America");
        List<WebElement> whereToStudyCategory = driver.findElements(By.xpath("//div[@class='rc-virtual-list-holder-inner']"));
        whereToStudyCategory.get(0).click();
        WebElement searchButton = driver.findElement(By.xpath("//div[@class='styles_header__start__search__btn__Faur6']"));
        searchButton.click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        WebElement searchData = driver.findElement(By.xpath("//div[@class='styles_criteriaCards__start__yNm95']"));
        String searchInput = searchData.getText();
        Assert.assertEquals(searchInput,"North America");

    }
    @Test
    public void SearchFunctionalitywithemptyinput() {
        WebElement searchButton = driver.findElement(By.xpath("//div[@class='styles_header__start__search__btn__Faur6']"));
        searchButton.click();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        WebElement searchData = driver.findElement(By.xpath("//span[@class='ais-Stats-text']"));
        String searchInput = searchData.getText();
        Assert.assertEquals(searchInput,"71,918 Programs");


    }

    @Test
    public void SearchFunctionalitywithSpecialChar() {
        WebElement whatToStudy = driver.findElement(By.xpath("//input[@id='rc_select_0']"));
        whatToStudy.sendKeys("@");
        WebElement noMatch = driver.findElement(By.xpath("//div[@class='ant-select-item-empty']"));
        String matching = noMatch.getText();
        Assert.assertEquals(matching, "No matches");
        WebElement whereToStudy = driver.findElement(By.xpath("//input[@aria-owns='rc_select_1_list']"));
        whereToStudy.sendKeys("%");
        WebElement noMatch1 = driver.findElement(By.xpath("//div[@class='ant-select-item-empty']"));
        String matching1 = noMatch.getText();
        Assert.assertEquals(matching1, "No matches");

    }

    @Test
    public void ValidatePlaceholder() {
        List<WebElement> searchInput = driver.findElements(By.xpath("//span[@class='ant-select-selection-placeholder']"));
        String placeholderWhatStudy = searchInput.get(0).getText();
        Assert.assertEquals(placeholderWhatStudy, "What to study");
        String placeholderWhereStudy = searchInput.get(1).getText();
        Assert.assertEquals(placeholderWhereStudy, "Where to study");

    }

    @Test
    public void ValidateCleareSearchInpurts() {
        WebElement whatToStudy = driver.findElement(By.xpath("//input[@id='rc_select_0']"));
        whatToStudy.sendKeys("Arts");
        whatToStudy.sendKeys(Keys.BACK_SPACE);
        List<WebElement> searchInput = driver.findElements(By.xpath("//span[@class='ant-select-selection-placeholder']"));
        String placeholderWhatStudy = searchInput.get(0).getText();
        Assert.assertEquals(placeholderWhatStudy, "What to study");
        WebElement whereToStudy = driver.findElement(By.xpath("//input[@aria-owns='rc_select_1_list']"));
        whereToStudy.sendKeys("North America");
        whereToStudy.sendKeys(Keys.BACK_SPACE);

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
