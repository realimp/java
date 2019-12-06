public class Main {
    public static void main(String[] args) {
        double[] patientsTemps = new double[30];

        for (int i = 0; i < 30; i++)
        {
            patientsTemps[i] = ((double)(320 + Math.round(80 * Math.random())) / 10);
        }

        double totalTemp = 0.0;
        int healthyPatientsCount = 0;

        for (double patientTemp : patientsTemps){
            System.out.print(patientTemp + " ");

            totalTemp += patientTemp;

            if (patientTemp >= 36.2 && patientTemp <= 36.2)
            {
                healthyPatientsCount++;
            }
        }

        double averageTemp = ((double)Math.round((totalTemp/patientsTemps.length) * 10)) / 10;

        System.out.println("\nAverage temperature - " + averageTemp);
        System.out.println("Number of healthy patients - " + healthyPatientsCount);
    }
}
