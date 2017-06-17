/**
 * @author nwuensche
 */
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;

public class WebScrapper {
    private static int numberExperiment = 5;

    public static void main(String[] args) {
        WebDriver driver = new PhantomJSDriver();
        gotoLogin(driver);
        login(driver);
        checkVersuch5(driver);
    }

    public static void gotoLogin(WebDriver driver) {
        driver.get(Secrets.OPAL_URL);
        driver.findElement(By.id("id1d")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Select uni = new Select(driver.findElement(By.id("id40")));
        uni.selectByVisibleText("TU Dresden");
        driver.findElement(By.id("id48")).click();
    }

    public static void login(WebDriver driver) {
        driver.findElement(By.id("username")).sendKeys(Secrets.USERNAME);
        driver.findElement(By.id("password")).sendKeys(Secrets.PASS);
        driver.findElement(By.name("_eventId_proceed")).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.name("_eventId_proceed")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void checkVersuch5(WebDriver driver) {
        if(driver.getPageSource().contains("<span class=\"jstree-title\">Versuch " + numberExperiment + "</span>")) {
            System.out.println("Experiment " + numberExperiment + " online");
            sendMessage();
        }
        else {
            System.out.println("Experiment " + numberExperiment + " not online");
        }
    }


    public static void sendMessage() {
        Twilio.init(Secrets.ACCOUNT_SID, Secrets.AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(Secrets.MY_NUMBER), //To
                new PhoneNumber(Secrets.TWILIO_NUMBER), //From
                "Experiment " + numberExperiment + " online!").create();
    }
}
