package kr.ac.sogang.creative.service;

import kr.ac.sogang.creative.domain.Conference;
import kr.ac.sogang.creative.repository.ConferenceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ConferenceInitializer {

    private final ConferenceRepository conferenceRepository;

    public void init() throws IOException {
        Reader in = new FileReader("data/conferences.csv");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

        records.forEach(record -> {
            Optional<String> nameEn = Optional.of(record.get("nameEn"));
            Optional<String> nameKo = Optional.of(record.get("nameKo"));
            Optional<String> descriptionEn = Optional.of(record.get("descriptionEn"));
            Optional<String> descriptionKo = Optional.of(record.get("descriptionKo"));
            Optional<Integer> year = Optional.of(Integer.parseInt(record.get("year")));
            Optional<String> thumbImage = Optional.of(record.get("thumbImage"));
            Optional<String> images = Optional.ofNullable(record.get("images"));

            saveConference(nameEn, nameKo, descriptionEn, descriptionKo, year, thumbImage, images);
        });
    }

    private void saveConference(Optional<String> nameEn, Optional<String> nameKo, Optional<String> descriptionEn, Optional<String> descriptionKo, Optional<Integer> year, Optional<String> thumbImage, Optional<String> images) {
        Conference conference = new Conference();

        nameEn.ifPresent(value -> conference.getName().put(Locale.ENGLISH, value));
        nameKo.ifPresent(value -> conference.getName().put(Locale.KOREAN, value));
        descriptionEn.ifPresent(value -> conference.getDescription().put(Locale.ENGLISH, value));
        descriptionKo.ifPresent(value -> conference.getDescription().put(Locale.KOREAN, value));
        year.ifPresent(conference::setYear);
        thumbImage.ifPresent(conference::setThumbImage);
        images.map(value -> Arrays.asList(value.split(","))).ifPresent(conference::setImages);

        conferenceRepository.save(conference);
    }
}
