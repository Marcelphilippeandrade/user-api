package br.com.ecommerce.marcel.philippe.dto;

import br.com.ecommerce.marcel.philippe.modelo.Usuario;
import br.com.ecommerce.marcel.philippe.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	private String nome;
	private String cpf;
	private String endereco;
	private String email;
	private String telefone;
	private String dataCadastro;
	private String key;

	public static UsuarioDTO convert(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setNome(usuario.getNome());
		usuarioDTO.setEndereco(usuario.getEndereco());
		usuarioDTO.setCpf(usuario.getCpf());
		usuarioDTO.setEmail(usuario.getEmail());
		usuarioDTO.setTelefone(usuario.getTelefone());
		usuarioDTO.setDataCadastro(DataUtil.transformarLocalDateEmString(usuario.getDataCadastro()));
		usuarioDTO.setKey(usuario.getKey());
		return usuarioDTO;
	}

}
