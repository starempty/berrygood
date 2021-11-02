package com.berrygood.domain.funding.api.request;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
public class FundingInfo {
    private String email;
    private String name;
    private String content;
    private int amount;
    private Date due;
    private Date time;
    private List<MultipartFile> files;
    private int clac;
}
