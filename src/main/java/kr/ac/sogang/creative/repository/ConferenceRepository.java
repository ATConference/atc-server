package kr.ac.sogang.creative.repository;

import kr.ac.sogang.creative.domain.Conference;
import kr.ac.sogang.creative.domain.ConferenceExcerpt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostFilter;

@RepositoryRestResource(excerptProjection = ConferenceExcerpt.class)
public interface ConferenceRepository extends CrudRepository<Conference, Long> {

    @PostFilter("filterObject.year != 2016")
    Iterable<Conference> findAll();

    Conference findByYear(Integer year);
}
