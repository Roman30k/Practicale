public class EnergyCalculatorSolver {
    private EnergyResult energyResult;

    public EnergyCalculatorSolver(double mass, double velocity, double height) {
        double kineticEnergy = 0.5 * mass * Math.pow(velocity, 2);
        double potentialEnergy = mass * 9.8 * height;
        double totalEnergy = kineticEnergy + potentialEnergy;

        this.energyResult = new EnergyResult(mass, velocity, height, kineticEnergy, potentialEnergy, totalEnergy);
    }

    // Getter for energy result
    public EnergyResult getEnergyResult() {
        return energyResult;
    }
}
