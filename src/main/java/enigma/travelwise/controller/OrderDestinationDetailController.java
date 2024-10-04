package enigma.travelwise.controller;

import enigma.travelwise.service.OrderDestinationDetailService;
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

@RestController
@RequestMapping("/api/v1/order_destination_details")
@RequiredArgsConstructor
public class OrderDestinationDetailController {

    private final OrderDestinationDetailService orderDestinationDetailService;

    @Operation(summary = "Get all order destination details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all order destination details", content = @Content),
            @ApiResponse(responseCode = "404", description = "No order destination details found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) String categoryTicket,
            @RequestParam(required = false) Long destinationId) {
        return Response.renderJSON(orderDestinationDetailService.getAll(pageable, price, quantity, categoryTicket, destinationId), "SHOW ALL ORDER DESTINATION DETAIL");
    }

    @Operation(summary = "Get an order destination detail by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order destination detail found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order destination detail not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(
            @Parameter(description = "ID of the order destination detail to retrieve") @PathVariable long id) {
        return Response.renderJSON(orderDestinationDetailService.getOne(id), "SHOW ONE ORDER DESTINATION DETAIL");
    }
}
