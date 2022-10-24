package co.user.api.repo;

import co.user.api.data.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByCivilId(String cid);
    Users findByName(String name);
    @Query("SELECT u FROM Users u where u.expiryDate > CURRENT_DATE")
    List<Users> findActiveStatus();
    @Query("SELECT u FROM Users u where u.expiryDate < CURRENT_DATE")
    List<Users> findInActiveStatus();
    void deleteByCivilId(String cid);
}
