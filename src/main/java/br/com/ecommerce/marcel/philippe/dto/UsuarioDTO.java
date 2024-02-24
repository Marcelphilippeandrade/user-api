package br.com.ecommerce.marcel.philippe.dto;

import java.time.LocalDate;

import br.com.ecommerce.marcel.philippe.modelo.Usuario;
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
	private LocalDate dataCadastro;

	public static UsuarioDTO convert(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setNome(usuario.getNome());
		usuarioDTO.setEndereco(usuario.getEndereco());
		usuarioDTO.setCpf(usuario.getCpf());
		usuarioDTO.setEmail(usuario.getEmail());
		usuarioDTO.setTelefone(usuario.getTelefone());
		usuarioDTO.setDataCadastro(usuario.getDataCadastro());
		return usuarioDTO;
	}

}
