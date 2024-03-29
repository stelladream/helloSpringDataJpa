package kr.ac.hansung.cse.hellospringdatajpa;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class HelloSpringDataJpaApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Test1: findProductById")
    public void findProductById() {
        Optional<Product> product = productRepository.findById(1L);
        System.out.println(product.get());
        assertNotNull(product.get());
    }

    @Test
    @DisplayName("Test2: findAllProducts")
    public void findAllProducts() {
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
    }


    @Test
    @DisplayName("Test3: createProduct")
    public void createProduct() {
        Product product = new Product("OLED TV", "LG전자", "korea", 300.0);
        Product savedProduct = productRepository.save(product);

        Product newProduct = productRepository.findById(savedProduct.getId()).get();
        assertEquals("OLED TV", newProduct.getName());
    }

    @Test
    @DisplayName("Test4: findByName")
    public void findByName() {
        Product product = productRepository.findByName("Galaxy S21");
        assertEquals("Galaxy S21", product.getName());
    }

    @Test
    @DisplayName("Test5: findByNameContainingWithPaging")
    public void findByNameContainingWithPaging() {

        Pageable paging = PageRequest.of(0, 3);
        List<Product> productList = productRepository.findByNameContaining("MacBook", paging);

        System.out.println("====findByNameContainingWithPaging: Macbook=====");
        for (Product product : productList) {
            System.out.println("-->" + product.toString() );
        }
    }

    @Test
    @DisplayName("Test6: findByNameContainingWithPagingAndSort")
    public void findByNameContainingWithPagingAndSort( ) {

        Pageable paging = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        List<Product> productList =
                productRepository.findByNameContaining("Galaxy", paging);

        System.out.println("===findByNameContainingWithPagingAndSort: Galaxy====");
        for (Product product : productList) {
            System.out.println("-->" + product.toString() );
        }
    }

    @Test
    @DisplayName("Test7: searchByNameUsingQuery")
    public void searchByNameUsingQuery() {
        List<Product> productList= productRepository.searchByName("Air");

        System.out.println(" ====searchByNameUsingQuery: Air======");
        for (Product product : productList) {
            System.out.println("-->" + product.toString() );
        }
    }



}
