## Fix(log)

 Fix(log)는 개발 과정에서 마주한 에러와 해결 과정을 체계적으로 기록하고,
키워드 및 태그 기반으로 다시 탐색할 수 있도록 설계한 트러블슈팅 아카이빙 서비스입니다.
<img width="1536" height="858" alt="image" src="https://github.com/user-attachments/assets/da464225-7c1e-43cd-9911-d62d033cef24" />


### Core Features
Fix(log)는 단순 게시판이 아니라,  
에러 기록을 **검색 가능하고 다시 활용 가능한 지식 자산**으로 만들기 위해 다음 기능들로 구성했습니다.

- 키워드 + 태그 기반 검색
- 최신글 / 인기글 탐색
- 게시글 작성 및 아카이빙
- 마이페이지에서 작성글 / 북마크 / 좋아요 관리
<img width="1536" height="863" alt="image" src="https://github.com/user-attachments/assets/3cc88bfc-8dad-4167-9e56-1e3f17828826" />

### User Flow
Fix(log)의 주요 사용자 흐름은  
**가입 → 검색/탐색 → 아카이빙 → 포스팅** 으로 이어지도록 설계했습니다.
<img width="736" height="412" alt="image" src="https://github.com/user-attachments/assets/add544c3-55a2-4bb7-8b3e-5b37eb0e6864" />


### Team R&R
<img width="735" height="410" alt="image" src="https://github.com/user-attachments/assets/30db3596-8554-4e83-a1c7-b476d81a7115" />

### Backend Stack & Responsibilities
**Tech Stack**
- Spring Boot
- Java 21
- MySQL
- AWS EC2 / RDS / S3
- GitHub Actions
<img width="734" height="410" alt="image" src="https://github.com/user-attachments/assets/13d846ef-569c-4c99-9a50-a71721d7d7ff" />

### System Architecture
GitHub Actions 기반 CI 파이프라인으로 빌드한 애플리케이션을
AWS S3와 CodeDeploy를 통해 EC2에 배포했으며,
애플리케이션 데이터는 Amazon RDS(MySQL)에 저장되도록 구성했습니다.

<img width="734" height="412" alt="image" src="https://github.com/user-attachments/assets/770ce3af-92e1-4293-9d66-2ce67c7b0df6" />


