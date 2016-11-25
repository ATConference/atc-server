package kr.ac.sogang.creative.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Projection(name = "excerpt", types = Program.class)
public interface ProgramExcerpt {

    Long getId();

    Map<Locale, String> getName();

    Set<Program.Type> getType();

    String getThumbImage();

    Set<Participant> getParticipants();
}
