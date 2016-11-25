package kr.ac.sogang.creative.repository;

import kr.ac.sogang.creative.domain.Participant;
import kr.ac.sogang.creative.domain.ParticipantExcerpt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = ParticipantExcerpt.class)
public interface ParticipantRepository extends CrudRepository<Participant, Long> {

    List<Participant> findByName(String name);

}
