package co.user.api.repo;

import co.user.api.data.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface FileRepository extends JpaRepository<File,Long> {
}
