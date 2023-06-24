package com.product.provider.api.controller;

import com.product.provider.api.dto.ProductDTO;
import com.product.provider.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class productController {

    @Autowired
    private ProductService productService;

    @Operation(
            summary ="get product" ,
            description ="get product by reference")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "get the existing product",
                    content = @Content( schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Unknown product",
                    content = @Content( schema = @Schema(implementation = ProductDTO.class)))})
    @GetMapping("/products/{reference}")
    public ProductDTO getProduct(@PathVariable String reference){

       return productService.findByReference(reference);

    }

    @Operation(
            summary ="Create product" ,
            description ="request to create product in the database")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "success, the product is created",
                    content = @Content( schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "invalid parameters",
                    content = @Content( schema = @Schema(implementation = ProductDTO.class)))})
    @PostMapping("/products")
    public ProductDTO createProduct( @RequestBody ProductDTO product){

        return productService.createProduct(product);

    }

}
