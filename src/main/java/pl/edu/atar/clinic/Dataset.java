package pl.edu.atar.clinic;

import java.util.ArrayList;
import java.util.List;
import static pl.edu.atar.clinic.Sex.*;

public class Dataset {

    public Dataset() {
    }

    public List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<>();

        // Pacjent 1: Anna (zapalenie płuc) - temperatura >38, saturacja <95, tętno >100, kaszel
        Patient p1 = new Patient(1L, "Anna", "Kowalewska", FEMALE, 39.5);
        p1.setSaturation(94.0);
        p1.setHeartRate(110);
        p1.setCough(true);
        patients.add(p1);

        // Pacjent 2: Jacek (wstrząs septyczny) - temp >38, saturacja <92, tętno >90, oddechy >20
        Patient p2 = new Patient(2L, "Jacek", "Nowak", MALE, 39.0);
        p2.setSaturation(90.0);
        p2.setHeartRate(120);
        p2.setRespiratoryRate(25);
        patients.add(p2);

        // Pacjent 3: Ewa (wstrząs kardiogenny) - ciśnienie skurczowe <90, saturacja <92, tętno >100, ból w klatce
        Patient p3 = new Patient(3L, "Ewa", "Wiśniowa", FEMALE, 36.6);
        p3.setSystolicPressure(85);
        p3.setSaturation(91.0);
        p3.setHeartRate(110);
        p3.setChestPain(true);
        patients.add(p3);

        // Pacjent 4: Karol (zdrowy) - żadne objawy nie pasują
        Patient p4 = new Patient(4L, "Karol", "Gruszka", MALE, 36.9);
        p4.setSaturation(98.0);
        p4.setHeartRate(70);
        p4.setRespiratoryRate(16);
        p4.setSystolicPressure(120);
        patients.add(p4);

        // Pacjent 5: Ewelina (brak danych) - tylko temperatura, reszta null → powinna być zdrowa (tylko domniemanie)
        Patient p5 = new Patient(5L, "Ewelina", "Jabłoń", FEMALE, 36.6);
        patients.add(p5);

        // Pacjent 6: Tomasz (gorączka) - temperatura >37.3, reszta w normie → powinien dostać "Gorączka!"
        Patient p6 = new Patient(6L, "Tomasz", "Wiśniewski", MALE, 38.5);
        p6.setSaturation(98.0);
        p6.setHeartRate(80);
        p6.setRespiratoryRate(16);
        p6.setSystolicPressure(120);
        p6.setCough(false);
        p6.setChestPain(false);
        p6.setDyspnea(false);
        patients.add(p6);

        // Pacjent 7: Katarzyna (zapalenie płuc bez kaszlu) - ma temp 39, saturacja 94, tętno 110, ale brak kaszlu → nie spełnia zapalenia płuc, ale ma gorączkę → "Gorączka!"
        Patient p7 = new Patient(7L, "Katarzyna", "Nowicka", FEMALE, 39.0);
        p7.setSaturation(94.0);
        p7.setHeartRate(110);
        p7.setCough(false);
        patients.add(p7);

        // Pacjent 8: Piotr (wstrząs septyczny, ale saturacja 93% - powyżej progu 92) - ma temp 39, tętno 100, oddechy 22, ale saturacja 93 → nie spełnia wstrząsu, ma gorączkę → "Gorączka!"
        Patient p8 = new Patient(8L, "Piotr", "Zieliński", MALE, 39.0);
        p8.setSaturation(93.0);
        p8.setHeartRate(100);
        p8.setRespiratoryRate(22);
        patients.add(p8);

        // Pacjent 9: Magdalena (wstrząs kardiogenny, ale tętno 80) - ciśnienie 85, saturacja 91, ból w klatce, ale tętno 80 (za niskie dla >100 i za wysokie dla <50) → nie spełnia wstrząsu, temp 36.7 → brak gorączki → powinna być zdrowa
        Patient p9 = new Patient(9L, "Magdalena", "Kamińska", FEMALE, 36.7);
        p9.setSystolicPressure(85);
        p9.setSaturation(91.0);
        p9.setHeartRate(80);
        p9.setChestPain(true);
        patients.add(p9);

        // Pacjent 10: Jan (zapalenie płuc) - spełnia zapalenie płuc (temp 39.2, saturacja 94, tętno 110, kaszel), dodatkowo ma gorączkę → powinien dostać "Podejrzenie zapalenia płuc" (priorytet 300)
        Patient p10 = new Patient(10L, "Jan", "Kowalczyk", MALE, 39.2);
        p10.setSaturation(94.0);
        p10.setHeartRate(110);
        p10.setCough(true);
        p10.setRespiratoryRate(18);
        p10.setSystolicPressure(110);
        patients.add(p10);

        // Pacjent 11: Maria (wstrząs kardiogenny) - spełnia wstrząs (ciśnienie 85, saturacja 91, tętno 110, ból w klatce), dodatkowo gorączka → powinna dostać "Podejrzenie wstrząsu kardiogennego" (priorytet 300)
        Patient p11 = new Patient(11L, "Maria", "Lewandowska", FEMALE, 38.5);
        p11.setSystolicPressure(85);
        p11.setSaturation(91.0);
        p11.setHeartRate(110);
        p11.setChestPain(true);
        p11.setCough(false);
        patients.add(p11);

        // Pacjent 12: Paweł (wstrząs septyczny i zapalenie płuc) - spełnia obie reguły (temp 39.5, saturacja 90, tętno 120, oddechy 25, kaszel) → obie mają priorytet 300, więc wynik zależy od kolejności wykonania. Komentarz: wstrząs septyczny lub zapalenie płuc
        Patient p12 = new Patient(12L, "Paweł", "Wójcik", MALE, 39.5);
        p12.setSaturation(90.0);
        p12.setHeartRate(120);
        p12.setRespiratoryRate(25);
        p12.setCough(true);
        p12.setSystolicPressure(100);
        patients.add(p12);

        return patients;
    }
}