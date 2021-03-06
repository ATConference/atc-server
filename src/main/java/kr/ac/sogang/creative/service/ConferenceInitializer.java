package kr.ac.sogang.creative.service;

import kr.ac.sogang.creative.domain.Conference;
import kr.ac.sogang.creative.repository.ConferenceRepository;
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
public class ConferenceInitializer {

    private final ConferenceRepository conferenceRepository;

    @Value("classpath:data/conferences.csv")
    private Resource conferences;

    public void init() throws IOException {
        Reader in = new InputStreamReader(conferences.getInputStream());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

        records.forEach(record -> {
            Optional<String> nameEn = Optional.of(record.get("nameEn"));
            Optional<String> nameKo = Optional.of(record.get("nameKo"));
            Optional<String> descriptionEn = Optional.of(record.get("descriptionEn"));
            Optional<String> descriptionKo = Optional.of(record.get("descriptionKo"));
            Optional<Integer> year = Optional.of(Integer.parseInt(record.get("year")));
            String imageIdx = record.get("imageIdx");
            Integer imageCnt = Integer.parseInt(record.get("imageCnt"));

            saveConference(nameEn, nameKo, descriptionEn, descriptionKo, year, imageIdx, imageCnt);
        });
    }

    private void saveConference(Optional<String> nameEn, Optional<String> nameKo, Optional<String> descriptionEn, Optional<String> descriptionKo, Optional<Integer> year, String imageIdx, Integer imageCnt) {
        Conference conference = new Conference();

        nameEn.ifPresent(value -> conference.getName().put(Locale.ENGLISH, value));
        nameKo.ifPresent(value -> conference.getName().put(Locale.KOREAN, value));
        descriptionEn.ifPresent(value -> conference.getDescription().put(Locale.ENGLISH, value));
        descriptionKo.ifPresent(value -> conference.getDescription().put(Locale.KOREAN, value));
        year.ifPresent(conference::setYear);

        conference.setThumbImage(URLHelper.getURL("conferences", imageIdx, "thumb", "jpg"));
        conference.setImages(URLHelper.getURL("conferences", imageIdx, 1, imageCnt, "jpg"));

        conferenceRepository.save(conference);
    }
}
