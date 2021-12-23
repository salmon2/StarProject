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

#### 📆프로젝트 기간
2021년 10월 25일 ~ 2021년 12월 03일

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
- <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">
- <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">
- <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
- <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=Nginx&logoColor=white">
- <img src="https://img.shields.io/badge/github Action-181717?style=for-the-badge&logo=github&logoColor=white">
- <img src="https://img.shields.io/badge/Spring Admin-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
- <img src="https://img.shields.io/badge/Spring JPA-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
- <img src="https://img.shields.io/badge/AWS EC2-232F3E?style=for-the-badge&logo=Amazon%20AWS&logoColor=white"/>
- <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
- <img src="https://img.shields.io/badge/jasypt-EF2D5E?style=for-the-badge&logo=jasypt&logoColor=white">
- <img src="https://img.shields.io/badge/QueryDsl-9999FF?style=for-the-badge&logo=QueryDsl&logoColor=white">
- <img src="https://img.shields.io/badge/CodeDeploy-F8DC75?style=for-the-badge&logo=CodeDeploy&logoColor=white">

## 설계
- API 설계
  - https://www.notion.so/48d7ae150c7a4d3a893a19052ab41e0d?v=c131cd1c89344c12bba6513e9a4c5399
- 아키텍쳐
  - ![image](https://user-images.githubusercontent.com/23234577/144532220-b4807cc3-9f86-47c3-9791-f86bd36030f0.png)
- 도메인 설계
  - ![image](https://user-images.githubusercontent.com/23234577/144532891-39a637f7-86bd-42c2-b11c-1d1e2cb7dfc8.png)


## 주요 기능
### ⭐️별자리 페이지
- 사용자의 위치 정보를 받아서 그 위치의 날씨나 천문지표를 실시간으로 보여줍니다.
- 월출,월몰 정보를 제공하여 별을 잘 볼수있는 시간을 제공합니다.
### 📌지도 페이지
- 사용자와 가까운 다른 사용자들의 추천 장소를 보여줍니다.
- `모두보기`를 통해서 전체 캠핑장 및 사용자들이 작성한 장소를 표시해줍니다.
- 지명과 게시물의 이름을 통해 검색이 가능합니다.
### 🎯커뮤니티
- 추천순,인기순,최신순으로 정렬하여 사용자에게 원하는 게시글을 보여줍니다.
- 좋아요를 통해 집계하여 인기순으로 정렬해서 보여줍니다.
- 북마크를 통해 원하는 게시글을 저장하고 마이페이지에서 확인할수 있습니다.

## 유저 피드백 및 개선 사항
### 유저 피드백

![image](https://user-images.githubusercontent.com/91645519/144547307-056a12e2-5c81-4c76-a907-8de0eff340da.png)

+ "별자리 설명을 클릭 유지시가 아니라 클릭으로 켜고 끄는게 좋을것 같아요"
+ ![image](https://user-images.githubusercontent.com/91645519/144548193-df7fe981-5217-4d91-8c28-c7e595fc7d25.png)
	- 별자리 설명버튼을 클릭시 설명란 토글기능 추가
	
+ "관측 지수에 따라 문구가 달라졌으면 좋겠어요"
+ ![image](https://user-images.githubusercontent.com/91645519/144548055-19ee734c-def1-44bc-8021-8ee4dfea9886.png)
	- 관측 지수에 따라 문구 변경 "**별보기좋은날:)**"
	
### 개선사항
+ JPA 버전으로 개발 이후 성능 개선을 위해 QeryDSL도입
	- 3.089초에서 0.251초로 개선
+ AWS EC2에 배포를 위해 FileZilla를 통해 수동배포
	- Github Action을 통한 자동배포 구현
+ 서버가 죽었을때 대처를 위한 이중화
	- Ngnix를 통한 무중단 배포와 서버 이중화 해결
+ 서버 모니토링 기능 추가
        - Spring Admin을 통한 모니토링 서버 추가
