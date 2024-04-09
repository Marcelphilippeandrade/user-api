package br.com.ecommerce.marcel.philippe.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

	private static final String REQUEST_PARAM_NOME = "nome=Mar";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UsuarioService usuarioService;
	
	private List<UsuarioDTO> usuarios;
	private UsuarioDTO usuarioDto;

	private static final String URL_BASE = "/user";
	private static final String CPF = "066.189.386-35";
	private static final String EMAIL = "marcelpaa@hotmail.com";
	private static final String NOME = "Marcel Philippe";
	private static final Long USUARIO_ID = 1L;
	private static final String DATA_CADASTRO = "29-03-2024";
	
	@BeforeEach
	public void setUp() {
		usuarios = new ArrayList<>();
		usuarioDto = ObterDadosDoUsuario();
		
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
	public void deveSalvarUmUsuario() throws Exception {
		Usuario usuario = Usuario.convert(usuarioDto);
		usuario.setId(USUARIO_ID);
		
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(UsuarioDTO.convert(usuario));
		
		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(String.valueOf(USUARIO_ID))))
				.andExpect(content().string(containsString(CPF)))
				.andExpect(content().string(containsString(EMAIL)))
				.andExpect(content().string(containsString(NOME)));
	}
	
	@Test
	public void deveRetornarUmUsuarioPeloCpf() throws Exception {
		BDDMockito.given(this.usuarioService.save(Mockito.any(UsuarioDTO.class))).willReturn(usuarioDto);
		BDDMockito.given(this.usuarioService.findByCpf(CPF)).willReturn(usuarioDto);
		
		mvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/cpf/" + CPF)
				.accept(MediaType.APPLICATION_JSON))
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
		UsuarioDTO usuarioDto = ObterDadosDoUsuario();
		Usuario usuario = Usuario.convert(usuarioDto);
		usuario.setId(USUARIO_ID);
		
		UsuarioDTO usuarioDtoConvertido = UsuarioDTO.convert(usuario);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(usuarioDtoConvertido);
	}

	private UsuarioDTO ObterDadosDoUsuario() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setCpf(CPF);
		usuario.setEmail(EMAIL);
		usuario.setNome(NOME);
		usuario.setDataCadastro(DATA_CADASTRO);
		return usuario;
	}
}
