package enigma.travelwise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import enigma.travelwise.service.AccommodationService;
import enigma.travelwise.utils.dto.AccommodationDTO;
import enigma.travelwise.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/accommodations")
@RequiredArgsConstructor
public class AccommodationController {
    @Autowired
    private final AccommodationService accommodationService;

    @Operation(summary = "Create a new accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Accommodation created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    private ResponseEntity<?> create(@RequestBody AccommodationDTO request) {
        return Response.renderJSON(accommodationService.create(request), "ACCOMMODATION CREATED");
    }

    @Operation(summary = "Update photos of an accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Photos uploaded", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input or file", content = @Content),
            @ApiResponse(responseCode = "404", description = "Accommodation not found", content = @Content)
    })
    @PutMapping("/{id}/photos")
    private ResponseEntity<?> updatePhoto(
            @RequestPart("images") List<MultipartFile> files,
            @Parameter(description = "ID of the accommodation to update photos for") @PathVariable Long id) throws JsonProcessingException {
        return Response.renderJSON(accommodationService.updatePhoto(files, id), "PHOTOS UPLOADED");
    }

    @Operation(summary = "Get a list of all accommodations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the accommodations", content = @Content),
            @ApiResponse(responseCode = "404", description = "No accommodations found", content = @Content)
    })
    @GetMapping
    private ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location) {
        return Response.renderJSON(accommodationService.getAll(pageable, name, location, category), "SHOW ALL ACCOMMODATIONS");
    }

    @Operation(summary = "Get an accommodation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the accommodation", content = @Content),
            @ApiResponse(responseCode = "404", description = "Accommodation not found", content = @Content)
    })
    @GetMapping("/{id}")
    private ResponseEntity<?> getById(
            @Parameter(description = "ID of the accommodation to retrieve") @PathVariable Long id) {
        return Response.renderJSON(accommodationService.getById(id), "SHOW ACCOMMODATION BY ID");
    }

    @Operation(summary = "Update an existing accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodation updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Accommodation not found", content = @Content)
    })
    @PutMapping("/{id}")
    private ResponseEntity<?> update(
            @RequestBody AccommodationDTO request,
            @Parameter(description = "ID of the accommodation to update") @PathVariable Long id) {
        return Response.renderJSON(accommodationService.update(request, id), "ACCOMMODATION UPDATED");
    }

    @Operation(summary = "Delete an accommodation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accommodation deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Accommodation not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(
            @Parameter(description = "ID of the accommodation to delete") @PathVariable Long id) {
        return Response.renderJSON(accommodationService.deleteById(id), "ACCOMMODATION DELETED");
    }
}
