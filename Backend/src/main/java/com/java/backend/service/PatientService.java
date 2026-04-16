package com.java.backend.service;

import com.java.backend.dto.PatientDTO;
import com.java.backend.exception.EmailAlreadyUsedException;
import com.java.backend.exception.UserNotFoundException;
import com.java.backend.mapper.PatientMapper;
import com.java.backend.model.Doctor;
import com.java.backend.model.Patient;
import com.java.backend.repository.DoctorRepository;
import com.java.backend.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PatientService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper,DoctorRepository doctorRepository){
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.patientMapper = patientMapper;
    }

    public Patient getPatientByEmail(String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if(patient.isEmpty())
            throw new UserNotFoundException("User With Email = "+email+" Not Found");
        return patient.get();
    }

    public Map<String,String> registerNewPatient(@Valid  PatientDTO patientDTO){
        if(patientRepository.existsByEmail(patientDTO.getEmail())){
            throw new EmailAlreadyUsedException("Email already in use!");
        }
        Patient patient = patientRepository.save( patientMapper.toPatientEntity(patientDTO,"SAVE",null));

        Map<String, String> map = new HashMap<>();
        map.put("message","Registered Successfully");
        map.put("role",patient.getRole().getName());
        return map;
    }

    public String updatePatient(@Valid PatientDTO patientDTO, Patient existingPatient) {
        Patient patient  = patientMapper.toPatientEntity(patientDTO,"EDIT",existingPatient);

        patientRepository.save(patient);// return will never be null.
        return "Patient Saved successfully.";
    }

    public void bookAppointment(String patientEmail, Long doctorId) {
        Optional<Patient> patient = patientRepository.findByEmail(patientEmail);
        if(patient.isEmpty()){
            throw new UserNotFoundException("No such Patient with this Email: "+patientEmail);
        }

        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if(doctor.isEmpty())
            throw new UserNotFoundException("No Doctors with id: "+ doctorId);

        doctor.get().getPatientList().add(patient.get());
        patient.get().setDoctor(doctor.get());
        patient.get().setBookingDateAndTime(LocalDateTime.now());
        doctorRepository.save(doctor.get());
        patientRepository.save(patient.get());
    }
}
