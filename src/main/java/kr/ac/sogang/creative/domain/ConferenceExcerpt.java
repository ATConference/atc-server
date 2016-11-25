package kr.ac.sogang.creative.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Locale;
import java.util.Map;

@Projection(name = "excerpt", types = Conference.class)
public interface ConferenceExcerpt {

    Long getId();

    Map<Locale, String> getName();

    String getThumbImage();
}
