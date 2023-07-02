package com.mdaq.testing.teams;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdaptiveCard {
    private String type;
    private List<Attachments> attachments;
}
