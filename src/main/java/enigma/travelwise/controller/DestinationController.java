package enigma.travelwise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import enigma.travelwise.service.DestinationService;
import enigma.travelwise.utils.dto.DestionationDTO;
import enigma.travelwise.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/v1/destinations")
@RequiredArgsConstructor
public class DestinationController {
    private final DestinationService destinationService;

    @Operation(summary = "Create a new destination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Destination created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody DestionationDTO request) throws JsonProcessingException {
        return Response.renderJSON(destinationService.create(request), "DESTINATION CREATED");
    }

    @Operation(summary = "Get all destinations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all destinations", content = @Content),
            @ApiResponse(responseCode = "404", description = "No destinations found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location) {
        return Response.renderJSON(destinationService.getAll(pageable, name, category, location));
    }

    @Operation(summary = "Get all destinations with weather information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all destinations with weather", content = @Content),
            @ApiResponse(responseCode = "404", description = "No destinations found", content = @Content)
    })
    @GetMapping("/weather")
    public ResponseEntity<?> getAllWithWeather(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location) {
        return Response.renderJSON(destinationService.getAllWithWeather(pageable, name, category, location));
    }

    @Operation(summary = "Get a destination by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Destination found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Destination not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID of the destination to retrieve") @PathVariable Long id) {
        return Response.renderJSON(destinationService.getById(id));
    }

    @Operation(summary = "Get a destination by its ID with weather information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Destination with weather found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Destination not found", content = @Content)
    })
    @GetMapping("/{id}/weather")
    public ResponseEntity<?> getWithWeatherById(
            @Parameter(description = "ID of the destination to retrieve with weather") @PathVariable Long id) {
        return Response.renderJSON(destinationService.getWithWeatherById(id));
    }

    @Operation(summary = "Update the photos of a destination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Photos updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input or file", content = @Content),
            @ApiResponse(responseCode = "404", description = "Destination not found", content = @Content)
    })
    @PutMapping("/{id}/photos")
    public ResponseEntity<?> updatePhoto(
            @RequestPart("images") List<MultipartFile> files,
            @Parameter(description = "ID of the destination to update photos for") @PathVariable Long id) throws JsonProcessingException {
        return Response.renderJSON(destinationService.uploadPhoto(files, id), "DESTINATION UPDATED");
    }

    @Operation(summary = "Update an existing destination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Destination updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Destination not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @RequestBody DestionationDTO request,
            @Parameter(description = "ID of the destination to update") @PathVariable Long id) throws JsonProcessingException {
        return Response.renderJSON(destinationService.update(id, request), "DESTINATION UPDATED");
    }

    @Operation(summary = "Delete a destination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Destination deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Destination not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "ID of the destination to delete") @PathVariable Long id) {
        destinationService.delete(id);
        return Response.renderJSON(null, "DESTINATION DELETED");
    }
}
