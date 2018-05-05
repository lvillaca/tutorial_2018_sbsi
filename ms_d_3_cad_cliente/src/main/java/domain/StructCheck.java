package domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class StructCheck implements Serializable {

    private final long id;
    private final String content;
}
