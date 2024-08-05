package br.com.ecommerce.marcel.philippe.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.exception.UsuarioNotFoundException;
import br.com.ecommerce.marcel.philippe.modelo.Usuario;
import br.com.ecommerce.marcel.philippe.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	private static final LocalDate DATA_CADASTRO = LocalDate.now();
	private static final String USUARIO_CPF = "066.189.386-35";
	private static final String USUARIO_NOME = "Marcel";
	private static final Long USUARIO_ID = 1L;
	
	private static final String TELEFONE = "31998565849";
	private static final String ENDERECO = "Rua Manoel Rubim";
	private static final String USUARIO_EMAIL = "marcelpaa@hotmail.com";
	
	@BeforeEach
	public void setUp() {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();
		usuario.setId(USUARIO_ID);
		usuario.setNome(USUARIO_NOME);
		usuario.setCpf(USUARIO_CPF);
		usuario.setDataCadastro(DATA_CADASTRO);
		usuarios.add(usuario);
		
		Usuario usuario1 = new Usuario();
		usuario1.setId(USUARIO_ID);
		usuario1.setNome(USUARIO_NOME);
		usuario1.setCpf(USUARIO_CPF);
		usuario1.setDataCadastro(DATA_CADASTRO);
		
		usuarios.add(usuario1);
		
		BDDMockito.given(this.usuarioRepository.save(any())).willReturn(usuario);
		BDDMockito.given(this.usuarioRepository.saveAll(Mockito.anyList())).willReturn(usuarios);
		BDDMockito.given(this.usuarioRepository.findById(USUARIO_ID)).willReturn(Optional.ofNullable(usuario));
		BDDMockito.given(this.usuarioRepository.findByCpf(USUARIO_CPF)).willReturn(usuario);
		
		BDDMockito.given(this.usuarioRepository.findAll()).willReturn(usuarios);
		BDDMockito.given(this.usuarioRepository.queryByNomeLike(USUARIO_NOME)).willReturn(usuarios);
	}
	
	@Test
	public void deveRetornarTodosOsUsuarios() {
		List<UsuarioDTO> usuarios = this.usuarioService.getAll();
		assertNotNull(usuarios);
		assertTrue("A lista de usuarios deve ter mais de 1 usuario!", usuarios.size() > 1);
	}
	
	@Test
	public void deveRetornarUmUsuarioPeloId() {
		UsuarioDTO usuario = usuarioService.findById(1);
		assertNotNull(usuario);
	}
	
	@Test
	public void naoDeveRetornarUmUsuarioPeloId() {
		UsuarioDTO usuario = usuarioService.findById(2);
		assertNull(usuario);
	}
	
	@Test
	public void deveSalvarUmUsuario() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setNome(USUARIO_NOME);
		usuarioDTO.setCpf(USUARIO_CPF);
		usuarioDTO.setEmail(USUARIO_EMAIL);
		usuarioDTO.setEndereco(ENDERECO);
		usuarioDTO.setTelefone(TELEFONE);
		usuarioDTO.setDataCadastro("30-03-2024");
		
		UsuarioDTO usuarioSalvo = usuarioService.save(usuarioDTO);
		assertNotNull(usuarioSalvo);
	}
	
	@Test
	public void deveDeletarUmUsuarioPeloId() {
		UsuarioDTO usuarioDeletado = usuarioService.delete(1);
		assertEquals(USUARIO_CPF, usuarioDeletado.getCpf());
	}
	
	@Test
	public void naoDeveDeletarUmUsuarioPeloId() {
		UsuarioDTO usuarioDeletado = usuarioService.delete(3);
		assertNull(usuarioDeletado);
	}
	
	@Test
	public void deveRetornarUmUsuarioPeloCpf() {
		UsuarioDTO usuario = usuarioService.findByCpf(USUARIO_CPF);
		assertNotNull(usuario);
		assertEquals(USUARIO_CPF, usuario.getCpf());
	}
	
	@Test
	public void deveRetornarUmaExecaoQuandoNaoExitirUmUsuario() {

		String cpf = "876.223.320-38";
		when(usuarioRepository.findByCpf(cpf)).thenReturn(null);

		assertThrows(UsuarioNotFoundException.class, () -> {
			usuarioService.findByCpf(cpf);
		});
	}
	
	@Test
	public void deveRetornarUmUsuariosComMesmoNome() {
		List<UsuarioDTO> usuarios = usuarioService.queryByName("Marcel");
		assertNotNull(usuarios);
		assertEquals(2, usuarios.size());
	}
}
