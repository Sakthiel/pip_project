package com.thoughtworks.sample.version;

import com.thoughtworks.sample.version.exceptions.VersionNotAvailableException;
import com.thoughtworks.sample.version.repository.Version;
import com.thoughtworks.sample.version.repository.VersionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VersionServiceTest {

    private VersionRepository versionRepository;
    private VersionService versionService;
    @Test
    public void should_return_current_version() throws VersionNotAvailableException {
        versionRepository = mock(VersionRepository.class);
        versionService = new VersionService(versionRepository);
        Version version = new Version(1,"v2");
        List<Version> versions = new ArrayList<>();
        versions.add(version);
        when(versionRepository.currentVersion()).thenReturn(versions);

        versionRepository.save(version);

        String expectedVersion = "v2";

        assertThat(versionService.version() , is(equalTo(expectedVersion)));

    }
}
