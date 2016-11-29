package kr.ac.sogang.creative.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@ToString(exclude = {"programs", "participants"})
@EqualsAndHashCode(exclude = {"programs", "participants"})
@Entity
public class Conference extends AbstractPersistable<Long> {

    @ElementCollection
    private Map<Locale, String> name = new HashMap<>();

    @ElementCollection
    @Lob
    private Map<Locale, String> description = new HashMap<>();

    @NotNull
    @Range(min = 2012, max = 2099)
    @Column(unique = true)
    private Integer year;

    @NotEmpty
    @URL
    private String thumbImage;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @OneToMany(mappedBy = "conference")
    private List<Program> programs;

    @OneToMany(mappedBy = "conference")
    private List<Participant> participants;

    public Participant getDirector() {
        return participants.stream().filter(Participant::isDirector).findFirst().get();
    }
}
