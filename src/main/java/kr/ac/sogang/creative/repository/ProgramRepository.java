package kr.ac.sogang.creative.repository;

import kr.ac.sogang.creative.domain.Program;
import kr.ac.sogang.creative.domain.ProgramExcerpt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostFilter;

@RepositoryRestResource(excerptProjection = ProgramExcerpt.class)
public interface ProgramRepository extends CrudRepository<Program, Long> {

    @PostFilter("filterObject.conference.year == 2016")
    Iterable<Program> findAll();
}