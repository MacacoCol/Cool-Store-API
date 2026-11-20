package cool.tienda.service;

import cool.tienda.model.Categoria;
import cool.tienda.repository.CategoriaRepository;
import cool.tienda.repository.ProductoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// Aquí viven las reglas de negocio de las categorías
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    // Inyección de dependencias por constructor
    public CategoriaService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Categoria obtener(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la categoría " + id));
    }

    public Categoria crear(Categoria categoria) {
        validar(categoria);
        if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre().trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una categoría con ese nombre");
        }
        categoria.setIdCategoria(null); // el id lo genera MySQL (AUTO_INCREMENT)
        categoria.setNombre(categoria.getNombre().trim());
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Integer id, Categoria datos) {
        Categoria existente = obtener(id);
        validar(datos);
        existente.setNombre(datos.getNombre().trim());
        existente.setDescripcion(datos.getDescripcion());
        return categoriaRepository.save(existente);
    }

    public void eliminar(Integer id) {
        Categoria existente = obtener(id);
        // Integridad referencial: no se borra una categoría que tiene productos
        if (!productoRepository.findByCategoria_IdCategoria(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar: la categoría tiene productos");
        }
        categoriaRepository.delete(existente);
    }

    private void validar(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        if (categoria.getNombre().trim().length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre admite máximo 100 caracteres");
        }
    }
}