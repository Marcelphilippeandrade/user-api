package br.com.ecommerce.marcel.philippe.dto;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import br.com.ecommerce.marcel.philippe.modelo.Usuario;

public class DTOConverterTest {

    private static final String CPF = "123.456.789-00";
	private static final String ENDERECO_USUARIO = "Rua A, 123";
	private static final String NOME_USUARIO = "Marcel";

	@Test
    public void deveConveterUmUsuarioParaUsuarioDto() {
        Usuario usuario = new Usuario();
        usuario.setNome(NOME_USUARIO);
        usuario.setEndereco(ENDERECO_USUARIO);
        usuario.setCpf(CPF);

        UsuarioDTO usuarioDTO = DTOConverter.convert(usuario);

        assertEquals(usuario.getNome(), usuarioDTO.getNome());
        assertEquals(usuario.getEndereco(), usuarioDTO.getEndereco());
        assertEquals(usuario.getCpf(), usuarioDTO.getCpf());
    }
}