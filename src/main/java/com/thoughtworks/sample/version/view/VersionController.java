package com.thoughtworks.sample.version.view;

import com.thoughtworks.sample.version.exceptions.VersionNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.thoughtworks.sample.version.VersionService;

import java.util.HashMap;
import java.util.Map;
import com.thoughtworks.sample.handlers.models.ErrorResponse;

@RestController()
@RequestMapping("/version")
public class VersionController {
    private final VersionService versionService;
    @Autowired
    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String,String> version() throws VersionNotAvailableException {
        String version = versionService.version();
        Map<String,String> versionDetails = new HashMap<>();
        versionDetails.put("CurrentVersion" , version);
        return versionDetails;
    }
}
