package hello.hellospring.domain;

import jakarta.persistence.*;

// jpa가 관련하는 entity가 됨!
@Entity
public class Member {

    // db에 값을 넣어줄 때 ID값은 자동으로 생성되는데, 이걸 Identitiy!!!
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    // 이때 id = 데이터 저장을 위한 임의의 값임! 고객이 설정한 값 X
    private Long id;
    private String name;

    // getter & setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

} // end class
