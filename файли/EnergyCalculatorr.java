public class EnergyCalculatorr {
    private double mass;
    private double height;
    private double velocity;
    private static final double GRAVITY = 9.8;

    public EnergyCalculatorr(double mass, double height, double velocity) {
        this.mass = mass;
        this.height = height;
        this.velocity = velocity;
    }

    public double calculatePotentialEnergy() {
        return mass * GRAVITY * height;
    }

    public double calculateKineticEnergy() {
        return 0.5 * mass * velocity * velocity;
    }

    public double calculateTotalEnergy() {
        return calculatePotentialEnergy() + calculateKineticEnergy();
    }

    public String getTotalEnergyBinaryRepresentation() {
        long bits = Double.doubleToLongBits(calculateTotalEnergy());
        return Long.toBinaryString(bits);
    }

    public static void main(String[] args) {
        EnergyCalculatorr calculator = new EnergyCalculatorr(10.0, 20.0, 30.0);

        System.out.println("Полная энергия: " + calculator.calculateTotalEnergy());
        System.out.println("Двоичное представление полной энергии: " + calculator.getTotalEnergyBinaryRepresentation());
    }
}