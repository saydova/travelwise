package enigma.travelwise.controller;

import enigma.travelwise.model.UserEntity;
import enigma.travelwise.service.UserService;
import enigma.travelwise.utils.dto.UserChangeLocationDTO;
import enigma.travelwise.utils.dto.UserChangeProfilePictureDTO;
import enigma.travelwise.utils.dto.UserDTO;
import enigma.travelwise.utils.dto.UserUpdateDTO;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New user created", content = @Content(schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    private ResponseEntity<?> createUser(
            @RequestBody UserDTO request) {
        UserEntity result = userService.create(request);
        return Response.renderJSON(result, "NEW USER CREATED");
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all users", content = @Content),
            @ApiResponse(responseCode = "404", description = "No users found", content = @Content)
    })
    @GetMapping
    private ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String name) {
        return Response.renderJSON(userService.getAll(pageable, name), "SHOW ALL USERS");
    }

    @Operation(summary = "Get a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/{id}")
    private ResponseEntity<?> getById(
            @Parameter(description = "ID of the user to retrieve") @PathVariable Long id) {
        return Response.renderJSON(userService.getById(id), "SHOW ONE USER BY ID");
    }

    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/{id}")
    private ResponseEntity<?> update(
            @RequestBody UserUpdateDTO request,
            @PathVariable Long id) {
        return Response.renderJSON(userService.update(request, id), "USER UPDATED");
    }

    @Operation(summary = "Change user's profile picture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile picture changed", content = @Content(schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/{id}/change-profile-picture")
    private ResponseEntity<?> changeProfile(
            @RequestBody UserChangeProfilePictureDTO request,
            @PathVariable Long id) {
        return Response.renderJSON(userService.changeProfilePicture(request, id), "PROFILE PICTURE CHANGED");
    }

    @Operation(summary = "Change user's location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User location changed", content = @Content(schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/{id}/change-location")
    private ResponseEntity<?> changeLocation(
            @RequestBody UserChangeLocationDTO request,
            @PathVariable Long id) {
        return Response.renderJSON(userService.changeLocation(request, id), "USER LOCATION CHANGED");
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(
            @PathVariable Long id) {
        return Response.renderJSON(userService.deleteById(id), "USER DELETED SUCCESSFULLY");
    }
}
