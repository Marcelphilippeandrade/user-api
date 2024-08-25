package br.com.ecommerce.marcel.philippe.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.ecommerce.marcel.philippe.modelo.Usuario;

@ExtendWith(SpringExtension.class)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@MockBean
	private UsuarioRepository mockUsuarioRepository;

	private static final String CPF = "06618938635";
	private static final String KEY = "0d769a46-3919-4476-bc6d-f812da60144f";
	private static final String EMAIL = "marcelpaa@hotmail.com";
	private static final String NOME = "Mar";

	@Test
	public void deveRetornarUmUsuarioBuscandoPorUmCPF() {
		when(mockUsuarioRepository.findByCpfAndKey(CPF, KEY)).thenReturn(obterDadosUsuario().stream().findAny().get());
		Usuario usuario = usuarioRepository.findByCpfAndKey(CPF, KEY);
		assertEquals(CPF, usuario.getCpf());
	}

	@Test
	public void deveRetornarUmaListaDeUsuariosApartirDoNome() {
		when(mockUsuarioRepository.queryByNomeLike(NOME)).thenReturn(obterDadosUsuario());
		List<Usuario> usuarios = usuarioRepository.queryByNomeLike(NOME);

		assertFalse("A lista não pode ser vazia!", usuarios.isEmpty());
	}

	private List<Usuario> obterDadosUsuario() {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		Usuario usuario = new Usuario();
		usuario.setCpf(CPF);
		usuario.setNome("Marcel Philippe");
		usuario.setEmail(EMAIL);
		usuario.setEndereco("Rua: Manoel Rubim Nº: 409 Bairro: São Paulo Cep: 31910-030");
		usuario.setTelefone("31998565849");
		usuario.setDataCadastro(LocalDate.now());
		usuario.setKey(KEY);

		Usuario usuario2 = new Usuario();
		usuario2.setCpf(CPF);
		usuario2.setNome("Marcel");
		usuario2.setEmail(EMAIL);
		usuario2.setEndereco("Rua: Manoel Rubim Nº: 409 Bairro: São Paulo Cep: 31910-030");
		usuario2.setTelefone("31998565849");
		usuario2.setDataCadastro(LocalDate.now());
		usuario2.setKey(KEY);

		usuarios.add(usuario);
		usuarios.add(usuario2);

		return usuarios;
	}
}
