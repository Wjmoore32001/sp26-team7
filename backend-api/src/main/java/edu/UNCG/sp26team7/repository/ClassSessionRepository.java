package edu.UNCG.sp26team7.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.UNCG.sp26team7.entity.ClassSession;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {

  List<ClassSession> findByClassTemplateClassTemplateId(Long classTemplateId);
}
