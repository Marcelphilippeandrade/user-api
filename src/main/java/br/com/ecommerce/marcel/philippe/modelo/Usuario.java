package br.com.ecommerce.marcel.philippe.modelo;

import java.time.LocalDate;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private String cpf;
	private String endereco;
	private String email;
	private String telefone;
	private LocalDate dataCadastro;

	public static Usuario convert(UsuarioDTO userDTO) {
		Usuario usuario = new Usuario();
		usuario.setNome(userDTO.getNome());
		usuario.setEndereco(userDTO.getEndereco());
		usuario.setCpf(userDTO.getCpf());
		usuario.setEmail(userDTO.getEmail());
		usuario.setTelefone(userDTO.getTelefone());
		usuario.setDataCadastro(userDTO.getDataCadastro());
		return usuario;
	}
}
