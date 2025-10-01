package vn.maxtrann.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import vn.maxtrann.model.Customer;

@RestController
public class CustomerController {

  private final List<Customer> customers = List.of(
      Customer.builder().id("1").name("Tran Le Quoc Dai").phoneNumber("0938432219").email("daitlq@hcmute.edu.vn").build(),
      Customer.builder().id("2").name("Quoc Dai").phoneNumber("0906851351").email("quocdai@gmail.com").build()
  );

  @GetMapping("/hello")
  public ResponseEntity<String> hello() {
      return ResponseEntity.ok("hello is Guest");
  }

  @GetMapping("/customer/all")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<List<Customer>> getCustomerList() {
      List<Customer> list = this.customers;
      return ResponseEntity.ok(list);
  }

  @GetMapping("/customer/{id}")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<Customer> getCustomerList(@PathVariable("id") String id) {
      List<Customer> customers = this.customers.stream()
          .filter(customer -> customer.getId().equals(id))
          .toList();
      return ResponseEntity.ok(customers.get(0));
  }
}
