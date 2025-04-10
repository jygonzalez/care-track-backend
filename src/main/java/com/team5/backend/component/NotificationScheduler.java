package com.team5.backend.component;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.team5.backend.entity.MedicationSchedule;
import com.team5.backend.repository.MedicationScheduleRepository;
import com.team5.backend.service.EmailService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final EmailService emailService;
    private final MedicationScheduleRepository medicationScheduleRepository;

    @Scheduled(cron = "0 0/5 * * * ?") // Runs every 5 minutes
    public void sendScheduledNotifications() {
        sendMedicationReminders();
        notifyCaregiversOfTakenDoses();
        notifyCaregiversOfMissedDoses();
    }

    private void sendMedicationReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusMinutes(15);
        List<MedicationSchedule> schedules = medicationScheduleRepository.findByScheduledTimeBetween(now, later);

        for (MedicationSchedule schedule : schedules) {
            emailService.sendEmail(
                    schedule.getPatient().getEmail(),
                    "Medication Reminder",
                    "Please take your " + schedule.getMedication().getName() + " at " + schedule.getScheduled_time());
        }
    }

    private void notifyCaregiversOfTakenDoses() {
        List<MedicationSchedule> completedDoses = medicationScheduleRepository.findNotNotifiedTakenDoses();

        for (MedicationSchedule schedule : completedDoses) {
            String caregiverEmail = schedule.getPatient().getCaregiver_email();
            String subject = "Medication Taken: " + schedule.getMedication().getName();
            String body = "The patient " + schedule.getPatient().getFirstname() + " "
                    + schedule.getPatient().getLastname() +
                    " has taken the medication " + schedule.getMedication().getName() +
                    " at " + schedule.getTime_taken() + ".";

            emailService.sendEmail(caregiverEmail, subject, body);

            // Mark the notification as sent to avoid duplicate emails
            schedule.setNotification_sent(true);
            medicationScheduleRepository.save(schedule);
        }
    }

    private void notifyCaregiversOfMissedDoses() {
        LocalDateTime now = LocalDateTime.now();

        List<MedicationSchedule> missedDoses = medicationScheduleRepository
                .findNotNotifiedMissedDoses(now);

        for (MedicationSchedule schedule : missedDoses) {
            String caregiverEmail = schedule.getPatient().getCaregiver_email();
            String subject = "Missed Medication: " + schedule.getMedication().getName();
            String body = "The patient " + schedule.getPatient().getFirstname() + " "
                    + schedule.getPatient().getLastname() +
                    " missed the medication " + schedule.getMedication().getName() +
                    " scheduled for " + schedule.getScheduled_time() + ".";

            emailService.sendEmail(caregiverEmail, subject, body);

            // Mark the notification as sent to avoid duplicate emails
            schedule.setNotification_sent(true);
            medicationScheduleRepository.save(schedule);
        }
    }
}
