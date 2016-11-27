package kr.ac.sogang.creative.service;

import kr.ac.sogang.creative.domain.Participant;
import kr.ac.sogang.creative.repository.ConferenceRepository;
import kr.ac.sogang.creative.repository.ParticipantRepository;
import kr.ac.sogang.creative.util.URLHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ParticipantInitializer {

    private final ConferenceRepository conferenceRepository;
    private final ParticipantRepository participantRepository;

    @Value("classpath:data/participants.csv")
    private Resource participants;

    public void init() throws IOException {
        Reader in = new InputStreamReader(participants.getInputStream());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

        records.forEach(record -> {
            Optional<String> nameEn = Optional.of(record.get("nameEn"));
            Optional<String> nameKo = Optional.of(record.get("nameKo"));
            Optional<String> descriptionEn = Optional.ofNullable(record.get("descriptionEn"));
            Optional<String> descriptionKo = Optional.ofNullable(record.get("descriptionKo"));
            Optional<String> type = Optional.ofNullable(record.get("type"));
            Optional<String> email = Optional.ofNullable(record.get("email"));
            Optional<Integer> conferenceYear = Optional.ofNullable(Integer.parseInt(record.get("conferenceYear")));
            String imageIdx = record.get("imageIdx");

            saveParticipant(nameEn, nameKo, descriptionEn, descriptionKo, type, email, conferenceYear, imageIdx);
        });
    }

    private void saveParticipant(Optional<String> nameEn, Optional<String> nameKo, Optional<String> descriptionEn, Optional<String> descriptionKo, Optional<String> type, Optional<String> email, Optional<Integer> conferenceYear, String imageIdx) {
        Participant participant = participantRepository.findByName(nameEn.get())
                .stream().findFirst().orElse(new Participant());

        nameEn.ifPresent(value -> participant.getName().put(Locale.ENGLISH, value));
        nameKo.ifPresent(value -> participant.getName().put(Locale.KOREAN, value));
        descriptionEn.ifPresent(value -> participant.getDescription().put(Locale.ENGLISH, value));
        descriptionKo.ifPresent(value -> participant.getDescription().put(Locale.KOREAN, value));
        type.map(Participant.Type::valueOf).ifPresent(participant.getType()::add);
        email.ifPresent(participant::setEmail);
        conferenceYear.map(conferenceRepository::findByYear).ifPresent(participant::setConference);

        participant.setThumbImage(URLHelper.getURL("participants", imageIdx, "thumb", "jpg"));
        participant.setImage(URLHelper.getURL("participants", imageIdx, "profile", "jpg"));

        participantRepository.save(participant);
    }
}
