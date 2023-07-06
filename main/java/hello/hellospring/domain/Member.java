package hello.hellospring.domain;

public class Member {

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
