package kr.ac.sogang.creative.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.*;

@Data
@ToString(exclude = {"participants", "conference"})
@EqualsAndHashCode(exclude = {"participants", "conference"})
@Entity
public class Program extends AbstractPersistable<Long> {

    @ElementCollection
    private Map<Locale, String> name = new HashMap<>();

    @ElementCollection
    @Lob
    private Map<Locale, String> description = new HashMap<>();

    @ElementCollection
    private Set<Type> type = new HashSet<>();

    @ElementCollection
    private Map<Locale, String> category = new HashMap<>();

    @NotEmpty
    @URL
    private String thumbImage;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @NotEmpty
    private String videoId;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Conference conference;

    @ManyToMany
    private Set<Participant> participants = new HashSet<>();

    public enum Type {
        EXHIBITION,
        SHOWCASE,
        LECTURE
    }
}
