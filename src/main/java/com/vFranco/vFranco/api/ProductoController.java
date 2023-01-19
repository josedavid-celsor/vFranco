package com.vFranco.vFranco.api;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vFranco.vFranco.service.FilesStorageService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vFranco.vFranco.entity.ProductoEntity;
import com.vFranco.vFranco.helper.FileUploadUtil;
import com.vFranco.vFranco.request.CreateProductoRequest;
import com.vFranco.vFranco.service.ProductoService;

@RestController
@Controller
@RequestMapping("/Producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private FilesStorageService filesStorageService;

    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createProducto(@RequestPart("producto") String producto,
            @RequestPart("images") List<MultipartFile> multipartFiles) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        CreateProductoRequest createProductoRequest = objectMapper.readValue(producto, CreateProductoRequest.class);
        if (productoService.existByName(createProductoRequest.getNombre())) {

            return ResponseEntity.badRequest().body("name is already taken");
        }
        String fotos = "";
        for (MultipartFile file : multipartFiles) {

            filesStorageService.save(file);
            fotos += file.getOriginalFilename() + "-";
        }
        // Borramos el último caracter concatenador
        fotos = fotos.substring(0, fotos.length() - 1);

        ProductoEntity productoEntity = productoService.creaProducto(createProductoRequest, fotos);

        return ResponseEntity.ok(productoEntity);

    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ProductoEntity> update(@RequestPart("producto") String producto,
            @RequestPart(name = "images", required = false) List<MultipartFile> multipartFiles,
            @PathVariable(value = "id") Long id) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductoEntity productoEntity = objectMapper.readValue(producto, ProductoEntity.class);
        ProductoEntity productoBDDEntity = productoService.get(id);
        String fotosNuevas = productoBDDEntity.getImages();
        if (productoEntity != null) {
            if (multipartFiles != null) {

                if (multipartFiles.size() > 0) {
                    // Nos ha enviado fotos, por lo que debemos borrar todas las qu estuvieran antes
                    // y subir las nuevas
                    fotosNuevas = "";
                    String[] fotos = productoBDDEntity.getImages().split("-");
                    for (String foto : fotos) {
                        filesStorageService.deleteOne(foto);
                    }

                    for (MultipartFile file : multipartFiles) {
                        filesStorageService.save(file);
                        fotosNuevas += file.getOriginalFilename() + "-";
                    }
                    // Borramos el último caracter concatenador
                    fotosNuevas = fotosNuevas.substring(0, fotosNuevas.length() - 1);

                }
            }
            productoEntity.setImages(fotosNuevas);
            return ResponseEntity.ok(productoService.update(productoBDDEntity, productoEntity));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        ProductoEntity productoEntity = productoService.get(id);
        if (productoEntity != null) {
            String[] fotos = productoEntity.getImages().split("-");
            productoService.delete(id);
            for (String foto : fotos) {
                filesStorageService.deleteOne(foto);
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> getTipoProducto(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productoService.get(id));
    }

    @GetMapping("/filtros")
    public ResponseEntity<Page<ProductoEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 5, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "tipoproducto", required = false) String codigo,
            @RequestParam(name = "subtipoproducto", required = false) String subTipoProducto) {

        return ResponseEntity.ok(productoService.getPage(oPageable, strFilter, codigo, subTipoProducto));
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ProductoEntity> generate() {
        return ResponseEntity.ok(productoService.generate());
    }

    @PostMapping("/generate/{amount}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") int amount) {
        return ResponseEntity.ok(productoService.generateSome(amount));
    }

}
