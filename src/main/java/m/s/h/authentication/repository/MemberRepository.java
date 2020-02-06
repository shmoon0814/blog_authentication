package m.s.h.authentication.repository;

import m.s.h.authentication.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Members, Integer> {

    Members findByEmail(String email);

    @Query(value = "SELECT m FROM Members m WHERE m.display_name = :display_name ")
    Members findByDisplay_name(@Param("display_name") String display_name);
}
