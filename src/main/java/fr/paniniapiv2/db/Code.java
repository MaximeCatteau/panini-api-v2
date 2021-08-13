package fr.paniniapiv2.db;

import fr.paniniapiv2.enums.CodeStatus;
import fr.paniniapiv2.enums.CodeType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "codes")
@Getter
@Setter
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private Long playerAssociated;

    private CodeType codeType;

    private CodeStatus status;

    public String generateRandomCode() {
        Code c = new Code();
        String alphanumerics = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

        StringBuilder sb1 = new StringBuilder(4);
        StringBuilder sb2 = new StringBuilder(4);
        StringBuilder sb3 = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {
            int index = (int)(alphanumerics.length() * Math.random());

            sb1.append(alphanumerics.charAt(index));
        }

        for (int i = 0; i < 4; i++) {
            int index = (int)(alphanumerics.length() * Math.random());

            sb2.append(alphanumerics.charAt(index));
        }

        for (int i = 0; i < 4; i++) {
            int index = (int)(alphanumerics.length() * Math.random());

            sb3.append(alphanumerics.charAt(index));
        }

        return sb1.append(sb2).append(sb3).toString();
    }
}
