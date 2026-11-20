package cool.tienda.repository;

import cool.tienda.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository ya trae findAll, findById, save y deleteById: no hay que escribir SQL
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    // Spring crea la consulta solo a partir del nombre del método
    boolean existsByNombreIgnoreCase(String nombre);
}