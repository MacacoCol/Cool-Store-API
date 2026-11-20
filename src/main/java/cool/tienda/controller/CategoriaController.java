package cool.tienda.controller;

import cool.tienda.model.Categoria;
import cool.tienda.service.CategoriaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/// Capa de presentación: recibe las peticiones HTTP y responde en JSON
@CrossOrigin(originPatterns = "http://localhost:*")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService servicio;

    public CategoriaController(CategoriaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<Categoria> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public Categoria obtener(@PathVariable Integer id) {
        return servicio.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria crear(@RequestBody Categoria categoria) {
        return servicio.crear(categoria);
    }

    @PutMapping("/{id}")
    public Categoria actualizar(@PathVariable Integer id, @RequestBody Categoria categoria) {
        return servicio.actualizar(id, categoria);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        servicio.eliminar(id);
    }
}