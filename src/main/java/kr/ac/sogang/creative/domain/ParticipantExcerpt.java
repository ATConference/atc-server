package kr.ac.sogang.creative.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Projection(name = "excerpt", types = Participant.class)
public interface ParticipantExcerpt {

    Long getId();

    Map<Locale, String> getName();

    Set<Participant.Type> getType();

    String getImage();
}
