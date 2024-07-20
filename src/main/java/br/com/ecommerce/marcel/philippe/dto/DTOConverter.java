package br.com.ecommerce.marcel.philippe.dto;

import br.com.ecommerce.marcel.philippe.modelo.Usuario;

public class DTOConverter {

	public static UsuarioDTO convert(Usuario usuario) {
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setNome(usuario.getNome());
		usuarioDto.setEndereco(usuario.getEndereco());
		usuarioDto.setCpf(usuario.getCpf());
		return usuarioDto;
	}
}
