package data;

import models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    // =====================================================
    // FACILITIES
    // =====================================================
    public static List<Facility> loadFacilities(String path) {

        List<Facility> facilities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine(); // header
            String line;

            while ((line = br.readLine()) != null) {
                String[] f = CSVUtils.split(line);

                facilities.add(new Facility(
                        f[0],
                        f[1],
                        f[2],
                        f[3],
                        f[4],
                        f[5],
                        f[6],
                        f[7],
                        f[8],
                        Integer.parseInt(f[9]),
                        CSVUtils.parseList(f[10])
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return facilities;
    }


    // PATIENTS
    public static List<Patient> loadPatients(
            String path,
            List<Facility> facilities) {

        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = CSVUtils.split(line);
                if (p.length < 13) {
                    System.out.println("Skipping invalid patient row: " + line);
                    continue;
                }

                patients.add(new Patient(
                        p[0],
                        p[1],
                        p[2],
                        CSVUtils.parseDate(p[3]),
                        p[5],
                        p[6],
                        p[7],
                        p[8],
                        p[10],
                        p[11],
                        p[4],
                        p[9],
                        CSVUtils.parseDate(p[12]),
                        p[13]
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }


    // CLINICIANS
    public static List<Clinician> loadClinicians(
            String path,
            List<Facility> facilities) {

        List<Clinician> clinicians = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] c = CSVUtils.split(line);

                Facility facility = findFacility(facilities, c[5]);

                Clinician clinician;
                String role = c[3].toLowerCase();

                if (role.equals("gp")) {
                    clinician = new GP(c[0], c[1], c[2], c[4],facility);
                } else if (role.equals("nurse")) {
                    clinician = new Nurse(c[0], c[1], c[2], c[4], facility);
                } else {
                    clinician = new SpecialistDoctor(c[0], c[1], c[2], c[4], facility);
                }

                clinicians.add(clinician);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clinicians;
    }


    // APPOINTMENTS
    public static List<Appointment> loadAppointments(
            String path,
            List<Patient> patients,
            List<Clinician> clinicians,
            List<Facility> facilities) {

        List<Appointment> appointments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] a = CSVUtils.split(line);
                if (a.length < 13) {
                    System.out.println("Skipping invalid patient row: " + line);
                    continue;
                }

                Patient patient = findPatient(patients, a[1]);
                Clinician clinician = findClinician(clinicians, a[2]);
                Facility facility = findFacility(facilities, a[3]);

                if (patient == null || clinician == null) continue;

                appointments.add(new Appointment(
                        a[0],
                        patient,
                        clinician,
                        facility,
                        CSVUtils.parseDate(a[4]),
                        CSVUtils.parseTime(a[5]),
                        Integer.parseInt(a[6]),
                        a[7],
                        a[8],
                        a[9],
                        a[10],
                        CSVUtils.parseDate(a[11]),
                        CSVUtils.parseDate(a[12])
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointments;
    }


    // PRESCRIPTIONS
        public static List<Prescription> loadPrescriptions(
                String filePath,
                List<Patient> patients,
                List<Clinician> clinicians,
                List<Appointment> appointments
        ) {

            List<Prescription> prescriptions = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

                String line = br.readLine(); // skip header

                while ((line = br.readLine()) != null) {

                    String[] data = CSVUtils.split(line);



                    String prescriptionId = data[0];
                    String patientId = data[1];
                    String clinicianId = data[2];
                    String appointmentId = data[3];
                    LocalDate prescription_date = CSVUtils.parseDateSafe(data[4]);
                    String medicationName = data[5];
                    String dosage = data[6];
                    String frequency = data[7];
                    int duration_days = CSVUtils.parseIntSafe(data[8]);
                    String quantity = data[9];
                    String instructions = data[10];
                    String pharmacyName = data[11];
                    String status = data[12];

                    LocalDate issueDate = CSVUtils.parseDateSafe(data[13]);
                    LocalDate collectionDate =  null;

                    // using this as a temperary fix cause the data loading keeps failing
                    if (data.length > 14 && !data[14].isBlank()) {
                        collectionDate = LocalDate.parse(data[14]);
                    }


                    Patient patient = patients.stream()
                            .filter(p -> p.getPatientId().equals(patientId))
                            .findFirst()
                            .orElse(null);

                    Clinician clinician = clinicians.stream()
                            .filter(c -> c.getClinicianId().equals(clinicianId))
                            .findFirst()
                            .orElse(null);

                    Appointment appointment = appointments.stream()
                            .filter(c -> c.getAppointmentId().equals(appointmentId))
                            .findFirst()
                            .orElse(null);

                    if (patient == null || clinician == null) {
                        continue;
                    }


                    prescriptions.add(new Prescription(
                            prescriptionId,
                            patient,
                            clinician,
                            appointment,
                            prescription_date,
                            medicationName,
                            dosage,
                            frequency,
                            duration_days,
                            quantity,
                            instructions,
                            pharmacyName,
                            status,
                            issueDate,
                            collectionDate

                    ));
                }

            } catch (Exception e) {
                System.out.println("Error loading prescriptions");
                e.printStackTrace();
            }

            return prescriptions;
        }


    // REFERRALS
    public static List<Referral> loadReferrals(
            String filePath,
            List<Patient> patients,
            List<Clinician> clinicians,
            List<Facility> facilities,
            List<Appointment> appointments
    ) {

        List<Referral> referrals = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line = br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] data = CSVUtils.split(line);

                if (data.length < 15) {
                    System.out.println("Skipping invalid referral row: " + line);
                    continue;
                }

                String referralId = data[0];
                String patientId = data[1];
                String referringClinicianId = data[2];
                String referredClinicianId = data[3];
                String referringFacilityId = data[4];
                String referredFacilityId = data[5];

                LocalDate referralDate = CSVUtils.parseDateSafe(data[6]);
                String urgency = data[7];
                String referralReason = data[8];
                String clinicalSummary = data[9];
                String requestedInvestigations = data[10];
                String status = data[11];
                String appointmentId = data[12];
                String notes = data[13].replace("\"", "").trim();
                LocalDate createdDate = CSVUtils.parseDateSafe(data[14]);
                LocalDate lastUpdated = CSVUtils.parseDateSafe(data[15]);

                Patient patient = patients.stream()
                        .filter(p -> p.getPatientId().equals(patientId))
                        .findFirst()
                        .orElse(null);

                Clinician referringClinician = clinicians.stream()
                        .filter(c -> c.getClinicianId().equals(referringClinicianId))
                        .findFirst()
                        .orElse(null);

                Clinician referredClinician = clinicians.stream()
                        .filter(c -> c.getClinicianId().equals(referredClinicianId))
                        .findFirst()
                        .orElse(null);

                Facility referringFacility = facilities.stream()
                        .filter(f -> f.getFacilityId().equals(referringFacilityId))
                        .findFirst()
                        .orElse(null);

                Facility referredFacility = facilities.stream()
                        .filter(f -> f.getFacilityId().equals(referredFacilityId))
                        .findFirst()
                        .orElse(null);

                Appointment appointment = appointments.stream()
                        .filter(c -> c.getAppointmentId().equals(appointmentId))
                        .findFirst()
                        .orElse(null);


                if (patient == null || referringClinician == null || referredClinician == null) {
                    continue;
                }

                referrals.add(new Referral(
                        referralId,
                        patient,
                        referringClinician,
                        referredClinician,
                        referringFacility,
                        referredFacility,
                        referralDate,
                        urgency,
                        referralReason,
                        clinicalSummary,
                        requestedInvestigations,
                        status,
                        appointment,
                        notes,
                        createdDate,
                        lastUpdated
                ));
            }

        } catch (Exception e) {
            System.out.println("Error loading referrals");
            e.printStackTrace();
        }

        return referrals;
    }






    // Helpers
    private static Patient findPatient(List<Patient> patients, String id) {
        for (Patient p : patients) {
            if (p.getPatientId().equals(id)) return p;
        }
        return null;
    }

    private static Clinician findClinician(List<Clinician> clinicians, String id) {
        for (Clinician c : clinicians) {
            if (c.getClinicianId() != null &&
                    c.getClinicianId().equals(id)) return c;
        }
        return null;
    }

    private static Facility findFacility(List<Facility> facilities, String id) {
        for (Facility f : facilities) {
            if (f.getFacilityId().equals(id)) return f;
        }
        return null;
    }

    private static Appointment findAppointment(List<Appointment> appointments, String id) {
        for (Appointment a : appointments) {
            if (a.getAppointmentId().equals(id)) return a;
        }
        return null;
    }



}
