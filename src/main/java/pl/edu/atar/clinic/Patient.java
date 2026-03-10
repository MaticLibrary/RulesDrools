package pl.edu.atar.clinic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;

public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private Double temperature = null;
    private Boolean isHealthy = null;
    private transient StringBuilder diagnosis = new StringBuilder();
    private transient StringBuilder logger = new StringBuilder();
    
    private Double saturation;          // saturacja krwi (%)
    private Integer heartRate;           // tętno (uderzenia/min)
    private Boolean cough;               // kaszel (true/false)
    private Integer systolicPressure;    // ciśnienie skurczowe (mmHg)
    private Integer respiratoryRate;      // częstość oddechów (oddechy/min)
    private Boolean chestPain;            // ból w klatce piersiowej
    private Boolean dyspnea;              // duszność

    private static final double MIN_TEMP = 0.0;
    private static final double MAX_TEMP = 45.0;

    public Patient(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Identyfikator pacjenta nie może być null.");
        this.id = id;
    }

    public Patient(Long id, String firstName, String lastName, Sex sex, Double temperature) {
        this(id);
        setFirstName(firstName);
        setLastName(lastName);
        setSex(sex);
        setTemperature(temperature);
    }

    // Gettery i settery dla nowych pól
    public Double getSaturation() {
        return saturation;
    }

    public void setSaturation(Double saturation) {
        if (saturation != null && (saturation < 0 || saturation > 100)) {
            throw new IllegalArgumentException("Saturacja musi mieścić się w zakresie 0-100%");
        }
        this.saturation = saturation;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        if (heartRate != null && heartRate <= 0) {
            throw new IllegalArgumentException("Tętno musi być dodatnie");
        }
        this.heartRate = heartRate;
    }

    public Boolean getCough() {
        return cough;
    }

    public void setCough(Boolean cough) {
        this.cough = cough;
    }

    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(Integer systolicPressure) {
        if (systolicPressure != null && systolicPressure <= 0) {
            throw new IllegalArgumentException("Ciśnienie skurczowe musi być dodatnie");
        }
        this.systolicPressure = systolicPressure;
    }

    public Integer getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Integer respiratoryRate) {
        if (respiratoryRate != null && respiratoryRate <= 0) {
            throw new IllegalArgumentException("Częstość oddechów musi być dodatnia");
        }
        this.respiratoryRate = respiratoryRate;
    }

    public Boolean getChestPain() {
        return chestPain;
    }

    public void setChestPain(Boolean chestPain) {
        this.chestPain = chestPain;
    }

    public Boolean getDyspnea() {
        return dyspnea;
    }

    public void setDyspnea(Boolean dyspnea) {
        this.dyspnea = dyspnea;
    }


    public Long getId() { return this.id; }
    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("Imię jest wymagane.");
        this.firstName = firstName;
    }
    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Nazwisko jest wymagane.");
        this.lastName = lastName;
    }
    public Sex getSex() { return sex; }
    public void setSex(Sex sex) {
        if (sex == null) throw new IllegalArgumentException("Płeć jest wymagana.");
        this.sex = sex;
    }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) {
        if (temperature != null && (temperature < MIN_TEMP || temperature > MAX_TEMP)) {
            throw new IllegalArgumentException(
                    String.format("Temperatura musi mieścić się w zakresie od %.1f°C do %.1f°C", MIN_TEMP, MAX_TEMP));
        }
        this.temperature = temperature;
    }
    public Boolean isHealthy() { return this.isHealthy; }
    public void setHealthy(Boolean isHealthy) { this.isHealthy = isHealthy; }

    // Metody logowania
    public synchronized void appendLogger(String message) {
        if (message != null && !message.isBlank()) {
            this.logger.append(message).append('\n');
        }
    }
    public synchronized String getLogger() { return this.logger.toString(); }
    public synchronized void setLogger(String message) {
        this.logger = (message == null) ? new StringBuilder() : new StringBuilder(message);
    }
    public synchronized String getDiagnosis() { return this.diagnosis.toString(); }
    public synchronized void setDiagnosis(String diagnosis) {
        this.diagnosis = (diagnosis == null) ? new StringBuilder() : new StringBuilder(diagnosis);
    }

    // Diagnoza jako lista
    // public synchronized void addDiagnosis(String diagnosis)

    @Override
    public String toString() {
        String reasoningLogger = (this.logger.isEmpty()) ? "\n" : "\n\nREGULY URUCHOMIONE W NASTĘPUJĄCEJ KOLEJNOŚCI:\n" + this.logger;
        String healthy = (this.isHealthy == null) ? "NIEZNANY" : this.isHealthy.toString();
        String diagnosisStr = (this.diagnosis.isEmpty()) ? "Brak" : this.diagnosis.toString();
        String vitals = String.format(
                "\n⮕ Parametry życiowe: Temp=%.1f°C, Sat=%s%%, HR=%s, RR=%s, BP=%s, Cough=%s, ChestPain=%s, Dyspnea=%s",
                temperature != null ? temperature : Double.NaN,
                saturation != null ? saturation : "?",
                heartRate != null ? heartRate : "?",
                respiratoryRate != null ? respiratoryRate : "?",
                systolicPressure != null ? systolicPressure : "?",
                cough != null ? cough : "?",
                chestPain != null ? chestPain : "?",
                dyspnea != null ? dyspnea : "?"
        );

        return "ID pacjenta: " + id +
                "\nImię i nazwisko oraz płeć: " + this.firstName + " " + this.lastName + " (" + this.sex + ")" +
                "\n⮕ Zdrowy: " + healthy +
                "\n⮕ Diagnoza: " + diagnosisStr +
                vitals +
                "\nReferencja obiektu: " + Integer.toHexString(System.identityHashCode(this)) +
                reasoningLogger;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.diagnosis = new StringBuilder();
        this.logger = new StringBuilder();
        if (this.firstName != null) setFirstName(this.firstName);
        if (this.lastName != null) setLastName(this.lastName);
        if (this.sex != null) setSex(this.sex);
        if (this.temperature != null) setTemperature(this.temperature);
        if (this.saturation != null) setSaturation(this.saturation);
        if (this.heartRate != null) setHeartRate(this.heartRate);
        if (this.systolicPressure != null) setSystolicPressure(this.systolicPressure);
        if (this.respiratoryRate != null) setRespiratoryRate(this.respiratoryRate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}