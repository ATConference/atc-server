package kr.ac.sogang.creative.repository;

import kr.ac.sogang.creative.domain.Conference;
import kr.ac.sogang.creative.domain.ConferenceExcerpt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = ConferenceExcerpt.class)
public interface ConferenceRepository extends CrudRepository<Conference, Long> {

    Conference findByYear(Integer year);

}
