package com.tenant.management.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@JsonPropertyOrder({ "success", "message" })
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 7702134516418120340L;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;
    @JsonProperty("errors")
    private List<Object> errors;

    @JsonProperty("data")
    private Object data;

    @JsonIgnore
    private HttpStatus status;
}
