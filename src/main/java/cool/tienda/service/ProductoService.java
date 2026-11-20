package cool.tienda.service;

import cool.tienda.model.Categoria;
import cool.tienda.model.Producto;
import cool.tienda.repository.CategoriaRepository;
import cool.tienda.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Producto> listar(String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            return productoRepository.findByNombreContainingIgnoreCase(nombre.trim());
        }
        return productoRepository.findAll();
    }

    public Producto obtener(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No se encontró el producto " + id));
    }

    public Producto crear(Producto producto) {
        validar(producto);
        producto.setIdProducto(null);
        producto.setNombre(producto.getNombre().trim());
        producto.setCategoria(buscarCategoria(producto));
        return productoRepository.save(producto);
    }

    public Producto actualizar(Integer id, Producto datos) {
        Producto existente = obtener(id);
        validar(datos);
        existente.setNombre(datos.getNombre().trim());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());
        existente.setCategoria(buscarCategoria(datos));
        return productoRepository.save(existente);
    }

    public void eliminar(Integer id) {
        Producto producto = obtener(id);
        productoRepository.delete(producto);
    }

    private void validar(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        if (producto.getNombre().trim().length() > 120) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre admite máximo 120 caracteres");
        }
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor que 0");
        }
    }

    private Categoria buscarCategoria(Producto producto) {
        if (producto.getCategoria() == null || producto.getCategoria().getIdCategoria() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoría es obligatoria");
        }
        return categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "La categoría indicada no existe"));
    }
}