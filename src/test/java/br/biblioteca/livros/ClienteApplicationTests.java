package br.biblioteca.livros;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.biblioteca.livros.beans.Cliente;
import br.biblioteca.livros.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ClienteApplicationTests {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void buscaClientesCadastrados() {
		
		Page<Cliente> clientes = this.clienteRepository.findAll(new PageRequest(0, 10));
		assertThat(clientes.getTotalElements()).isGreaterThan(1L);
		
	}

	@Test
	public void buscaClienteMaria() {
		
		Cliente clienteNaoEncontrado = this.clienteRepository.findByNome("Katsumi");
		assertThat(clienteNaoEncontrado).isNull();
		
		Cliente clienteEncontrado = this.clienteRepository.findByNome("Maria da Silva");
		assertThat(clienteEncontrado).isNotNull();	
	}
	
	@Test
	public void alteraClienteCadastrado() {
		Cliente alteraCliente = this.clienteRepository.findByNome("Laura da Silva");
		alteraCliente.setNome("Helder da Silva");
		
		this.clienteRepository.save(alteraCliente);
		
		Cliente alteraClienteVerifica = this.clienteRepository.findByNome("Helder da Silva");
		assertThat(alteraClienteVerifica).isNotNull();
	}
	
	@Test
	public void criaCliente() {
		Cliente cardoso = new Cliente();
		cardoso.setNome("cardoso");
		cardoso.setDataNascimento(new Date());
		cardoso.setEndereco("Rua do cardoso");
		cardoso.setObservacao("teste");
		
		this.clienteRepository.save(cardoso);
		
		Cliente clienteCardoso = this.clienteRepository.findByNome("cardoso");
		assertThat(clienteCardoso).isNotNull();
		
	}
	
	@Test
	public void excluiCliente() {
		Cliente clienteCardoso = this.clienteRepository.findByNome("cardoso");
		this.clienteRepository.delete(clienteCardoso);
		
		Cliente clienteCardosoExcluido = this.clienteRepository.findByNome("cardoso");
		assertThat(clienteCardosoExcluido).isNull();
	}
}
