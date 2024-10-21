package com.api.app.controllers;

import com.api.app.dtos.ProdutoDto;
import com.api.app.models.ProdutoModel;
import com.api.app.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/produtos")
public class ProdutoController {

    final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoModel> saveProduto(@RequestBody @Valid ProdutoDto produtoDto) {
        var produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(produtoDto, produtoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(produtoModel));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoModel>> getAllProdutos() {
        return ResponseEntity.ok().body(produtoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> getProdutoById(@PathVariable(value = "id") UUID id) {
        Optional<ProdutoModel> produtoModel = produtoService.findById(id);

        return produtoModel.map(model -> ResponseEntity.ok().body(model)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editProduto(@PathVariable(value = "id") UUID id, @RequestBody ProdutoDto produtoDto)
    {
        Optional<ProdutoModel> produtoModelOptional = produtoService.findById(id);

        if(produtoModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Produto não encontrado"
            );
        }

        produtoDto.setId(id);

        var produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(produtoDto, produtoModel);

        return ResponseEntity.status(HttpStatus.OK).body(
                produtoService.save(produtoModel)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagarProduto(@PathVariable(value = "id") UUID id){
        Optional<ProdutoModel> produtoModelOptional = produtoService.findById(id);

        if(produtoModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Produto não encontrado"
            );
        }

        produtoService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                "Produto apagado com sucesso"
        );
    }
}
