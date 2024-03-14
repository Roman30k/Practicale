import java.io.*;

public class EnergySerializationDemo {
    public static void main(String[] args) {
        EnergyResult result = new EnergyResult(60, 10, 20, 3000, 11760, 14760);

        // Saving object state to file
        try {
            FileOutputStream fileOut = new FileOutputStream("energy_result.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(result);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in energy_result.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieving object state from file
        try {
            FileInputStream fileIn = new FileInputStream("energy_result.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            EnergyResult restoredResult = (EnergyResult) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Deserialized data:");
            System.out.println("Kinetic Energy: " + restoredResult.getKineticEnergy());
            System.out.println("Potential Energy: " + restoredResult.getPotentialEnergy());
            System.out.println("Total Energy: " + restoredResult.getTotalEnergy());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
