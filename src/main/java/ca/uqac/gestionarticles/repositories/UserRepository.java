package ca.uqac.gestionarticles.repositories;

import ca.uqac.gestionarticles.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User,Long> {
    public User findByLogin(String login);

    @Query("select u from User u where u.lastName like :x")
    public Page<User> chercher(@Param("x") String mc, Pageable pageable);

    @Query("select u from User u where u.lastName like :x")
    public Page<Object> rechercher(@Param("x") String mc, Pageable pageable);

    /*SELECT t.a
     , SUM(t.b)
     , AVG(t.c)
  FROM (
         SELECT 'q1' AS source, a, b, c, d FROM t1
          UNION ALL
         SELECT 'q2', t2.fee, t2.fi, t2.fo, 'fum' FROM t2
       ) t
 GROUP BY t.a
 ORDER BY t.a*/
}
