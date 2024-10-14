package br.com.ecommerce.marcel.philippe.swagger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SwaggerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void deveRetornarPaginaUiDoSwagger() throws Exception {
		mockMvc.perform(get("/usuarios/swagger-ui/index.html"))
			.andExpect(status()
					.isOk());
	}
	
	@Test
	public void deveRetornarOpenApiDocs() throws Exception {
		mockMvc.perform(get("/usuarios/v3/api-docs"))
			.andExpect(status()
					.isOk())
            .andExpect(jsonPath("$.paths['/usuario']").exists())
            .andExpect(jsonPath("$.paths['/usuario'].get.responses['200'].description").exists())
            .andExpect(jsonPath("$.paths['/usuario/{id}'].get.responses['200'].description").exists())
            .andExpect(jsonPath("$.paths['/usuario/{id}'].get.responses['404'].description").exists())
            .andExpect(jsonPath("$.paths['/usuario'].post.responses['201'].description").exists())
            .andExpect(jsonPath("$.paths['/usuario'].post.responses['400'].description").exists())
            .andExpect(jsonPath("$.paths['/usuario/cpf/{cpf}'].get.responses['200'].description").exists())
            .andExpect(jsonPath("$.paths['/usuario/{id}'].delete.responses['200'].description").exists())
            .andExpect(jsonPath("$.paths['/usuario/{id}'].delete.responses['404'].description").exists())
            .andExpect(jsonPath("$.paths['/usuario/search'].get.responses['200'].description").exists());
	}
}
