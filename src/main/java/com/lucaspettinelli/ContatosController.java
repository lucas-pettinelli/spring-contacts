package com.lucaspettinelli;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContatosController {

	private static final ArrayList<Contato> LISTA_CONTATOS = new ArrayList<>();

	static {
		LISTA_CONTATOS.add(new Contato("1", "Michael Scott", "417-555-0116"));
		LISTA_CONTATOS.add(new Contato("2", "Creed Bratton", "417-555-0199"));
		LISTA_CONTATOS.add(new Contato("3", "Dwight Schrute	", "417-555-0187"));
		LISTA_CONTATOS.add(new Contato("4", "Ryan Howard", "417-555-0105"));
		LISTA_CONTATOS.add(new Contato("5", "Erin Hannon", "417-555-0142"));
		LISTA_CONTATOS.add(new Contato("6", "Jan Levinson", "417-555-0185"));
		LISTA_CONTATOS.add(new Contato("8", "Pam Beesly", "417-555-2226"));
		LISTA_CONTATOS.add(new Contato("9", "Jim Halpert", "417-555-2223"));
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/contatos")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("listar");
		modelAndView.addObject("contatos", LISTA_CONTATOS);

		return modelAndView;
	}

	@GetMapping("/contatos/novo")
	public ModelAndView novo() {
		ModelAndView modelAndView = new ModelAndView("formulario");
		modelAndView.addObject("contato", new Contato());

		return modelAndView;
	}

	@PostMapping("/contatos")
	public String cadastrar(Contato contato) {
		String id = UUID.randomUUID().toString();

		contato.setId(id);
		LISTA_CONTATOS.add(contato);

		return "redirect:/contatos";

	}

	@GetMapping("/contatos/{id}/editar")
	public ModelAndView editar(@PathVariable String id) {
		ModelAndView modelAndView = new ModelAndView("formulario");

		Contato contato = procurarContato(id);

		modelAndView.addObject("contato", contato);
		return modelAndView;
	}

	@PutMapping("/contatos/{id}")
	public String atualizar(Contato contato) {
		Integer indice = procurarIndiceContato(contato.getId());

		Contato contatoAntigo = LISTA_CONTATOS.get(indice);

		LISTA_CONTATOS.remove(contatoAntigo);

		LISTA_CONTATOS.add(indice, contato);

		return "redirect:/contatos";
	}

	// ------------- Métodos auxiliares ------------------------//
	private Integer procurarIndiceContato(String id) {
		for (int i = 0; i < LISTA_CONTATOS.size(); i++) {
			Contato contato = LISTA_CONTATOS.get(i);

			if (contato.getId().equals(id)) {
				return i;
			}
		}
		return null;
	}

	private Contato procurarContato(String id) {
		for (int i = 0; i < LISTA_CONTATOS.size(); i++) {
			Contato contato = LISTA_CONTATOS.get(i);

			if (contato.getId().equals(id)) {
				return contato;
			}
		}
		return null;
	}
}
