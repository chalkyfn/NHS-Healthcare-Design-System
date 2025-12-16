package data;


import models.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DataLoader {

    public static List<Facility> loadFacilities(String path) {

        List<Facility> facilities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {

                String[] f = CSVUtils.split(line);

                Facility facility = new Facility(
                        CSVUtils.clean(f[0]),
                        CSVUtils.clean(f[1]),
                        CSVUtils.clean(f[2]),
                        CSVUtils.clean(f[3]),
                        CSVUtils.clean(f[4]),
                        CSVUtils.clean(f[5]),
                        CSVUtils.clean(f[6]),
                        CSVUtils.clean(f[7]),
                        CSVUtils.clean(f[8]),
                        Integer.parseInt(CSVUtils.clean(f[9])),
                        CSVUtils.parseList(f[10])
                );

                facilities.add(facility);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return facilities;
    }


    // 🔥 Helper to find a facility by ID
    private static Facility findFacility(List<Facility> facilities, String id) {
        return facilities.stream()
                .filter(f -> f.getFacilityId().equals(id))
                .findFirst()
                .orElse(null);
    }



    // ----------------------------------------------
    // LOAD PATIENTS
    // ----------------------------------------------
    public static List<Patient> loadPatients(String path, List<Facility> facilities) {

        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {

                String[] p = CSVUtils.split(line);

                Facility registeredFacility = findFacility(facilities, CSVUtils.clean(p[13]));

                Patient patient = new Patient(
                        CSVUtils.clean(p[0]),
                        CSVUtils.clean(p[1]),
                        CSVUtils.clean(p[2]),
                        CSVUtils.parseDate(p[3]),
                        CSVUtils.clean(p[5]),
                        CSVUtils.clean(p[6]),
                        CSVUtils.clean(p[7]),
                        CSVUtils.clean(p[8]),
                        CSVUtils.clean(p[9]),
                        CSVUtils.clean(p[4]), // nhs number
                        CSVUtils.clean(p[10]),
                        CSVUtils.clean(p[11]),
                        CSVUtils.parseDate(p[12]),
                        registeredFacility
                );

                patients.add(patient);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }


    // ----------------------------------------------
    // LOAD CLINICIANS
    // ----------------------------------------------
    public static List<Clinician> loadClinicians(String path, List<Facility> facilities) {

        List<Clinician> clinicians = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {

                String[] c = CSVUtils.split(line);

                String id = CSVUtils.clean(c[0]);
                String first = CSVUtils.clean(c[1]);
                String last = CSVUtils.clean(c[2]);
                String role = CSVUtils.clean(c[3]);
                String qualification = CSVUtils.clean(c[4]);
                Facility facility = findFacility(facilities, CSVUtils.clean(c[5]));

                Clinician clinician;

                switch (role.toLowerCase()) {
                    case "gp":
                        clinician = new GP(id, first, last, qualification, facility);
                        break;
                    case "nurse":
                        clinician = new Nurse(id, first, last, qualification, facility);
                        break;
                    default:
                        clinician = new SpecialistDoctor(id, first, last, qualification, facility);
                        break;
                }

                clinicians.add(clinician);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clinicians;
    }


    // 🔥 Helper to find patient/clinician for appointments/referrals
    private static Patient findPatient(List<Patient> patients, String id) {
        return patients.stream()
                .filter(p -> p.getPersonId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private static Clinician findClinician(List<Clinician> clinicians, String id) {
        return clinicians.stream()
                .filter(c -> c.getClinicianId() != null)
                .filter(c -> c.getClinicianId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // ----------------------------------------------
    // LOAD APPOINTMENTS
    // ----------------------------------------------
    public static List<Appointment> loadAppointments(
            String path,
            List<Patient> patients,
            List<Clinician> clinicians,
            List<Facility> facilities) {

        List<Appointment> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {

                String[] a = CSVUtils.split(line);

                Appointment appt = new Appointment(
                        CSVUtils.clean(a[0]),
                        findPatient(patients, CSVUtils.clean(a[1])),
                        findClinician(clinicians, CSVUtils.clean(a[2])),
                        findFacility(facilities, CSVUtils.clean(a[3])),
                        CSVUtils.parseDate(a[4]),
                        CSVUtils.parseTime(a[5]),
                        Integer.parseInt(CSVUtils.clean(a[6])),
                        CSVUtils.clean(a[7]),
                        CSVUtils.clean(a[8]),
                        CSVUtils.clean(a[9]),
                        CSVUtils.clean(a[10]),
                        CSVUtils.parseDate(a[11]),
                        CSVUtils.parseDate(a[12])
                );

                list.add(appt);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // ----------------------------------------------
    // LOAD REFERRALS
    // ----------------------------------------------
    public static List<Referral> loadReferrals(
            String path,
            List<Patient> patients,
            List<Clinician> clinicians,
            List<Facility> facilities,
            List<Appointment> appointments) {

        List<Referral> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {

                String[] r = CSVUtils.split(line);

                Referral referral = new Referral(
                        CSVUtils.clean(r[0]),
                        findPatient(patients, CSVUtils.clean(r[1])),
                        findClinician(clinicians, CSVUtils.clean(r[2])),
                        findClinician(clinicians, CSVUtils.clean(r[3])),
                        findFacility(facilities, CSVUtils.clean(r[4])),
                        findFacility(facilities, CSVUtils.clean(r[5])),
                        CSVUtils.parseDate(r[6]),
                        CSVUtils.clean(r[7]),
                        CSVUtils.clean(r[8]),
                        CSVUtils.clean(r[9]),
                        CSVUtils.parseList(r[10]),
                        CSVUtils.clean(r[11]),
                        CSVUtils.clean(r[13]),
                        CSVUtils.parseDate(r[14]),
                        CSVUtils.parseDate(r[15]),
                        findAppointment(appointments, CSVUtils.clean(r[12]))
                );

                list.add(referral);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static Appointment findAppointment(List<Appointment> list, String id) {
        return list.stream()
                .filter(a -> a.getAppointmentId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
