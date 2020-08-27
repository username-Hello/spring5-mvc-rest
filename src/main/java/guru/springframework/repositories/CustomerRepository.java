package guru.springframework.repositories;

import guru.springframework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findById(Long id);
    
    Customer findCustomerByFirstnameAndLastname(String firstName, String lastName);
}
