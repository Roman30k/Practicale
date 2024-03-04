import java.io.*;

class EnergyObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private double mass;
    private transient double height;
    private double velocity;

    public EnergyObject(double mass, double height, double velocity) {
        this.mass = mass;
        this.height = height;
        this.velocity = velocity;
    }

    public double calculateTotalEnergy() {
        double g = 9.8;
        double potentialEnergy = mass * g * height;
        double kineticEnergy = 0.5 * mass * velocity * velocity;
        return potentialEnergy + kineticEnergy;
    }

    @Override
    public String toString() {
        return "EnergyObject{" +
                "mass=" + mass +
                ", height=" + height +
                ", velocity=" + velocity +
                '}';
    }
}

public class SerializationDemo {
    public static void main(String[] args) {
        // Створення об'єкта
        EnergyObject energyObject = new EnergyObject(10.0, 20.0, 30.0);

        // Серіалізація об'єкта
        serializeObject("energyObject.ser", energyObject);

        // Десеріалізація об'єкта
        EnergyObject deserializedObject = deserializeObject("energyObject.ser");

        // Вивід інформації про десеріалізований об'єкт
        System.out.println("Десеріалізований об'єкт: " + deserializedObject);

        // Розрахунок та вивід повної енергії з десеріалізованого об'єкта
        double totalEnergy = deserializedObject.calculateTotalEnergy();
        System.out.println("Повна енергія: " + totalEnergy);
    }

    // Метод для серіалізації об'єкта
    private static void serializeObject(String fileName, EnergyObject obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(obj);
            System.out.println("Об'єкт серіалізовано і збережено в файл " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для десеріалізації об'єкта
    private static EnergyObject deserializeObject(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (EnergyObject) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
