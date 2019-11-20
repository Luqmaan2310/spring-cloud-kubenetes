package za.co.solinta.organisationservice;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Book {
    private Long id;
    private String author;
    private String title;

    public Book(Long id, String title, String author) {
        this.id = id;
        this.author = author;
        this.title = title;
    }
}
