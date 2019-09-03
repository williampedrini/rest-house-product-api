package com.custodio.resthouse.product.api.product.controller;

import com.custodio.resthouse.product.api.outcome.dto.OutcomeDTO;
import com.custodio.resthouse.product.api.product.dto.ErrorResponseDTO;
import com.custodio.resthouse.product.api.product.dto.ProductDTO;
import com.custodio.resthouse.product.api.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("Customer API")
@RequestMapping("/products")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(@Qualifier("defaultProductService") final ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation("Search for all the existing registered products.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully return a list of all existing products.", response = List.class),
            @ApiResponse(code = 400, message = "An error occurred while performing the search for all products.", response = ErrorResponseDTO.class)
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public List<ProductDTO> findAll() {
        return this.productService.findAll();
    }

    @ApiOperation("Search for a customer by a specific identifier.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully return a specific customer by identifier.", response = ProductDTO.class),
            @ApiResponse(code = 400, message = "An error occurred while performing the search for a specific product.", response = ErrorResponseDTO.class),
            @ApiResponse(code = 404, message = "The product does not exist.", response = ErrorResponseDTO.class)
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ProductDTO findByCode(@PathVariable("id") final ObjectId id) {
        return this.productService.findById(id);
    }

    @ApiOperation("Create a specific product.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created a product.", response = ProductDTO.class),
            @ApiResponse(code = 400, message = "An error occurred while field validation.", response = ProductDTO.class),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    public ProductDTO save(@RequestBody final ProductDTO product) {
        return this.productService.save(product);
    }

    @ApiOperation("Update a specific product.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated a product.", response = ProductDTO.class),
            @ApiResponse(code = 400, message = "An error occurred while field validation.", response = ProductDTO.class),
            @ApiResponse(code = 404, message = "The product does not exist.", response = ErrorResponseDTO.class)
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ProductDTO update(@PathVariable("id") final ObjectId id, @RequestBody final ProductDTO product) {
        return this.productService.update(id, product);
    }

    @ApiOperation("Update a specific product.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successfully deleted a product."),
            @ApiResponse(code = 400, message = "An error occurred while field validation."),
            @ApiResponse(code = 404, message = "The product does not exist.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    public void deleteById(@PathVariable("id") final ObjectId id) {
        this.productService.deleteById(id);
    }

    @ApiOperation("Increase the stock for a specific product.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successfully increased the product stock."),
            @ApiResponse(code = 404, message = "The product does not exist.")
    })
    @PostMapping("/{id}/add")
    @ResponseStatus(HttpStatus.OK)
    public void add(@PathVariable("id") final ObjectId id, @RequestBody final OutcomeDTO outcome) {
        this.productService.addToStock(id, outcome);
    }
}