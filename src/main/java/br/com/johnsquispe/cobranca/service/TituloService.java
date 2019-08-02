package br.com.johnsquispe.cobranca.service;

import br.com.johnsquispe.cobranca.domain.StatusTitulo;
import br.com.johnsquispe.cobranca.domain.Titulo;
import br.com.johnsquispe.cobranca.repository.TituloRepository;
import br.com.johnsquispe.cobranca.repository.filter.TituloFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TituloService {

    @Autowired
    private TituloRepository tituloRepository;

    public void salvar(Titulo titulo) {

        try {

            tituloRepository.save(titulo);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("Formato de Data inválido");

        }

    }

    public void excluir (Long codigo) {
        tituloRepository.deleteById(codigo);
    }

    public String receber (Long codigo) {
        Optional<Titulo> optionalTitulo = tituloRepository.findById(codigo);
        if (!optionalTitulo.isPresent()) {
            StatusTitulo.PENDENTE.getDescricao();
        }

        Titulo titulo = optionalTitulo.get();
        titulo.setStatusTitulo(StatusTitulo.RECEBIDO);
        tituloRepository.save(titulo);

        tituloRepository.save(titulo);

        return StatusTitulo.RECEBIDO.getDescricao();

    }

    public List<Titulo> filtrar (TituloFilter tituloFilter) {

        String descricao = tituloFilter.getDescricao() == null ? "" : tituloFilter.getDescricao();

        return tituloRepository.findByDescricaoContaining(descricao);

    }

}
