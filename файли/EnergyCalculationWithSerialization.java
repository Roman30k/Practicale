import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EnergyCalculationWithSerialization {
    public static void main(String[] args) {
        double mass = 10.0;   // маса в кг
        double height = 20.0; // висота в метрах
        double velocity = 30.0; // швидкість в м/с
        double g = 9.8;       // прискорення вільного падіння на поверхні Землі

        double potentialEnergy = mass * g * height;
        double kineticEnergy = 0.5 * mass * velocity * velocity;
        double totalEnergy = potentialEnergy + kineticEnergy;

        // Збереження результатів обчислень у колекції
        List<Double> energyResults = new ArrayList<>();
        energyResults.add(potentialEnergy);
        energyResults.add(kineticEnergy);
        energyResults.add(totalEnergy);

        // Збереження колекції у файл
        saveResultsToFile("energyResults.ser", energyResults);

        // Відновлення колекції з файлу
        List<Double> restoredResults = loadResultsFromFile("energyResults.ser");

        System.out.println("Потенційна енергія: " + restoredResults.get(0));
        System.out.println("Кінетична енергія: " + restoredResults.get(1));
        System.out.println("Повна енергія: " + restoredResults.get(2));
    }

    // Метод для збереження колекції у файл
    private static void saveResultsToFile(String filename, List<Double> results) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(results);
            System.out.println("Результати збережено у файл " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для відновлення колекції з файлу
    private static List<Double> loadResultsFromFile(String filename) {
        List<Double> results = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            results = (List<Double>) ois.readObject();
            System.out.println("Результати відновлено з файлу " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return results;
    }
}
