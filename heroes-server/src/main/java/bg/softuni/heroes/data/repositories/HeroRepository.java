package bg.softuni.heroes.data.repositories;

import bg.softuni.heroes.data.models.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HeroRepository extends JpaRepository<Hero, UUID> {

}
