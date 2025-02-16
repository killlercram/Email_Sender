package com.emailsender.app.helper;
import lombok.*;
import java.util.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Messages {
    private String from;
    private String content;
    private List<String> files;
    private String subjects;
}
