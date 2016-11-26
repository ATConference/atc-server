package kr.ac.sogang.creative.service;

import kr.ac.sogang.creative.domain.Program;
import kr.ac.sogang.creative.repository.ConferenceRepository;
import kr.ac.sogang.creative.repository.ParticipantRepository;
import kr.ac.sogang.creative.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class ProgramInitializer {

    private final ConferenceRepository conferenceRepository;
    private final ParticipantRepository participantRepository;
    private final ProgramRepository programRepository;

    @Value("classpath:data/programs.csv")
    private Resource programs;

    public void init() throws IOException {
        Reader in = new InputStreamReader(programs.getInputStream());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

        records.forEach(record -> {
            Optional<String> nameEn = Optional.of(record.get("nameEn"));
            Optional<String> nameKo = Optional.of(record.get("nameKo"));
            Optional<String> descriptionEn = Optional.of(record.get("descriptionEn"));
            Optional<String> descriptionKo = Optional.of(record.get("descriptionKo"));
            Optional<String> type = Optional.ofNullable(record.get("type"));
            Optional<String> categoryEn = Optional.ofNullable(record.get("categoryEn"));
            Optional<String> categoryKo = Optional.ofNullable(record.get("categoryKo"));
            Optional<String> imageId = Optional.of(record.get("imageId"));
            Optional<String> imageRange = Optional.ofNullable(record.get("imageRange"));
            Optional<String> videoId = Optional.ofNullable(record.get("videoId"));
            Optional<Integer> conferenceYear = Optional.ofNullable(Integer.parseInt(record.get("conferenceYear")));
            Optional<String> participants = Optional.ofNullable(record.get("participants"));

            saveProgram(nameEn, nameKo, descriptionEn, descriptionKo, type, categoryEn, categoryKo, imageId, imageRange, videoId, conferenceYear, participants);
        });
    }

    private void saveProgram(Optional<String> nameEn, Optional<String> nameKo, Optional<String> descriptionEn, Optional<String> descriptionKo, Optional<String> type, Optional<String> categoryEn, Optional<String> categoryKo, Optional<String> imageId, Optional<String> imageRange, Optional<String> videoId, Optional<Integer> conferenceYear, Optional<String> participants) {
        Program program = new Program();

        nameEn.ifPresent(value -> program.getName().put(Locale.ENGLISH, value));
        nameKo.ifPresent(value -> program.getName().put(Locale.KOREAN, value));
        descriptionEn.ifPresent(value -> program.getDescription().put(Locale.ENGLISH, value));
        descriptionKo.ifPresent(value -> program.getDescription().put(Locale.KOREAN, value));
        type.map(Program.Type::valueOf).ifPresent(program.getType()::add);
        categoryEn.ifPresent(value -> program.getCategory().put(Locale.ENGLISH, value));
        categoryKo.ifPresent(value -> program.getCategory().put(Locale.KOREAN, value));

        String imageIdValue = imageId.get();
        Integer startIdx = Integer.parseInt(Arrays.asList(imageRange.get().split("-")).get(0));
        Integer endIdx = Integer.parseInt(Arrays.asList(imageRange.get().split("-")).get(1));

        program.setThumbImage(getImageURL(imageIdValue, startIdx));
        program.setImages(getImageURL(imageIdValue, startIdx, endIdx));

        videoId.ifPresent(program::setVideoId);
        conferenceYear.map(conferenceRepository::findByYear).ifPresent(program::setConference);

        program.setParticipants(Arrays.stream(participants.get().split(","))
                .map(participantRepository::findByName)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()));

        programRepository.save(program);
    }

    static final String prefix = "http://atcofficial.kr/atc-assets/programs";

    private String getImageURL(String id, Integer idx) {
        return String.format("%s/%s_%02d.jpg", prefix, id, idx);
    }

    private List<String> getImageURL(String id, Integer startIdx, Integer endIdx) {
        List<String> list = new ArrayList<>();
        IntStream.range(startIdx, endIdx).forEach(idx -> list.add(getImageURL(id, idx)));
        return list;
    }
}
