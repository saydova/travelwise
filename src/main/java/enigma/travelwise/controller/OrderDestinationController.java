package enigma.travelwise.controller;

import enigma.travelwise.service.OrderDestinationService;
import enigma.travelwise.service.TransactionService;
import enigma.travelwise.utils.dto.OrderDestinationDTO;
import enigma.travelwise.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/order_destinations")
@RequiredArgsConstructor
public class OrderDestinationController {
    private final OrderDestinationService orderDestinationsService;
    private final TransactionService transactionService;

    @Operation(summary = "Create a new order destination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order destination created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody OrderDestinationDTO request) {
        return Response.renderJSON(transactionService.createOrderDestionation(request), "ORDER DESTINATION CREATED");
    }

    @Operation(summary = "Get all order destinations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all order destinations", content = @Content),
            @ApiResponse(responseCode = "404", description = "No order destinations found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer totalPrice,
            @RequestParam(required = false) LocalDate orderDate) {
        return Response.renderJSON(orderDestinationsService.getAll(pageable, userId, totalPrice, orderDate), "SHOW ALL ORDER DESTINATIONS");
    }

    @Operation(summary = "Get an order destination by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order destination found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order destination not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(
            @Parameter(description = "ID of the order destination to retrieve") @PathVariable String id) {
        return Response.renderJSON(orderDestinationsService.getOne(id), "SHOW ONE ORDER DESTINATION");
    }
}
