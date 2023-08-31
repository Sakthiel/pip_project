package com.thoughtworks.sample.version.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;

public class VersionResponse {

    private Long id;
    @JsonProperty
    String CurrentVersion;
}
