package br.biblioteca.livros;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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

import br.biblioteca.livros.beans.Review;
import br.biblioteca.livros.repository.ReviewRepository;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ReviewApplicationTests {
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void buscaReviewsCadastrados() {	
		Page<Review> reviews = this.reviewRepository.findAll(new PageRequest(0, 10));
		assertThat(reviews.getTotalElements()).isGreaterThan(1L);	
	}
	
	@Test
	public void buscaReviewsComentarioTeste() {		
		Review naoEncontrado = this.reviewRepository.findByComentario("Muito ruim");
		assertThat(naoEncontrado).isNull();
		
		Review encontrado = this.reviewRepository.findByComentario("Teste");
		assertThat(encontrado).isNotNull();
		assertThat(encontrado.getComentario()).isEqualTo("Teste");		
	}
	
	@Test
	public void buscaReviewsComAvaliacao5() {
		List<Review> igual5 = this.reviewRepository.findByAvaliacao(5);
		assertThat(igual5.size()).isGreaterThan(1);
	}

}
