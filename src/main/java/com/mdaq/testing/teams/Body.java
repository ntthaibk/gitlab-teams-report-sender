package com.mdaq.testing.teams;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Body {
    private String type;
    private String style;
    private List<Items> items;
    private List<Facts> facts;
    private List<Actions> actions;
    private String horizontalAlignment;
    private String spacing;
    private String text;
    private String wrap;
    private String fontType;
    private String size;
    private String weight;
    private String id;
    private String separator;

    
}
