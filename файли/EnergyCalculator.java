import java.util.*;
import java.util.concurrent.*;

// Інтерфейс для команд
interface Command {
    void execute(EnergyDisplay energyDisplay); // Додано параметр EnergyDisplay
}

// Команда для обчислення енергії
class CalculateEnergyCommand implements Command {
    private final EnergyCalculator energyCalculator;

    public CalculateEnergyCommand(EnergyCalculator energyCalculator) {
        this.energyCalculator = energyCalculator;
    }

    @Override
    public void execute(EnergyDisplay energyDisplay) { // Додано параметр EnergyDisplay
        energyCalculator.calculateEnergy(energyDisplay); // Передача параметра energyDisplay
    }
}

// Команда для скасування останньої операції
class UndoCommand implements Command {
    private final EnergyCalculator energyCalculator;

    public UndoCommand(EnergyCalculator energyCalculator) {
        this.energyCalculator = energyCalculator;
    }

    @Override
    public void execute(EnergyDisplay energyDisplay) { // Додано параметр EnergyDisplay
        energyCalculator.undo();
    }
}

// Команда для обчислення максимуму
class CalculateMaximumCommand implements Command {
    private final EnergyCalculator energyCalculator;

    public CalculateMaximumCommand(EnergyCalculator energyCalculator) {
        this.energyCalculator = energyCalculator;
    }

    @Override
    public void execute(EnergyDisplay energyDisplay) {
        double maximum = energyCalculator.calculateMaximum();
        energyDisplay.displayMaximum(maximum);
    }
}

// Команда для обчислення мінімуму
class CalculateMinimumCommand implements Command {
    private final EnergyCalculator energyCalculator;

    public CalculateMinimumCommand(EnergyCalculator energyCalculator) {
        this.energyCalculator = energyCalculator;
    }

    @Override
    public void execute(EnergyDisplay energyDisplay) {
        double minimum = energyCalculator.calculateMinimum();
        energyDisplay.displayMinimum(minimum);
    }
}

// Команда для обчислення середнього значення
class CalculateAverageCommand implements Command {
    private final EnergyCalculator energyCalculator;

    public CalculateAverageCommand(EnergyCalculator energyCalculator) {
        this.energyCalculator = energyCalculator;
    }

    @Override
    public void execute(EnergyDisplay energyDisplay) {
        double average = energyCalculator.calculateAverage();
        energyDisplay.displayAverage(average);
    }
}

// Макрокоманда, яка виконує послідовність команд
class MacroCommand implements Command {
    private final List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void execute(EnergyDisplay energyDisplay) { // Додано параметр EnergyDisplay
        for (Command command : commands) {
            command.execute(energyDisplay); // Передача параметра energyDisplay
        }
    }

    public void clear() {
        commands.clear();
    }
}

// Клас для обчислення енергії
public class EnergyCalculator {
    private static EnergyCalculator instance;

    private final Scanner scanner;
    private final ExecutorService executorService;
    private final List<Double> numbers = new ArrayList<>(); // Список чисел для обчислення максимуму, мінімуму та середнього

    private EnergyCalculator() {
        scanner = new Scanner(System.in);
        executorService = Executors.newFixedThreadPool(4); // Використання фіксованого пула з 4 потоками
    }

    public static EnergyCalculator getInstance() {
        if (instance == null) {
            instance = new EnergyCalculator();
        }
        return instance;
    }

