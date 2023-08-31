package com.thoughtworks.sample.version;

import com.thoughtworks.sample.version.exceptions.VersionNotAvailableException;
import com.thoughtworks.sample.version.repository.Version;
import com.thoughtworks.sample.version.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VersionService {
    @Autowired
    private final VersionRepository versionRepository;
    public VersionService(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }
    public String version() throws VersionNotAvailableException {
        List<Version> version = versionRepository.currentVersion();
        if(version.isEmpty()){
            throw new VersionNotAvailableException("Version info is not available");
        }
        Version currentVersion = version.get(0);

        return currentVersion.getName();
    }
}
