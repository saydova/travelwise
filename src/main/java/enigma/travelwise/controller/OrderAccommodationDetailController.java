package enigma.travelwise.controller;

import enigma.travelwise.service.OrderAccommodationDetailService;
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
@RequestMapping("/api/v1/order_accommodation_details")
@RequiredArgsConstructor
public class OrderAccommodationDetailController {
    private final OrderAccommodationDetailService orderAccommodationDetailService;

    @Operation(summary = "Get all order accommodation details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all order accommodation details", content = @Content),
            @ApiResponse(responseCode = "404", description = "No order accommodation details found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) String categoryRoom,
            @RequestParam(required = false) Long accommodationId) {
        return Response.renderJSON(orderAccommodationDetailService.getAll(pageable, price, quantity, categoryRoom, accommodationId), "SHOW ALL ORDER ACCOMMODATION DETAIL");
    }

    @Operation(summary = "Get an order accommodation detail by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order accommodation detail found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order accommodation detail not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(
            @Parameter(description = "ID of the order accommodation detail to retrieve") @PathVariable long id) {
        return Response.renderJSON(orderAccommodationDetailService.getOne(id), "SHOW ONE ORDER ACCOMMODATION DETAIL");
    }
}
