package kr.ac.sogang.creative.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.util.*;

@Data
@ToString(exclude = {"programs", "conference"})
@EqualsAndHashCode(exclude = {"programs", "conference"})
@Entity
public class Participant extends AbstractPersistable<Long> {

    @ElementCollection
    private Map<Locale, String> name = new HashMap<>();

    @ElementCollection
    @Lob
    private Map<Locale, String> description = new HashMap<>();

    @ElementCollection
    private Set<Type> type = new HashSet<>();

    @NotEmpty
    @URL
    private String thumbImage;

    @URL
    private String image;

    @Email
    private String email;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Conference conference;

    @ManyToMany(mappedBy = "participants")
    private Set<Program> programs = new HashSet<>();

    @JsonIgnore
    public boolean isDirector() {
        return this.type.contains(Type.DIRECTOR);
    }

    public enum Type {
        ARTIST,
        SPEAKER,
        STAFF,
        DIRECTOR
    }
}
