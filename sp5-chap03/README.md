# Chapter 03 스프링 DI



## 1. 의존(dependency)이란?

```java
package com.example.sp5chap03.spring;

import java.time.LocalDateTime;

public class MemberRegisterService {
    private MemberDao memberDao = new MemberDao();

    public Long regist(RegisterRequest req) {
        // 이메일로 회원 데이터(Member) 조회
        Member member = memberDao.selectByEmail(req.getEmail());
        if (member != null) {
            // 같은 이메일을 가진 회원이 이미 존재하면 익셉션 발생
            throw new DuplicateMemberException("dup email " + req.getEmail());
        }
        // 같은 이메일을 가진 회원이 존재하지 않으면 DB에 삽입
        Member newMember = new Member(
                req.getEmail(), req.getPassword(), req.getName(),
                LocalDateTime.now());
        memberDao.insert(newMember);
        return newMember.getId();
    }
}
```

* MemberRegisterService 클래스는 MemberDao 객체의 selectByEmail() 메서드를 이용해서 동일한 이메일을 가진 회원 데이터가 존재하는지 확인한다.
* 즉, MemberRegisterService 클래스가 DB 처리를 위해 MemberDao 클래스의 메서드를 사용한다.
* 이렇게 한 클래스가 다른 클래스의 메서드를 실행할 때 이를 '의존'한다고 표현한다. 앞서 코드에서는 `MemberRegisterService 클래스가 MemberDao 클래스에 의존한다`고 표현할 수 있다.
* 또한 의존은 변경에 의해 영향을 받는 관계를 의미한다. 예를 들어 MemberDao의 insert() 메서드의 이름을insertMember()로 변경하면 이 메서드를 사용하는 MemberRegisterService 클래스의 소스 코드도 함께 변경된다. 이렇게 변경에 따른 영향이 전파되는 관계를 `의존`한다고 표현한다.

* 하지만 위 코드처럼, 의존 관계를 직접 생성하는 경우 유지보수 관점에서 문제가 발생할 가능성이 있다. 특히MemberRegisterService 객체를 생성하는 순간에 MemberDao 객체도 함께 생성된다는 문제점이 존재한다. 

```java
private MemberDao memberDao = new MemberDao();
```



## 2. DI를 통한 의존 처리

```java
package com.example.sp5chap03.spring;

import java.time.LocalDateTime;

public class MemberRegisterService {
    private MemberDao memberDao;

    public MemberRegisterService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    // 아래는 동일
    
}
```

* DI: Dependency Injection
* 의존 객체를 직접 구하지 않고 생성자 를 통해서 전달받기 때문에 이 코드는 DI(의존 주입) 패턴을 따르고 있다.
* 오히려 코드가 더 길어진 것 같지만, `변경의 유연함`을 위해 DI 방식을 사용한다.
  * 자세한 설명은 교재 p56 "3. DI와 의존 객체 변경의 유연함" 참고



## 예제 클래스
### 회원 데이터 관련 클래스
* Member
* WrongIdPasswordException 
* MemberDao

### 회원가입 처리 관련클래스
* DuplicateMemberException
* RegisterRequest
* MemberRegisterService 

### 암호변경 관련 클래스
* MemberNotFoundException
* ChangePasswordService