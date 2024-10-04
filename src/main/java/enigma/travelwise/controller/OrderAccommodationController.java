package enigma.travelwise.controller;

import enigma.travelwise.model.OrderAccommodation;
import enigma.travelwise.service.OrderAccommodationService;
import enigma.travelwise.service.TransactionService;
import enigma.travelwise.utils.dto.OrderAccommodationDTO;
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
@RequestMapping("/api/v1/order_accommodations")
@RequiredArgsConstructor
public class OrderAccommodationController {
    private final OrderAccommodationService orderAccommodationService;
    private final TransactionService transactionService;

    @Operation(summary = "Create a new order accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order accommodation created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody OrderAccommodationDTO request) {
        return Response.renderJSON(transactionService.createOrderAccommodation(request), "CREATE ORDER ACCOMMODATION");
    }

    @Operation(summary = "Get all order accommodations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all order accommodations", content = @Content),
            @ApiResponse(responseCode = "404", description = "No order accommodations found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer totalPrice,
            @RequestParam(required = false) LocalDate checkIn,
            @RequestParam(required = false) LocalDate checkOut) {
        return Response.renderJSON(orderAccommodationService.getAll(pageable, userId, totalPrice, checkIn, checkOut), "SHOW ALL ORDER ACCOMMODATION");
    }

    @Operation(summary = "Get an order accommodation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order accommodation found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order accommodation not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(
            @Parameter(description = "ID of the order accommodation to retrieve") @PathVariable String id) {
        return Response.renderJSON(orderAccommodationService.getOne(id), "SHOW ONE ORDER ACCOMMODATION");
    }
}

