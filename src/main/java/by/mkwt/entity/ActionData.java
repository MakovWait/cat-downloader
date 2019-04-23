package by.mkwt.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ActionData {

    public enum Type {
        find_and_download
    }

    public enum Status {
        completed,
        errored
    }

    private Long id;
    private Type type;
    private Status status = Status.errored;
    private List<String> result;

}
