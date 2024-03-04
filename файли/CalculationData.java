import java.io.*;

// Класс для хранения параметров и результатов вычислений
public class CalculationData implements Serializable {
    private static final long serialVersionUID = 1L;

    private double mass;
    private double height;
    private double velocity;
    private double potentialEnergy;
    private double kineticEnergy;
    private double totalEnergy;

    // Конструктор класса
    public CalculationData(double mass, double height, double velocity) {
        this.mass = mass;
        this.height = height;
        this.velocity = velocity;
        calculateEnergy(); // Рассчитываем энергию при создании объекта
    }

    // Метод для расчета энергии
    private void calculateEnergy() {
        double g = 9.8;  // ускорение свободного падения на поверхности Земли
        potentialEnergy = mass * g * height;
        kineticEnergy = 0.5 * mass * velocity * velocity;
        totalEnergy = potentialEnergy + kineticEnergy;
    }

    // Геттеры для получения значений
    public double getMass() {
        return mass;
    }

    public double getHeight() {
        return height;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getPotentialEnergy() {
        return potentialEnergy;
    }

    public double getKineticEnergy() {
        return kineticEnergy;
    }

    public double getTotalEnergy() {
        return totalEnergy;
    }

    // Метод для сериализации объекта в файл
    public void serialize(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Объект успешно сериализован в файл: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для десериализации объекта из файла
    public static CalculationData deserialize(String fileName) {
        CalculationData calculationData = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            calculationData = (CalculationData) ois.readObject();
            System.out.println("Объект успешно десериализован из файла: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return calculationData;
    }

    public static void main(String[] args) {
        // Пример использования класса и сериализации/десериализации
        CalculationData data = new CalculationData(10.0, 20.0, 30.0);
        data.serialize("calculation_data.ser");

        CalculationData deserializedData = CalculationData.deserialize("calculation_data.ser");
        if (deserializedData != null) {
            System.out.println("Десериализованные данные:");
            System.out.println("Масса: " + deserializedData.getMass());
            System.out.println("Высота: " + deserializedData.getHeight());
            System.out.println("Скорость: " + deserializedData.getVelocity());
            System.out.println("Потенциальная энергия: " + deserializedData.getPotentialEnergy());
            System.out.println("Кинетическая энергия: " + deserializedData.getKineticEnergy());
            System.out.println("Полная энергия: " + deserializedData.getTotalEnergy());
        }
    }
}
