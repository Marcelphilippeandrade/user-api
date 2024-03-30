package br.com.ecommerce.marcel.philippe.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.modelo.Usuario;
import br.com.ecommerce.marcel.philippe.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public void setUp() {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setNome("Marcel");
		usuario.setCpf("066.189.386-35");
		usuario.setDataCadastro(LocalDate.now());
		usuarios.add(usuario);
		
		Usuario usuario1 = new Usuario();
		usuario1.setId(2);
		usuario1.setNome("Marcel Philippe");
		usuario1.setCpf("390.857.676-87");
		usuario1.setDataCadastro(LocalDate.now());
		
		usuarios.add(usuario1);
		
		BDDMockito.given(this.usuarioRepository.save(any())).willReturn(usuario);
		BDDMockito.given(this.usuarioRepository.saveAll(Mockito.anyList())).willReturn(usuarios);
		BDDMockito.given(this.usuarioRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(usuario));
		BDDMockito.given(this.usuarioRepository.findByCpf(Mockito.anyString())).willReturn(usuario);
		
		BDDMockito.given(this.usuarioRepository.findAll()).willReturn(usuarios);
		BDDMockito.given(this.usuarioRepository.queryByNomeLike(Mockito.anyString())).willReturn(usuarios);
	}
	
	@Test
	public void deveRetornarTodosOsUsuarios() {
		setUp();
		List<UsuarioDTO> usuarios = this.usuarioService.getAll();
		assertNotNull(usuarios);
		assertTrue("A lista de usuarios deve ter mais de 1 usuario!", usuarios.size() > 1);
	}
	
	@Test
	public void deveRetornarUmUsuarioPeloId() {
		setUp();
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
		usuarioDTO.setNome("Marcel");
		usuarioDTO.setCpf("066.189.386-35");
		usuarioDTO.setEmail("marcelpaa@hotmail.com");
		usuarioDTO.setEndereco("Rua Manoel Rubim");
		usuarioDTO.setTelefone("31998565849");
		usuarioDTO.setDataCadastro("30-03-2024");
		
		setUp();
		UsuarioDTO usuarioSalvo = usuarioService.save(usuarioDTO);
		assertNotNull(usuarioSalvo);
	}
	
	@Test
	public void deveDeletarUmUsuarioPeloId() {
		setUp();
		UsuarioDTO usuarioDeletado = usuarioService.delete(1);
		assertEquals("066.189.386-35", usuarioDeletado.getCpf());
	}
	
	@Test
	public void naoDeveDeletarUmUsuarioPeloId() {
		UsuarioDTO usuarioDeletado = usuarioService.delete(3);
		assertNull(usuarioDeletado);
	}
	
	@Test
	public void deveRetornarUmUsuarioPeloCpf() {
		setUp();
		UsuarioDTO usuario = usuarioService.findByCpf("066.189.386-35");
		assertNotNull(usuario);
		assertEquals("066.189.386-35", usuario.getCpf());
	}
	
	@Test
	public void naoDeveRetornarUmUsuarioPeloCPF() {
		UsuarioDTO usuario = usuarioService.findByCpf("876.223.320-38");
		assertNull(usuario);
	}
	
	@Test
	public void deveRetornarUmUsuariosComMesmoNome() {
		setUp();
		List<UsuarioDTO> usuarios = usuarioService.queryByName("Mar");
		assertNotNull(usuarios);
		assertTrue("A lista de usuarios deve ter mais de 1 usuario!", usuarios.size() > 1);
	}
}
