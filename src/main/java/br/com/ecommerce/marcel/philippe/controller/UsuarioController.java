package br.com.ecommerce.marcel.philippe.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.response.Response;
import br.com.ecommerce.marcel.philippe.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class UsuarioController {
	
	Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private UsuarioService usuarioService;

	@Operation(summary = "Listar todos os usuários")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"), })
	@GetMapping("/usuario")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response<List<UsuarioDTO>>> getUsuarios() {
		Response<List<UsuarioDTO>> response = new Response<>();
		List<UsuarioDTO> usuarios = usuarioService.getAll();

		response.setStatusCode(200);
		response.setData(usuarios);
		
		logger.info("Busca de usuários retornada com sucesso!");

		return ResponseEntity.ok(response);
	}

	 @Operation(summary = "Retornar usuário pelo ID")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
	        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
	    })
	@GetMapping("/usuario/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response<UsuarioDTO>> getUsuarioFindById(@PathVariable Long id) {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();

		UsuarioDTO usuario = usuarioService.findById(id);

		if (usuario == null) {
			response.setStatusCode(404);
			response.getErros().add("Usuário não localizado para o ID: " + id);
			logger.error("Usuário não encontrado para o id: {}", id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setStatusCode(200);
		response.setData(usuario);
		logger.info("Usuário de nome: {}", usuario.getNome() + ", ID: " + id + " encontrado com sucesso!");
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Criar novo usuário")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro nos dados do usuário") })
	@PostMapping("/usuario")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Response<UsuarioDTO>> novoUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO,
			BindingResult result) {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		
		UsuarioDTO pesquisaUsuarioCPF = usuarioService.findByCpf(usuarioDTO.getCpf());

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			response.setStatusCode(400);
			response.setData(usuarioDTO);
			logger.error("Não foi possível salvar novo usuário: {}", usuarioDTO.getNome() + ", CPF: " +
					usuarioDTO.getCpf());
			return ResponseEntity.badRequest().body(response);
		}
		
		if (pesquisaUsuarioCPF != null) {
			response.getErros().add("Usuário já cadastrado para o CPF: " + usuarioDTO.getCpf());
			response.setStatusCode(409);
			response.setData(usuarioDTO);
			logger.error("Usuário já cadastrado: {}", usuarioDTO.getNome() + ", CPF: " + usuarioDTO.getCpf());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}

		UsuarioDTO usuario = this.usuarioService.save(usuarioDTO);
		response.setStatusCode(201);
		response.setData(usuario);
		logger.info("Usuário: {}", usuario.getNome() + ", CPF: " + usuario.getCpf() + " salvo com sucesso!");
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	
	@Operation(summary = "Retornar usuário pelo CPF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso"),
    })
	@GetMapping("/usuario/cpf/{cpf}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response<UsuarioDTO>> getFindByCpf(@RequestParam(name = "key", required = true) String key,
			@PathVariable String cpf) {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();

		UsuarioDTO usuario = usuarioService.findByCpf(cpf, key);

		response.setStatusCode(200);
		response.setData(usuario);
		logger.info("Usuário: {}", usuario.getNome() + ", CPF: " + usuario.getCpf() + " encontrado com sucesso!");
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Deletar usuário pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário Deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Erro ao deletar usuário")
    })
	@DeleteMapping("/usuario/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response<UsuarioDTO>> delete(@PathVariable Long id) {
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		UsuarioDTO usuario = usuarioService.delete(id);

		if (usuario == null) {
			response.setStatusCode(404);
			response.getErros().add("Erro ao excluir usuário. ID não localizado, ID: " + id);
			logger.error("Não foi possível deletar usuário para o ID: {}", id, ". Usuário não encontrado!");
			return ResponseEntity.badRequest().body(response);
		}

		response.setStatusCode(200);
		response.setData(usuario);
		logger.error("Usuário ID: {}", id + ", Nome: " + usuario.getNome() + ", CPF: " + usuario.getCpf() + "deletado com sucesso!");
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Retornar usuário pelo nome")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso")
	    })
	@GetMapping("/usuario/search")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response<List<UsuarioDTO>>> queryByName(
			@RequestParam(name = "nome", required = true) String nome) {
		Response<List<UsuarioDTO>> response = new Response<>();

		List<UsuarioDTO> usuarios = usuarioService.queryByName(nome);

		response.setStatusCode(200);
		response.setData(usuarios);
		
		logger.info("Busca de usuários pelo nome: {}", nome + " retornada com sucesso!");
		return ResponseEntity.ok(response);
	}
}
