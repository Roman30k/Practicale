import java.io.Serializable;

public class EnergyResult implements Serializable {
    private double mass;
    private double velocity;
    private double height;
    private double kineticEnergy;
    private double potentialEnergy;
    private double totalEnergy;

    public EnergyResult(double mass, double velocity, double height, double kineticEnergy, double potentialEnergy, double totalEnergy) {
        this.mass = mass;
        this.velocity = velocity;
        this.height = height;
        this.kineticEnergy = kineticEnergy;
        this.potentialEnergy = potentialEnergy;
        this.totalEnergy = totalEnergy;
    }

    // Getters for energy values
    public double getKineticEnergy() {
        return kineticEnergy;
    }

    public double getPotentialEnergy() {
        return potentialEnergy;
    }

    public double getTotalEnergy() {
        return totalEnergy;
    }
}
