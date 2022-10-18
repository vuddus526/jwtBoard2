# Spring 심화 주차 과제

<aside>
🏁 **Goal:  " 요구사항에 맞춰 API 구성 및 Git을 활용하여 협업하기"**

</aside>

- 학습 과제를 끝내고 나면 할 수 있어요!
    1. Git 원격 repo를 사용할 수 있고, branch 전략을 세워 협업 환경을 구성하여 개발할 수 있어요.
    2. 복잡한 비즈니스 요구사항을 보고 연관관계를 정하고 구현해낼 수 있어요.

❗ API만 개발하면 됩니다! 프론트엔드 영역은 고민하실 필요가 없어요.

<aside>
🚩 **Requirement:  과제에 요구되는 사항이에요**

</aside>

<aside>
✅ 협업

아래 내용들 기능별로 팀원끼리 분배하고, 각자 원격 repo 생성 및 branch 전략 세워서 진행

cf. [git branch 전략](https://velog.io/@kw2577/Git-branch-%EC%A0%84%EB%9E%B5)

</aside>

### 로그인과 마이페이지

- 기능
    - AccessToken과 RefreshToken을 사용해서 회원가입과 로그인 기능 구현
    - 내가 작성한 게시글 조회하기
    - 내가 작성한 댓글 조회하기
    - 내가 좋아요한 게시글 조회하기
- 조건
    - 회원가입 : 이름, 이메일(아이디), 비밀번호, 생성일자, 수정일자
    - 로그인이 필요한 기능과 필요하지 않은 기능 구분하기
- 에러처리
    - 회원가입 시 이메일 형식이 유효하지 않은 경우, 비밀번호가 영어대소문자, 숫자, 특수문자를 모두 포함하지 않은 경우
    - 토큰이 없거나, 유효하지 않은 경우

### 게시글/댓글 CRUD

- 기능
    - 게시글 작성하기, 게시글 전체조회하기, 게시글 1개 조회하기, 게시글 수정하기(작성자만), 게시글 삭제하기(작성자만)
    - 댓글 작성하기, 댓글 수정하기(작성자만), 댓글 삭제하기(작성자만)
- 조건
    - 게시글 : 제목, 내용, 작성자(아이디), 생성일자, 수정일자
    - 댓글 : 내용, 작성자(아이디), 생성일자, 수정일자
    - 게시글을 조회할 때는 댓글, 게시글 좋아요 개수도 함께 response
- 에러처리
    - 게시글의 제목과 내용이 없는 경우
    - 댓글의 내용이 없는 경우
    - db에서 게시글과 댓글의 id를 찾을 수 없는 경우
    - 수정과 삭제의 경우 작성자가 아닌 경우

### 게시글 좋아요

- 기능
    - 게시글 좋아요 기능
- 조건
    - 유저는 한 게시글을 한 번만 좋아요 할 수 있음
- 에러처리
    - 해당 유저가 이미 게시글에 좋아요를 한 경우
    
<aside>
🏃 **Challenge:** 과제를 다 하셨다면? (필수 ❌)

</aside>

- `과제 요구 사항`을 모두 완수했다면, 다음 단계에 도전해보세요!
    - **1단계**
        - S3를 사용해서 이미지 업로드 기능 만들기
            - 게시글 작성시 이미지 1개 업로드 할 수 있도록 api 수정하기
            - 게시글 조회시 저장된 이미지 url도 함께 조회하기
        - 대댓글 기능 만들기
            - 대댓글 작성하기
            - 댓글 조회 시 대댓글도 함께 조회하기
        - @ExceptionHandler를 사용해서 에러 처리하기
    - **2단계**
        - 게시글 카테고리 만들기
            - 게시글을 분류하는 카테고리를 만들어서 게시글을 작성할 때 카테고리 정보도 함께 저장하기
            - 카테고리 별로 게시글을 조회하는 기능 추가하기
        - 게시글과 댓글 조회할 때 페이징 기능 추가하기
        - 스케줄러
            - 매일 AM 01:00 마다 댓글이 0개인 게시물 전체 삭제하기
            - 삭제될 때마다 `"게시물 <{게시물 이름}>이 삭제되었습니다."` 라는 info level log 남기기
                
                ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cd1268dc-eb30-4286-8c21-62ebf1979049/Untitled.png)
                
            - cf. 스케줄러 example
                - 요구 기능
                    
                    <aside>
                    👉 매일 새벽 1시에 관심 상품 목록 제목으로 검색해서, 최저가 정보를 업데이트하는 스케줄러를 만들어보겠습니다.
                    
                    </aside>
                    
                - Scheduler 만들기
                    - **ExampleApplication 클래스**
                        
                        ```jsx
                        @EnableScheduling // 스프링 부트에서 스케줄러가 작동하게 합니다.
                        @EnableJpaAuditing // 시간 자동 변경이 가능하도록 합니다.
                        @SpringBootApplication // 스프링 부트임을 선언합니다.
                        public class ExampleApplication {
                        
                            public static void main(String[] args) {
                                SpringApplication.run(ExampleApplication.class, args);
                            }
                        
                        }
                        ```
                        
                    - **Scheduler 클래스**
                        
                        ```jsx
                        @RequiredArgsConstructor // final 멤버 변수를 자동으로 생성합니다.
                        @Component // 스프링이 필요 시 자동으로 생성하는 클래스 목록에 추가합니다.
                        public class Scheduler {
                        
                            private final ProductRepository productRepository;
                            private final ProductService productService;
                            private final NaverShopSearch naverShopSearch;
                        
                            // 초, 분, 시, 일, 월, 주 순서
                            @Scheduled(cron = "0 0 1 * * *")
                            public void updatePrice() throws InterruptedException {
                                System.out.println("가격 업데이트 실행");
                                // 저장된 모든 관심상품을 조회합니다.
                                List<Product> productList = productRepository.findAll();
                                for (int i=0; i<productList.size(); i++) {
                                    // 1초에 한 상품 씩 조회합니다 (Naver 제한)
                                    TimeUnit.SECONDS.sleep(1);
                                    // i 번째 관심 상품을 꺼냅니다.
                                    Product p = productList.get(i);
                                    // i 번째 관심 상품의 제목으로 검색을 실행합니다.
                                    String title = p.getTitle();
                                    String resultString = naverShopSearch.search(title);
                                    // i 번째 관심 상품의 검색 결과 목록 중에서 첫 번째 결과를 꺼냅니다.
                                    List<ItemDto> itemDtoList = naverShopSearch.fromJSONtoItems(resultString);
                                    ItemDto itemDto = itemDtoList.get(0);
                                    // i 번째 관심 상품 정보를 업데이트합니다.
                                    Long id = p.getId();
                                    productService.updateBySearch(id, itemDto);
                                }
                            }
                        }
                        ```
                        
                    - **ProductService 클래스**
                        
                        ```jsx
                        @RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
                        @Service // 서비스임을 선언합니다.
                        public class ProductService {
                        
                            private final ProductRepository productRepository;
                        
                            @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
                            public Long update(Long id, ProductMypriceRequestDto requestDto) {
                                Product product = productRepository.findById(id).orElseThrow(
                                        () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
                                );
                                product.update(requestDto);
                                return id;
                            }
                        
                            @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
                            public Long updateBySearch(Long id, ItemDto itemDto) {
                                Product product = productRepository.findById(id).orElseThrow(
                                        () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
                                );
                                product.updateByItemDto(itemDto);
                                return id;
                            }
                        }
                        ```
                        
                    - **Product 클래스**
                        
                        ```java
                        @Getter // get 함수를 일괄적으로 만들어줍니다.
                        @NoArgsConstructor // 기본 생성자를 만들어줍니다.
                        @Entity // DB 테이블 역할을 합니다.
                        public class Product extends Timestamped{
                        
                            // ID가 자동으로 생성 및 증가합니다.
                            @GeneratedValue(strategy = GenerationType.AUTO)
                            @Id
                            private Long id;
                        
                            // 반드시 값을 가지도록 합니다.
                            @Column(nullable = false)
                            private String title;
                        
                            @Column(nullable = false)
                            private String image;
                        
                            @Column(nullable = false)
                            private String link;
                        
                            @Column(nullable = false)
                            private int lprice;
                        
                            @Column(nullable = false)
                            private int myprice;
                        
                            // 관심 상품 생성 시 이용합니다.
                            public Product(ProductRequestDto requestDto) {
                                this.title = requestDto.getTitle();
                                this.image = requestDto.getImage();
                                this.link = requestDto.getLink();
                                this.lprice = requestDto.getLprice();
                                this.myprice = 0;
                            }
                        
                            public void updateByItemDto(ItemDto itemDto) {
                                this.lprice = itemDto.getLprice();
                            }
                        
                            // 관심 가격 변경 시 이용합니다.
                            public void update(ProductMypriceRequestDto requestDto) {
                                this.myprice = requestDto.getMyprice();
                            }
                        }
                        ```
                        
                - 확인
                    - 임의의 시간을 넣어 작동함을 확인합니다.
        - 에러코드(enum) 적용하기
        - Jpa 심화개념 공부하고 코드에 적용해보기
            - 즉시로딩과 지연로딩
            - Cascade
