package br.com.ecommerce.marcel.philippe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.response.Response;
import br.com.ecommerce.marcel.philippe.service.UsuarioService;
import jakarta.validation.Valid;

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
	public UsuarioDTO getUsuarioFindById(@PathVariable Long id) {
		return usuarioService.findById(id);
	}

	@PostMapping("/usuario")
	public ResponseEntity<Response<UsuarioDTO>> novoUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			response.setStatusCode(400);
			response.setData(usuarioDTO);
	        return ResponseEntity.badRequest().body(response);
	    }
		
		UsuarioDTO usuario = this.usuarioService.save(usuarioDTO);
		response.setStatusCode(200);
	    response.setData(usuario);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/usuario/cpf/{cpf}")
	public UsuarioDTO getFindByCpf(@RequestParam(name="key", required=true) String key, @PathVariable String cpf) {
		return usuarioService.findByCpf(cpf, key);
	}

	@DeleteMapping("/usuario/{id}")
	public UsuarioDTO delete(@PathVariable Long id) {
		return usuarioService.delete(id);
	}

	@GetMapping("/usuario/search")
	public List<UsuarioDTO> queryByName(@RequestParam(name = "nome", required = true) String nome) {
		return usuarioService.queryByName(nome);
	}
}
