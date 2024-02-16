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
		UsuarioDTO userDTO = new UsuarioDTO();
		userDTO.setNome(usuario.getNome());
		userDTO.setEndereco(usuario.getEndereco());
		userDTO.setCpf(usuario.getCpf());
		userDTO.setEmail(usuario.getEmail());
		userDTO.setTelefone(usuario.getTelefone());
		userDTO.setDataCadastro(usuario.getDataCadastro());
		return userDTO;
	}

}
