package com.lacuevita305.adPublisher.persistence.entities;

import com.lacuevita305.adPublisher.enums.PublishStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ad_to_publish")
public class Ad extends BaseEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private String price;
    @Column(name = "currency")
    private String currency;
    @Lob
    @Column(name = "body",columnDefinition = "TEXT")
    private String body;
    @Column(name = "product")
    private String productName;
    @Column(name = "publishStatus")
    private String publishStatus;

    @Override
    public String toString() {
        return "Ad{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                ", body='" + body + '\'' +
                ", category='" + productName + '\'' +
                ", publishStatus=" + publishStatus +
                '}';
    }
}
