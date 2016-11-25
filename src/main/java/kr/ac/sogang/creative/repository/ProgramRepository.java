package kr.ac.sogang.creative.repository;

import kr.ac.sogang.creative.domain.Program;
import kr.ac.sogang.creative.domain.ProgramExcerpt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = ProgramExcerpt.class)
public interface ProgramRepository extends CrudRepository<Program, Long> {

}