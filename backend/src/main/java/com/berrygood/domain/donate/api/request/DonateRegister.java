package com.berrygood.domain.donate.api.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonateRegister {
    private String email;
    private String name;
    private String content;
    private Date due;
    private List<MultipartFile> files;
}