package m.s.h.authentication.repository;

import m.s.h.authentication.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Members, Integer> {

    Members findByEmail(String email);

}
