package kr.ac.sogang.creative.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class DataInitializer {

    private final ConferenceInitializer conferenceInitializer;
    private final ParticipantInitializer participantInitializer;
    private final ProgramInitializer programInitializer;

    @EventListener
    @Transactional
    public void init(ApplicationReadyEvent event) throws IOException {
        conferenceInitializer.init();
        participantInitializer.init();
        programInitializer.init();
    }
}
