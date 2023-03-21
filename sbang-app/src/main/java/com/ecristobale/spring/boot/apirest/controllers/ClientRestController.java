package com.ecristobale.spring.boot.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecristobale.spring.boot.apirest.models.entity.Client;
import com.ecristobale.spring.boot.apirest.models.entity.ProfileImg;
import com.ecristobale.spring.boot.apirest.models.entity.Region;
import com.ecristobale.spring.boot.apirest.models.services.IClientService;
import com.ecristobale.spring.boot.apirest.models.services.IUploadFileService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientRestController {

    @Autowired
    IClientService clientService;

    @Autowired
    IUploadFileService uploadFileService;

    @GetMapping("/clients")
    public List<Client> index() {
        return clientService.findAll();
    }

    @GetMapping("/clients/page/{page}")
    public Page<Client> index(@PathVariable Integer page) {
        return clientService.findAll(PageRequest.of(page, 4));
    }

//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/clients/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Client client = null;
        Map<String, Object> response = new HashMap<>();
        try {
            client = clientService.findById(id);
        } catch (DataAccessException dae) {
            response.put("message", "Error performing the query on the database.");
            response.put("error", dae.getMessage().concat(": ").concat(dae.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (client == null) {
            response.put("message", "Customer ID: ".concat(id.toString())
                    .concat(" does not exist in the database."));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/clients")
    public ResponseEntity<?> create(@Valid @RequestBody Client client, BindingResult result) {
        Client newClient = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage()
                    ).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            newClient = clientService.save(client);
        } catch (DataAccessException dae) {
            response.put("message", "Error performing the insertion in the database.");
            response.put("error", dae.getMessage().concat(": ").concat(dae.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client has been successfully created");
        response.put("client", newClient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/clients/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id, @RequestBody Client client,
                                    BindingResult result) {
        Client clientActual = clientService.findById(id);
        Client clientUpdated;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()
                    ).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (clientActual == null) {
            response.put("message", "Error while updating. Customer ID: ".concat(id.toString())
                    .concat(" does not exist in the database."));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clientActual.setName(client.getName());
            clientActual.setLastname(client.getLastname());
            clientActual.setEmail(client.getEmail());
            clientActual.setCreatedAt(client.getCreatedAt());
            clientActual.setRegion(client.getRegion());

            clientUpdated = clientService.save(clientActual);
        } catch (DataAccessException dae) {
            response.put("message", "Error updating the database.");
            response.put("error", dae.getMessage().concat(": ").concat(dae.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client has been successfully updated");
        response.put("client", clientUpdated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/clients/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Client client = clientService.findById(id);
            uploadFileService.deleteFile(client.getPhoto());
            clientService.delete(id);
        } catch (DataAccessException dae) {
            response.put("message", "Error deleting from the database.");
            response.put("error", dae.getMessage().concat(": ").concat(dae.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "The client was successfully deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/clients/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        Client client = clientService.findById(id);
        if (!file.isEmpty()) {
            String filename = null;
            try {
                filename = uploadFileService.uploadFile(file);
            } catch (IOException e) {
                response.put("message", "Error uploading the image");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            uploadFileService.deleteFile(client.getPhoto());

            client.setPhoto(filename);
            clientService.save(client);

            response.put("client", client);
            response.put("message", "You have uploaded the image correctly: " + filename);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//	Used when the images are retrieved from folder location
//	@GetMapping("/uploads/img/{filename:.+}")
//	public ResponseEntity<Resource> showPhoto(@PathVariable String filename) {
//		Resource resource = null;
//		try {
//			resource = uploadFileService.loadFile(filename);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
//		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
//	}

    @GetMapping("/uploads/img/{filename:.+}")
    public ResponseEntity<Resource> showPhoto(@PathVariable String filename) {
        ProfileImg profileImg = null;
        try {
            profileImg = uploadFileService.loadFile(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (profileImg != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(profileImg.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profileImg.getFilename() + "\"")
                    .body(new ByteArrayResource(profileImg.getImg()));
        } else {
            Resource resource = null;
            try {
                resource = uploadFileService.loadFileDefaultImage(filename);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("clients/regions")
    public List<Region> getRegionsList() {
        return clientService.findAllRegions();
    }
}
