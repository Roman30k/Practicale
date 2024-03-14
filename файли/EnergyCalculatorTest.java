public class EnergyCalculatorTest {
    public static void main(String[] args) {
        // Створення об'єкту для тестування
        TableEnergyDisplay tableEnergyDisplay = new DetailedTableEnergyDisplay();

        // Вхідні дані для тестування
        double mass = 60.0;
        double velocity = 10.0;
        double height = 20.0;

        double kineticEnergy = 0.5 * mass * Math.pow(velocity, 2);
        double potentialEnergy = mass * 9.8 * height;
        double totalEnergy = kineticEnergy + potentialEnergy;

        // Виклик методу відображення результатів обчислень
        tableEnergyDisplay.displayTable(kineticEnergy, potentialEnergy, totalEnergy);
    }
}
