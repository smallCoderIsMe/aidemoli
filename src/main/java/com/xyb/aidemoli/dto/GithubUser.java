package com.xyb.aidemoli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GithubUser {
    private String name;
    private Long id ;
    private String bio;
    private String login;
    private String avatar_url;
}
