package br.com.ecommerce.marcel.philippe.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.modelo.Usuario;
import br.com.ecommerce.marcel.philippe.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
class UsuarioControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UsuarioService usuarioService;
	
	private List<UsuarioDTO> usuarios;
	private UsuarioDTO usuarioDto;

	private static final String URL_BASE = "/usuario";
	private static final String CPF = "066.189.386-35";
	private static final String KEY = "0d769a46-3919-4476-bc6d-f812da60144f";
	private static final String EMAIL = "marcelpaa@hotmail.com";
	private static final String NOME = "Marcel Philippe";
	private static final Long USUARIO_ID = 1L;
	private static final long ID_INESISTENtE = 2L;
	private static final String DATA_CADASTRO = "29-03-2024";
	
	private static final String TELEFONE_USUÁRIO = "Telefone do usuário.";
	private static final String ENDEREÇO_USUÁRIO = "Endereço do usuário.";
	private static final String REQUEST_PARAM_NOME = "nome=Mar";
	
	@BeforeEach
	public void setUp() {
		usuarios = new ArrayList<>();
		usuarioDto = obterDadosDoUsuario();
		
		usuarios.add(usuarioDto);
	}

	@Test
	public void deveRetornarTodosOsUsuarios() throws Exception {
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(usuarioDto);
		BDDMockito.given(this.usuarioService.getAll()).willReturn(usuarios);

		mvc.perform(MockMvcRequestBuilders.get(URL_BASE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(CPF)))
				.andExpect(content().string(containsString(EMAIL)))
				.andExpect(content().string(containsString(NOME)));
	}

	@Test
	public void deveRetornarUmUsuarioPeloId() throws Exception {
		Usuario usuario = Usuario.convert(usuarioDto);
		usuario.setId(USUARIO_ID);
		
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(UsuarioDTO.convert(usuario));
		BDDMockito.given(this.usuarioService.findById(USUARIO_ID)).willReturn(UsuarioDTO.convert(usuario));
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + USUARIO_ID)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(String.valueOf(USUARIO_ID))))
				.andExpect(content().string(containsString(CPF)))
				.andExpect(content().string(containsString(EMAIL)))
				.andExpect(content().string(containsString(NOME)));
	}
	
	@Test 
	public void naoDeveRetornarUmUsuarioPeloId() throws Exception {
		Usuario usuario = Usuario.convert(usuarioDto);
		
		usuario.setId(ID_INESISTENtE);
		
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(UsuarioDTO.convert(usuario));
		BDDMockito.given(this.usuarioService.findById(ID_INESISTENtE)).willReturn(UsuarioDTO.convert(usuario));
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + USUARIO_ID)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deveSalvarUmUsuario() throws Exception {
		Usuario usuario = Usuario.convert(usuarioDto);
	    usuario.setId(USUARIO_ID);
	    
	    BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class)))
	              .willReturn(UsuarioDTO.convert(usuario));
	    
	    mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
	            .content(this.obterJsonRequisicaoPost())
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isCreated())
	            .andExpect(jsonPath("$.statusCode").value(201))
	            .andExpect(jsonPath("$.data.cpf").value(CPF))
	            .andExpect(jsonPath("$.data.email").value(EMAIL))
	            .andExpect(jsonPath("$.data.nome").value(NOME));
	}
	
	@Test
	public void deveRetornarErrosDeValidacaoQuandoUsuarioInvalido() throws Exception {
		UsuarioDTO usuarioDtoInvalido = new UsuarioDTO();
		
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(usuarioDtoInvalido);
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
	            .content(this.obterJsonRequisicaoPostInvalido())
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andExpect(jsonPath("$.statusCode").value(400))
	            .andExpect(jsonPath("$.data").exists())
	            .andExpect(jsonPath("$.erros").isNotEmpty());
	}
	
	@Test
	public void naoDeveSalvarUsuarioJaExistente() throws Exception {
		Usuario usuario = Usuario.convert(usuarioDto);
		
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(UsuarioDTO.convert(usuario));
		BDDMockito.given(this.usuarioService.findByCpf(CPF)).willReturn(UsuarioDTO.convert(usuario));
		
		 mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
		            .content(this.obterJsonRequisicaoPost())
		            .contentType(MediaType.APPLICATION_JSON)
		            .accept(MediaType.APPLICATION_JSON))
		 			.andExpect(status().isConflict());
		 }
	
	@Test
	public void deveRetornarUmUsuarioPeloCpf() throws Exception {
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(usuarioDto);
		BDDMockito.given(this.usuarioService.findByCpf(CPF, KEY)).willReturn(usuarioDto);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/cpf/" + CPF)
				.accept(MediaType.APPLICATION_JSON)
				.param("key", KEY))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(String.valueOf(USUARIO_ID))))
				.andExpect(content().string(containsString(CPF)))
				.andExpect(content().string(containsString(EMAIL)))
				.andExpect(content().string(containsString(NOME)));
	}
	
	@Test
	public void deveDeletarUmUsuarioPeloId() throws Exception {
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(usuarioDto);
		BDDMockito.given(this.usuarioService.delete(Mockito.anyLong())).willReturn(usuarioDto);
		
		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + USUARIO_ID)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void nãoDeveDeletarUmUsuarioPeloId() throws Exception {
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(usuarioDto);
		BDDMockito.given(this.usuarioService.delete(ID_INESISTENtE)).willReturn(null);
		
		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + USUARIO_ID)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deveRetornarUsuariosComOMesmoNome() throws Exception {
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(usuarioDto);
		BDDMockito.given(this.usuarioService.queryByName("Mar")).willReturn(usuarios);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + "search" + "?" + REQUEST_PARAM_NOME)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(CPF)))
				.andExpect(content().string(containsString(EMAIL)))
				.andExpect(content().string(containsString(NOME)));
	}
	
	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		UsuarioDTO usuarioDto = obterDadosDoUsuario();
		Usuario usuario = Usuario.convert(usuarioDto);
		usuario.setId(USUARIO_ID);
		
		UsuarioDTO usuarioDtoConvertido = UsuarioDTO.convert(usuario);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(usuarioDtoConvertido);
	}
	
	private String obterJsonRequisicaoPostInvalido() throws JsonProcessingException {
		UsuarioDTO usuarioInvalido = new UsuarioDTO();
		usuarioInvalido.setNome("");
		usuarioInvalido.setCpf("123");
		usuarioInvalido.setEmail("email_invalido");

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(usuarioInvalido);
	}

	private UsuarioDTO obterDadosDoUsuario() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNome(NOME);
		usuario.setCpf(CPF);
		usuario.setEmail(EMAIL);
		usuario.setEndereco(ENDEREÇO_USUÁRIO);
		usuario.setTelefone(TELEFONE_USUÁRIO);
		usuario.setDataCadastro(DATA_CADASTRO);
		return usuario;
	}
}