    public void calculateEnergy(EnergyDisplay energyDisplay) { // Додано параметр EnergyDisplay
        System.out.print("Введіть масу тіла (в кг): ");
        double mass = scanner.nextDouble();

        System.out.print("Введіть швидкість тіла (в м/с): ");
        double velocity = scanner.nextDouble();

        System.out.print("Введіть висоту тіла (в м): ");
        double height = scanner.nextDouble();

        numbers.clear(); // Очистка списку перед додаванням нових чисел
        numbers.add(mass);
        numbers.add(velocity);
        numbers.add(height);

        Future<Double> kineticEnergyFuture = executorService.submit(() -> calculateKineticEnergy(mass, velocity));
        Future<Double> potentialEnergyFuture = executorService.submit(() -> calculatePotentialEnergy(mass, height));

        try {
            double kineticEnergy = kineticEnergyFuture.get();
            double potentialEnergy = potentialEnergyFuture.get();
            double totalEnergy = kineticEnergy + potentialEnergy;

            displayEnergy(energyDisplay, kineticEnergy, potentialEnergy, totalEnergy); // Передача результатів обчисленень
            executorService.shutdown(); // Завершення роботи пула потоків після виведення результату
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private double calculateKineticEnergy(double mass, double velocity) {
        return 0.5 * mass * Math.pow(velocity, 2);
    }

    private double calculatePotentialEnergy(double mass, double height) {
        return mass * 9.8 * height;
    }

    public double calculateMaximum() {
        return Collections.max(numbers);
    }

    public double calculateMinimum() {
        return Collections.min(numbers);
    }

    public double calculateAverage() {
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        return sum / numbers.size();
    }

    public void undo() {
        // Додаткова логіка скасування операції
        System.out.println("Остання операція скасована.");
    }

    private void displayEnergy(EnergyDisplay energyDisplay, double kineticEnergy, double potentialEnergy, double totalEnergy) {
        energyDisplay.displayKineticEnergy(kineticEnergy);
        energyDisplay.displayPotentialEnergy(potentialEnergy);
        energyDisplay.displayTotalEnergy(totalEnergy);
        energyDisplay.displayTable(kineticEnergy, potentialEnergy, totalEnergy);
    }

    private EnergyDisplay chooseEnergyDisplay() {
        System.out.println("Оберіть тип відображення:");
        System.out.println("1. Текст");
        System.out.println("2. Таблиця");
        System.out.println("3. Вийти");
        System.out.print("Введіть номер: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return new TextEnergyDisplay();
            case 2:
                return new TableEnergyDisplay();
            case 3:
                System.exit(0); // Вихід з програми
            default:
                System.out.println("Невідомий варіант. Спробуйте ще раз.");
                return chooseEnergyDisplay(); // Рекурсивний виклик для повторного введення
        }
    }

    public void executeCommandsAndShutdown() { // Перейменування методу
        EnergyDisplay energyDisplay = chooseEnergyDisplay();
        calculateEnergy(energyDisplay); // Почати обчислення енергії при виборі користувачем відображення
    }

    public static void main(String[] args) {
        EnergyCalculator energyCalculator = EnergyCalculator.getInstance();
        energyCalculator.executeCommandsAndShutdown(); // Виклик методу
    }
}

// Клас для відображення результатів обчисленень у текстовому вигляді
class TextEnergyDisplay implements EnergyDisplay {
    @Override
    public void displayKineticEnergy(double kineticEnergy) {
        System.out.println("Кінетична енергія: " + kineticEnergy + " Дж");
    }

    @Override
    public void displayPotentialEnergy(double potentialEnergy) {
        System.out.println("Потенційна енергія: " + potentialEnergy + " Дж");
    }

    @Override
    public void displayTotalEnergy(double totalEnergy) {
        System.out.println("Повна енергія: " + totalEnergy + " Дж");
    }

    @Override
    public void displayTable(double kineticEnergy, double potentialEnergy, double totalEnergy) {
        // Не підтримується у текстовому вигляді
    }

    @Override
    public void displayMaximum(double maximum) {
        System.out.println("Максимум: " + maximum);
    }

    @Override
    public void displayMinimum(double minimum) {
        System.out.println("Мінімум: " + minimum);
    }

    @Override
    public void displayAverage(double average) {
        System.out.println("Середнє: " + average);
    }
}

// Клас для відображення результатів обчислення у вигляді таблиці
class TableEnergyDisplay implements EnergyDisplay {
    @Override
    public void displayKineticEnergy(double kineticEnergy) {
        // Не підтримується у табличному вигляді
    }

    @Override
    public void displayPotentialEnergy(double potentialEnergy) {
        // Не підтримується у табличному вигляді
    }

    @Override
    public void displayTotalEnergy(double totalEnergy) {
        // Не підтримується у табличному вигляді
    }

    @Override
    public void displayTable(double kineticEnergy, double potentialEnergy, double totalEnergy) {
        System.out.println("=======================================");
        System.out.println("|           Energy Calculation         |");
        System.out.println("=======================================");
        System.out.println("| Kinetic Energy    | " + kineticEnergy + " J");
        System.out.println("| Potential Energy  | " + potentialEnergy + " J");
        System.out.println("| Total Energy      | " + totalEnergy + " J");
        System.out.println("=======================================");
    }

    @Override
    public void displayMaximum(double maximum) {
        System.out.println("Максимум: " + maximum);
    }

    @Override
    public void displayMinimum(double minimum) {
        System.out.println("Мінімум: " + minimum);
    }

    @Override
    public void displayAverage(double average) {
        System.out.println("Середнє: " + average);
    }
}

// Інтерфейс для відображення результатів обчислення
interface EnergyDisplay {
    void displayKineticEnergy(double kineticEnergy);

    void displayPotentialEnergy(double potentialEnergy);

    void displayTotalEnergy(double totalEnergy);

    void displayTable(double kineticEnergy, double potentialEnergy, double totalEnergy);

    void displayMaximum(double maximum);

    void displayMinimum(double minimum);

    void displayAverage(double average);
}
