package com.thoughtworks.sample.version.view;

import com.thoughtworks.sample.handlers.model.ErrorResponse;
import com.thoughtworks.sample.version.exceptions.VersionNotAvailableException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.thoughtworks.sample.version.VersionService;


import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/version")
public class VersionController {
    private final VersionService versionService;
    @Autowired
    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }
//    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched version successfully"),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    public Map<String,String> version() throws VersionNotAvailableException {
        String version = versionService.version();
        Map<String,String> versionDetails = new HashMap<>();
        versionDetails.put("CurrentVersion" , version);
        return versionDetails;
    }
}
