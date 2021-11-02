package com.berrygood.domain.volunteer.api.request;

import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.berrygood.domain.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class VolunteerInfo {
    private String email;
    private String name;
    private String content;
    private int num;
    private Date date;
    private String type;
    private String time;
    private List<MultipartFile> files;
}
