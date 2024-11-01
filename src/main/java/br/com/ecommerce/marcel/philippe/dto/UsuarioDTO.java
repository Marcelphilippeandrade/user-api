package br.com.ecommerce.marcel.philippe.dto;

import org.hibernate.validator.constraints.br.CPF;

import br.com.ecommerce.marcel.philippe.modelo.Usuario;
import br.com.ecommerce.marcel.philippe.utils.DataUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	@NotBlank(message = "Nome não pode ser vazio.")
	private String nome;
	
	@NotBlank(message = "CPF não pode ser vazio.")
	@CPF(message = "CPF Inválido, favor inserir um CPF válido.")
	private String cpf;
	
	@NotBlank(message = "Endereço não pode ser vazio.")
	private String endereco;
	
	@NotBlank(message = "Email não pode ser vazio.")
	private String email;
	
	@NotBlank(message = "Telefone não pode ser vazio.")
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
