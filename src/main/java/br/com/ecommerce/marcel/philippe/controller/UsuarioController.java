package br.com.ecommerce.marcel.philippe.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import jakarta.annotation.PostConstruct;

@RestController
public class UsuarioController {

	public static List<UsuarioDTO> usuarios = new ArrayList<>();

	@PostConstruct
	public void initiateList() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setNome("Eduardo");
		usuarioDTO.setCpf("123");
		usuarioDTO.setEndereco("Rua a");
		usuarioDTO.setEmail("eduardo@email.com");
		usuarioDTO.setTelefone("1234-3454");
		usuarioDTO.setDataCadastro(LocalDate.now());

		UsuarioDTO usarioDTO2 = new UsuarioDTO();
		usarioDTO2.setNome("Luiz");
		usarioDTO2.setCpf("456");
		usarioDTO2.setEndereco("Rua b");
		usarioDTO2.setEmail("luiz@email.com");
		usarioDTO2.setTelefone("1234-3454");
		usarioDTO2.setDataCadastro(LocalDate.now());

		UsuarioDTO usuarioDTO3 = new UsuarioDTO();
		usuarioDTO3.setNome("Bruna");
		usuarioDTO3.setCpf("678");
		usuarioDTO3.setEndereco("Rua c");
		usuarioDTO3.setEmail("bruna@email.com");
		usuarioDTO3.setTelefone("1234-3454");
		usuarioDTO3.setDataCadastro(LocalDate.now());

		usuarios.add(usuarioDTO);
		usuarios.add(usarioDTO2);
		usuarios.add(usuarioDTO3);
	}

	@GetMapping("/users")
	public List<UsuarioDTO> getMensagem() {
		return usuarios;
	}

	@GetMapping("/users/{cpf}")
	public UsuarioDTO getUsersFiltro(@PathVariable String cpf) {
		for (UsuarioDTO usuario : usuarios) {
			if (usuario.getCpf().equals(cpf)) {
				return usuario;
			}
		}
		return null;
	}

	@PostMapping("/newUser")
	UsuarioDTO inserir(@RequestBody UsuarioDTO usuarioDTO) {
		usuarioDTO.setDataCadastro(LocalDate.now());
		usuarios.add(usuarioDTO);
		return usuarioDTO;
	}

	@DeleteMapping("/user/{cpf}")
	public boolean remover(@PathVariable String cpf) {
		for (UsuarioDTO userFilter : usuarios) {
			if (userFilter.getCpf().equals(cpf)) {
				usuarios.remove(userFilter);
				return true;
			}
		}
		return false;
	}
}
