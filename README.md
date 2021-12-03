# 별 보러 가지 않을래?⭐️
![썸네일](https://user-images.githubusercontent.com/23234577/144531606-3f50649e-982d-4a33-88b4-51558b4f4273.png)

캠핑,차박 하실때 별⭐️도 구경해보세요! 

나만의 장소도 공유하고 다른분들과 추억도 공유해보세요!

## 목차
1. [프로젝트 소개](##프로젝트-소개)
2. [팀원](##팀원)
3. [기술 스텍](##기술-스택)
4. [설계](##설계)
5. [주요기능](##주요기능)
6. [유저 피드백](###유저-피드백) 및 [개선사항](###개선사항)

## 프로젝트 소개
캠핑,차박 하실때 별⭐️도 구경해보세요!</br> 나만의 장소도 공유하고 다른분들과 추억도 공유해보세요!👫

- 홈페이지 : [stellakorea](https://stellakorea.co.kr/ "별 보러 가지 않을래?") </br>
- 소개 영상 : https://youtu.be/mB3gAzsY8s4
- 날씨, 천문 데이터 업데이트 서버 : https://github.com/salmon2/StarProjectLocationDB
- 모니터링 서버 : https://github.com/salmon2/StarMonitoringServer

## 팀원
### **Backend**
- 김가민
- 박기남
- 박시준
### **Frontend**
- 심선아
- 이지연
- 홍유미
### **Designer**
- 박선현
- 한주혜

## 기술 스텍
- **Java**
- **Spring Boot**
- **MySQL**
- **Nginx**
- **Github Action**
- **Spring Admin**
- **Spring JPA**
- **AWS EC2**
- **Spring Security**
- **jasypt**
- **QueryDsl**
- **CodeDeploy**

## 설계
- API 설계
  - https://www.notion.so/48d7ae150c7a4d3a893a19052ab41e0d?v=c131cd1c89344c12bba6513e9a4c5399
- 아키텍쳐
  - <img src="https://user-images.githubusercontent.com/23234577/144532220-b4807cc3-9f86-47c3-9791-f86bd36030f0.png" width="720px" height="360">
- 도메인 설계
  - <img src="https://user-images.githubusercontent.com/23234577/144532891-39a637f7-86bd-42c2-b11c-1d1e2cb7dfc8.png" width="720px" height="480px">


## 주요 기능
### ⭐️별자리 페이지
- 사용자의 위치 정보를 받아서 그 위치의 날씨나 천문지표를 실시간으로 보여줍니다.
- 월출,월몰 정보를 제공하여 별을 잘 볼수있는 시간을 제공합니다.
<img src="https://user-images.githubusercontent.com/23234577/144536914-255b4819-dcf1-4e11-8edb-5a84f642dfe9.png" width="720px" height="480px">

### 📌지도 페이지
- 사용자와 가까운 다른 사용자들의 추천 장소를 보여줍니다.
- `모두보기`를 통해서 전체 캠핑장 및 사용자들이 작성한 장소를 표시해줍니다.
- 지명과 게시물의 이름을 통해 검색이 가능합니다.
<img src="https://user-images.githubusercontent.com/23234577/144536935-69d55c12-6ddd-45c2-990f-4ba1ac2e175b.png" width="720px" height="480px">

### 🎯커뮤니티
- 추천순,인기순,최신순으로 정렬하여 사용자에게 원하는 게시글을 보여줍니다.
- 좋아요를 통해 집계하여 인기순으로 정렬해서 보여줍니다.
- 북마크를 통해 원하는 게시글을 저장하고 마이페이지에서 확인할수 있습니다.
<img src="https://user-images.githubusercontent.com/23234577/144536946-bbb7c6f9-614a-482d-b051-ebdf5e668c3e.png" width="720px" height="480px">

### 상세페이지
- 게시글의 상세한 내용을 보여줍니다.
- 해당하는 장소의 날씨와 천문 지표를 추가로 보여줍니다.
- 해당하는 장소의 대략적인 위치를 지도와 마커로 표시해줍니다.
<img src="https://user-images.githubusercontent.com/23234577/144537121-2d1641c4-f710-41f9-91ea-99312ea999ff.png" width="720px" height="480px">

## 유저 피드백 및 개선 사항
### 유저 피드백
+ "별자리 설명을 클릭 유지시가 아니라 클릭으로 켜고 끄는게 좋을것 같아요"
	- 별자리 설명버튼을 클릭시 설명란 토글기능 추가
+ "관측 지수에 따라 문구가 달라졌으면 좋겠어요"
	- 관측 지수에 따라 문구 변경 "**별보기좋은날:)**"
### 개선사항
+ JPA 버전으로 개발 이후 성능 개선을 위해 QeryDSL도입
	- 3.089초에서 0.251초로 개선
+ AWS EC2에 배포를 위해 FileZilla를 통해 수동배포
	- Github Action을 통한 자동배포 구현
+ 서버가 죽었을때 대처를 위한 이중화
	- Ngnix를 통한 무중단 배포와 서버 이중화 해결
