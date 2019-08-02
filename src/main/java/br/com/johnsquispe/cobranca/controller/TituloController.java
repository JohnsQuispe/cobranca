package br.com.johnsquispe.cobranca.controller;

import br.com.johnsquispe.cobranca.domain.StatusTitulo;
import br.com.johnsquispe.cobranca.domain.Titulo;
import br.com.johnsquispe.cobranca.repository.filter.TituloFilter;
import br.com.johnsquispe.cobranca.service.TituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    private static final String CADASTRO_VIEW = "cadastroTitulo";
    private static final String PESQUISA_TITULOS = "pesquisaTitulos";

    @Autowired
    private TituloService tituloService;

    @RequestMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(new Titulo());
        return mv;
    }

    @RequestMapping
    public ModelAndView pesquisar(@ModelAttribute("filtro")TituloFilter filtro) {

        List<Titulo> todosTitulos = tituloService.filtrar(filtro);

        ModelAndView mv = new ModelAndView(PESQUISA_TITULOS);
        mv.addObject("titulos", todosTitulos);

        return mv;

    }

    @PostMapping("/salvar")
    public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return CADASTRO_VIEW;
        }

        try {
            tituloService.salvar(titulo);
            redirectAttributes.addAttribute("mensagem", "Título salvo com sucesso");

            return "redirect:/titulos/novo";

        } catch (IllegalArgumentException e) {

            errors.rejectValue("dataVencimento", null, e.getMessage());

            return CADASTRO_VIEW;

        }

    }



    @GetMapping("{codigo}")
    public ModelAndView edicao (@PathVariable("codigo") Titulo titulo) {
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(titulo);
        return mv;
    }

    @DeleteMapping(value = "{codigo")
    public String excluir (@PathVariable Long codigo, RedirectAttributes redirectAttributes) {

        tituloService.excluir(codigo);
        redirectAttributes.addAttribute("mensagem", "Título excluído com sucesso");
        return "redirect:/titulos";

    }

    @PutMapping(value = "/{codigo}/receber")
    public @ResponseBody String receber (@PathVariable Long codigo) {
        return tituloService.receber(codigo);
    }

    @ModelAttribute ("todosStatusTitulo")
    public List<StatusTitulo> todosStatusTiuo () {
        return Arrays.asList(StatusTitulo.values());
    }
}
