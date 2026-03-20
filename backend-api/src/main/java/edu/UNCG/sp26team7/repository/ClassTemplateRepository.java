package edu.UNCG.sp26team7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import edu.UNCG.sp26team7.entity.ClassTemplate;

@Repository
public interface ClassTemplateRepository
    extends JpaRepository<ClassTemplate, Long>, JpaSpecificationExecutor<ClassTemplate> {

}
