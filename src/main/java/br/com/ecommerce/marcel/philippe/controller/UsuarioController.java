package br.com.ecommerce.marcel.philippe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/usuario")
	public List<UsuarioDTO> getUsuarios() {
		List<UsuarioDTO> usuarios = usuarioService.getAll();
		return usuarios;
	}

	@GetMapping("/usuario/{id}")
	UsuarioDTO getUsuarioFindById(@PathVariable Long id) {
		return usuarioService.findById(id);
	}

	@PostMapping("/usuario")
	UsuarioDTO novoUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		return usuarioService.save(usuarioDTO);
	}

	@GetMapping("/usuario/cpf/{cpf}")
	UsuarioDTO getFindByCpf(@RequestParam(name="key", required=true) String key, @PathVariable String cpf) {
		return usuarioService.findByCpf(cpf, key);
	}

	@DeleteMapping("/usuario/{id}")
	UsuarioDTO delete(@PathVariable Long id) {
		return usuarioService.delete(id);
	}

	@GetMapping("/usuario/search")
	public List<UsuarioDTO> queryByName(@RequestParam(name = "nome", required = true) String nome) {
		return usuarioService.queryByName(nome);
	}
}
