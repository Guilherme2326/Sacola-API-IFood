package me.dio.sacola.repository;

import me.dio.sacola.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRespository extends JpaRepository<Item, Long> {
}
