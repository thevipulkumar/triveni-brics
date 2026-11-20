package com.trivenibricks.repository;

import com.trivenibricks.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByAvailableTrueOrderBySortOrderAsc();

    List<Product> findAllByOrderBySortOrderAsc();
}
