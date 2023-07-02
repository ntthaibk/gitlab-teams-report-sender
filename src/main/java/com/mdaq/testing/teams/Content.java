package com.mdaq.testing.teams;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Content {
    private String type;
    @SerializedName("$schema")
    private String schema;
    private String version;
    private List<Body> body;


    
}
