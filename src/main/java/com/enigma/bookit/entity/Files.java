package com.enigma.bookit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="files")
public class Files {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name ="system-uuid", strategy="uuid")
    @Column(name="file_id")
    private String id;
    private String name;
    private String type;
    private String url;

    @Lob
    private byte [] data;

    public Files(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Files{" +
                "url='" + url + '\'' +
                '}';
    }
}
