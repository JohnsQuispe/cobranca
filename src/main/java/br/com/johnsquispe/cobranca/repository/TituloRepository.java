package br.com.johnsquispe.cobranca.repository;

import br.com.johnsquispe.cobranca.domain.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TituloRepository extends JpaRepository<Titulo, Long> {

    public List<Titulo> findByDescricaoContaining(String descricao);

}
