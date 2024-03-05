import java.util.Scanner;

// Интерфейс для об'єктів, що фабрикуються
interface EnergyResult {
    String displayResult();
}

// Интерфейс фабричного методу
interface EnergyResultFactory {
    EnergyResult createEnergyResult(double value);
}

// Клас для потенційної енергії
class PotentialEnergy implements EnergyResult {
    private double value;

    public PotentialEnergy(double value) {
        this.value = value;
    }

    @Override
    public String displayResult() {
        return "Потенційна енергія: " + value;
    }
}

// Клас для кінетичної енергії
class KineticEnergy implements EnergyResult {
    private double value;

    public KineticEnergy(double value) {
        this.value = value;
    }

    @Override
    public String displayResult() {
        return "Кінетична енергія: " + value;
    }
}

// Клас для повної енергії
class TotalEnergy implements EnergyResult {
    private double value;

    public TotalEnergy(double value) {
        this.value = value;
    }

    @Override
    public String displayResult() {
        return "Повна енергія: " + value;
    }
}

// Фабрика для створення об'єктів PotentialEnergy
class PotentialEnergyFactory implements EnergyResultFactory {
    @Override
    public EnergyResult createEnergyResult(double value) {
        return new PotentialEnergy(value);
    }
}

// Фабрика для створення об'єктів KineticEnergy
class KineticEnergyFactory implements EnergyResultFactory {
    @Override
    public EnergyResult createEnergyResult(double value) {
        return new KineticEnergy(value);
    }
}

// Фабрика для створення об'єктів TotalEnergy
class TotalEnergyFactory implements EnergyResultFactory {
    @Override
    public EnergyResult createEnergyResult(double value) {
        return new TotalEnergy(value);
    }
}

public class EnergyCalculationTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть масу тіла: ");
        double mass = scanner.nextDouble();

        System.out.print("Введіть висоту тіла: ");
        double height = scanner.nextDouble();

        System.out.print("Введіть швидкість тіла: ");
        double velocity = scanner.nextDouble();

        double g = 9.8;

        double potentialEnergy = mass * g * height;
        double kineticEnergy = 0.5 * mass * velocity * velocity;
        double totalEnergy = potentialEnergy + kineticEnergy;

        // Створення фабрик для різних типів енергії
        EnergyResultFactory potentialEnergyFactory = new PotentialEnergyFactory();
        EnergyResultFactory kineticEnergyFactory = new KineticEnergyFactory();
        EnergyResultFactory totalEnergyFactory = new TotalEnergyFactory();

        // Створення об'єктів енергії за допомогою фабричного методу
        EnergyResult potentialEnergyObject = potentialEnergyFactory.createEnergyResult(potentialEnergy);
        EnergyResult kineticEnergyObject = kineticEnergyFactory.createEnergyResult(kineticEnergy);
        EnergyResult totalEnergyObject = totalEnergyFactory.createEnergyResult(totalEnergy);

        // Виведення результатів
        System.out.println(potentialEnergyObject.displayResult());
        System.out.println(kineticEnergyObject.displayResult());
        System.out.println(totalEnergyObject.displayResult());
    }
}