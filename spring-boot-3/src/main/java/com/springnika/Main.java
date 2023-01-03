package com.springnika;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.List;

@RestController
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@RequestMapping("api/v1/customers")
public class Main {
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String args[]){
        SpringApplication.run(Main.class, args);
    }
    @GetMapping
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }
    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){}
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setAge(request.age());
        customer.setEmail(request.email());
        customerRepository.save(customer);
    }
    @DeleteMapping("{customerId}")
    public String deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
        return "Customer is deleted successfully!";
    }
}
