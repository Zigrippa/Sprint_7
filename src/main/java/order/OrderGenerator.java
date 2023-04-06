package order;

import utils.RandomDate;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Random;

public class OrderGenerator {

    private List<String> firstNames = List.of("Сергей", "Петр", "Николай", "Игнат", "Никита");
    private List<String> lastNames = List.of("Сергеев", "Иванов", "Петров");
    private List<String> addresses = List.of("Острогоржская 7", "Капунова 15", "Ленина 48");
    private final Random random = new Random();
    private final RandomDate randomDate = new RandomDate();

    public Order generateOrder(List<String> color) {
        return new Order(
                firstNames.get(random.nextInt(firstNames.size() - 1)),
                lastNames.get(random.nextInt(lastNames.size() - 1)),
                addresses.get(random.nextInt(addresses.size() - 1)),
                RandomStringUtils.randomNumeric(20),
                "89" + RandomStringUtils.randomNumeric(9),
                random.nextInt(10) + 1,
                randomDate.generateRandomDate(),
                RandomStringUtils.randomAlphanumeric(10),
                color);
    }


}
