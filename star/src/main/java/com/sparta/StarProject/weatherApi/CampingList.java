package com.sparta.StarProject.weatherApi;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public enum CampingList {
    경기1("한탄강관광지 오토캠핑장", "경기도 연천군 전곡읍 선사로 76", "https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20160711_140%2F1468203136385hRBPh_PNG%2F176661514228888_0.png", 127.0588604, 38.008877),
    경기2("중미산자연휴양림", "경기도 양평군 옥천면 신복리 산201-2", "https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_29%2F1423205871447WqGB0_JPEG%2F%25B0%25E6%25B1%25E2_%25BE%25E7%25C6%25F2%25C1%25DF%25B9%25CC%25BB%25EA%25C0%25DA%25BF%25AC%25C8%25DE%25BE%25E7%25B8%25B2_1.JPG%3Ftype%3Dm500_500", 127.4520344, 37.5827073),
    경기3("의왕바라산자연휴양림", "경기도 의왕시 바라산로 84", "https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150901_205%2F1441118072970ITlt1_JPEG%2FSUBMIT_1420502985803_31041041.jpg", 127.0193169, 37.3731009),
    경기4("산장국민관광지", "경기도 가평군 상면 덕현산장길 71", "https://search.pstatic.net/common/?type=f&size=68x68&src=http%3A%2F%2Fblogfiles.naver.net%2F20140811_235%2Fsaying0910_1407731116700B7qCx_JPEG%2FDSC00937.JPG%23900x600", 127.410622, 37.7542233),
    경기5("가평명지산카라반글램핑", "경기도 가평군 북면 가화로 2932-23", "https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20140714_17%2F1405330457495C0J6D_JPEG%2F%25B0%25E6%25B1%25E2_%25B8%25ED%25C1%25F6%25BB%25EA%25BF%25C0%25C5%25E4%25C4%25B7%25C7%25CE%25C0%25E5_1.JPG%3Ftype%3Dm500_500", 127.4476256, 37.9813794),

    강원1("간현관광지","강원도 원주시 지정면 소금산길 12","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150901_127%2F1441034282345mB0ty_JPEG%2F13419069_0.jpg",127.834038,37.3645764),
    강원2("동호해변","강원도 양양군 손양면 동호리 141-26 동호해변관리사무소","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_226%2F1423209177318yl9Am_JPEG%2F%25B0%25AD%25BF%25F8_%25B5%25BF%25C8%25A3%25C7%25D8%25BC%25F6%25BF%25E5%25C0%25E5_1.JPG%3Ftype%3Dm500_500",128.6824685,38.0583391),
    강원3("서피비치","강원도 양양군 현북면 하조대해안길 119 서피비치","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20181128_52%2F1543409303274odqL3_JPEG%2FdcQqL-2GoPnP2KDuWTSAzGV7.jpg",128.7175212,38.0277534),
    강원4("집다리골자연휴양림","강원도 춘천시 사북면 화악지암1길 130 집다리골자연휴양림","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_43%2F1423208638117obM75_JPEG%2F%25B0%25AD%25BF%25F8_%25C3%25E1%25C3%25B5%25C1%25FD%25B4%25D9%25B8%25AE%25B0%25F1%25C0%25DA%25BF%25AC%25C8%25DE%25BE%25E7%25B8%25B2_1.JPG%3Ftype%3Dm500_500",127.5805429,37.9780549),
    강원5("아웃오브파크","강원도 춘천시 남면 가옹개길 52-9","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20180712_109%2F1531404618217M6Csj_JPEG%2FIzUFkyK1BCh2Osq2XXHi6dTL.jpg",127.5112561,37.7278036),

    충북1("옥화자연휴양림","충청북도 청주시 상당구 미원면 운암옥화길 140","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_57%2F1423205591389a4Vt9_JPEG%2F%25C3%25E6%25BA%25CF_%25C3%25BB%25BF%25F8%25BF%25C1%25C8%25AD%25C0%25DA%25BF%25AC%25C8%25DE%25BE%25E7%25B8%25B2_1.JPG%3Ftype%3Dm500_500",127.6943439,36.5989405),
    충북2("송호국민관광지","충청북도 영동군 양산면 송호리 280","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150831_14%2F1441016250863MNcI8_JPEG%2F13298947_0.jpg",127.6774959,36.1308077),
    충북3("속리산사내리야영장","충청북도 보은군 속리산면 법주사로 248-46","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20131218_177%2F1387358014007UoW06_JPEG%2F%25C3%25E6%25BA%25CF_%25BC%25D3%25B8%25AE%25BB%25EA%25BB%25E7%25B3%25BB%25B8%25AE%25BE%25DF%25BF%25B5%25C0%25E5_1.jpg%3Ftype%3Dm500_500",127.8254907,36.530903),
    충북4("충주호캠핑월드","충청북도 충주시 동량면 호반로 696-1","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20141014_47%2F1413280229592Uooxw_JPEG%2F%25C3%25E6%25BA%25CF_%25C3%25E6%25C1%25D6%25C8%25A3%25C4%25B7%25C7%25CE%25BF%25F9%25B5%25E5_1.JPG%3Ftype%3Dm500_500",128.0415329,37.0274654),
    충북5("문암생태공원","충청북도 청주시 흥덕구 문암동 122-2","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20170906_225%2F1504665349685FV4qU_JPEG%2FEgIjKBrhBgRQCBsgh325X6N6.jpg",127.4474894,36.6747049),

    충남1("공주한옥마을","충청남도 공주시 관광단지길 12","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_241%2F1423210245654oPrCt_JPEG%2F%25C3%25E6%25B3%25B2_%25B0%25F8%25C1%25D6%25C7%25D1%25BF%25C1%25B8%25B6%25C0%25BB_1.JPG%3Ftype%3Dm500_500",127.1075917,36.4646856),
    충남2("팜카밀레","충청남도 태안군 남면 우운길 56-19","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_78%2F1423207323496I65NN_JPEG%2F%25C3%25E6%25B3%25B2_%25C5%25C2%25BE%25C8%25C6%25CA%25C4%25AB%25B9%25D0%25B7%25B9%25C7%25E3%25BA%25EA%25B3%25F3%25BF%25F8_1.JPG%3Ftype%3Dm500_500",126.2824206,36.6878947),
    충남3("영인산자연휴양림","충청남도 아산시 영인면 아산온천로 16-26","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150901_65%2F1441039472032VH5QM_JPEG%2F11795970_0.jpg",126.9500272,36.8406007),
    충남4("이안숲속","충청남도 공주시 반포면 수목원길 25","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20191224_276%2F1577146755735gBRLW_JPEG%2F1563853323598.jpg",127.1993763,36.4226939),
    충남5("어은돌오토캠핑장","충청남도 태안군 소원면 파도리 543-541","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20141014_15%2F1413279964007EM1SX_JPEG%2F%25C3%25E6%25B3%25B2_%25BE%25EE%25C0%25BA%25B5%25B9%25BF%25C0%25C5%25E4%25C4%25B7%25C7%25CE%25C0%25E5_1.JPG%3Ftype%3Dm500_500",126.1346912,36.7505667),

    전북1("무주반디랜드","전라북도 무주군 설천면 무설로 1324","https://search.pstatic.net/common/?type=f&size=68x68&src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxNzEwMjZfMjE1%2FMDAxNTA5MDI1MTQ2NzE0.UgZaQ59sSixJ2I6cObFpPEvuNOWy9FM6DZ7_uXXG3N4g.5t6QYY2VSv8yUqMcM1lV-X0PVSHRpwfQmWkny10GJm0g.JPEG.jnlss%2F879.jpg%23900x600",127.7587423,36.0131923),
    전북2("와룡자연휴양림","전라북도 장수군 천천면 비룡로 632 와룡자연휴양림","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_148%2F1423205599255EXzHP_JPEG%2F%25C0%25FC%25BA%25CF_%25C0%25E5%25BC%25F6%25BF%25CD%25B7%25E6%25C0%25DA%25BF%25AC%25C8%25DE%25BE%25E7%25B8%25B2_1.JPG%3Ftype%3Dm500_500",127.4771927,35.6762574),
    전북3("덕유대오토캠핑장","전라북도 무주군 설천면 백련사길 2","https://search.pstatic.net/common/?type=f&size=68x68&src=http%3A%2F%2Fblogfiles.naver.net%2F20140819_60%2Fkppan1_1408410275265xOcuv_PNG%2F1408410265199_1406504342693.png",127.7777884,35.8912467),
    전북4("고사포야영장","전라북도 부안군 변산면 운산리 441-11","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20131218_105%2F1387357060835J2bsC_JPEG%2F%25C0%25FC%25BA%25CF_%25B0%25ED%25BB%25E7%25C6%25F7%25BE%25DF%25BF%25B5%25C0%25E5_1.jpg%3Ftype%3Dm500_500",126.5094373,35.6622947),
    전북5("내장야영장","전라북도 정읍시 내장산로 800 내장야영장","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20141014_20%2F1413279894547JjI6N_JPEG%2F%25C0%25FC%25BA%25CF_%25B3%25BB%25C0%25E5%25BB%25EA%25B1%25B9%25B8%25B3%25B0%25F8%25BF%25F8%25BE%25DF%25BF%25B5%25C0%25E5_1.JPG%3Ftype%3Dm500_500",126.9179063,35.4985816),

    전남1("담양 금성산성 오토캠핑장","전라남도 담양군 금성면 금성리 산90","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20210913_221%2F1631494961412Kanzv_JPEG%2FGFvb-Pu1RXKYIBLtfeO3Xg0n.jpg",127.0345921,35.3675865),
    전남2("심청관광농원 꿈꾸는캠핑장","전라남도 곡성군 고달면 고달리 산27-1","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20170111_210%2F1484112036892eMWld_JPEG%2F___1.jpg",127.3652063,35.2920313),
    전남3("여수굴전여가캠핑장","전라남도 여수시 돌산읍 돌산로 3017-15","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20200211_1%2F15813881116617MQU5_JPEG%2Fimage.jpg",127.7649967,34.6923978),
    전남4("불태산 글램핑","전라남도 장성군 진원면 고산로 61-164","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20210714_139%2F1626268304326qVaM4_JPEG%2FKnALaBVZAOAu9rOewJaOY-zq.jpeg.jpg",126.8366343,35.2868281),
    전남5("슬로우위켄드SLOWWEEKEND","전라남도 장성군 황룡면 구대해길 3-12","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20210622_17%2F1624347130062QOckJ_JPEG%2F%25DA%25B0%25E3%25E1%25F8%25B8_20210620123304.jpg",126.751802,35.2636472),

    경북1("통고산자연휴양림","경상북도 울진군 금강송면 불영계곡로 880","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_192%2F1423205921848PJEwW_JPEG%2F%25B0%25E6%25BA%25CF_%25BF%25EF%25C1%25F8%25C5%25EB%25B0%25ED%25BB%25EA%25C0%25DA%25BF%25AC%25C8%25DE%25BE%25E7%25B8%25B2_1.JPG%3Ftype%3Dm500_500",129.1917804,36.9337019),
    경북2("청도 신화랑풍류마을","경상북도 청도군 운문면 신화랑길 1","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20171012_24%2F1507793778818v31ph_JPEG%2FLo2W9htZeA6UGZ-xn-Vv3Q6m.jpg",128.9223074,35.7180765),
    경북3("옥계계곡","경상북도 영덕군 달산면 팔각산로 662","https://search.pstatic.net/common/?type=f&size=68x68&src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxNzA4MjRfNTkg%2FMDAxNTAzNTMyMjQ2MDg4.DYRze8pIiC6uUvJoJRc50Pf8WKm9dWRmFtdptNRXc9Eg.gYYRX0VJz-Gpy-ravZxxLAxCo_6tsyFklLiBSCIMU0Ug.JPEG.warm_today%2FIMG_0461.jpg%23936x624",129.2589389,36.3297298),
    경북4("구수곡자연휴양림","경상북도 울진군 북면 십이령로 2721","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_112%2F14232054875822Tjb1_JPEG%2F%25B0%25E6%25BA%25CF_%25BF%25EF%25C1%25F8%25B1%25B8%25BC%25F6%25B0%25EE%25C0%25DA%25BF%25AC%25C8%25DE%25BE%25E7%25B8%25B2_1.JPG%3Ftype%3Dm500_500",129.2929279,37.0621216),
    경북5("송정자연휴양림","경상북도 칠곡군 석적읍 반계3길 88","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_223%2F1423208622562DsLqD_JPEG%2F%25B0%25E6%25BA%25CF_%25C4%25A5%25B0%25EE%25BC%25DB%25C1%25A4%25C0%25DA%25BF%25AC%25C8%25DE%25BE%25E7%25B8%25B2_1.JPG%3Ftype%3Dm500_500",128.4416412,36.0233342),

    경남1("금원산자연휴양림","경상남도 거창군 위천면 금원산길 471-27 복합산막콘도","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150206_259%2F14232057352450M0Uj_JPEG%2F%25B0%25E6%25B3%25B2_%25B0%25C5%25C3%25A2%25B1%25DD%25BF%25F8%25BB%25EA%25C0%25DA%25BF%25AC%25C8%25DE%25BE%25E7%25B8%25B2_1.JPG%3Ftype%3Dm500_500",127.7957274,35.7261757),
    경남2("화왕산자연휴양림","경상남도 창녕군 고암면 청간길 128-126","https://search.pstatic.net/common/?type=f&size=68x68&src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxNzA4MDlfMjQ5%2FMDAxNTAyMjYzMzM2NDE0.5W7hkJNAXQ9Topd5UiMVEb7LdI08Y51sHEJ4gzVi1Xgg.MkVQT7UIzE3nqoaxy6-RdBi4ZVPL4RDBWI-e4t3c9wEg.JPEG.chae0604%2F_DSC1513.jpg%231600x1068",128.5673193,35.5469792),
    경남3("민들레울","경상남도 거창군 북상면 덕유월성로 2188 민들레울","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20200507_263%2F1588833424788NWQWA_PNG%2Fimage.png",127.8052918,35.7743676),
    경남4("럭셔리글램핑 W 풀빌라","경상남도 산청군 산청읍 웅석봉로285번길 80-20 럭셔리글램핑 W","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20210207_18%2F1612688486662ybaYe_JPEG%2FVpuHu1aaaJCzTIR8DPzEuRxh.jpg",127.885965,35.3929513),
    경남5("미르캠핑장","경상남도 밀양시 산외면 밀양대로 3400-127","https://search.pstatic.net/common/?type=f&size=68x68&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20131218_182%2F1387353710594DYBEG_JPEG%2F%25B0%25E6%25B3%25B2_%25B9%25D0%25BE%25E7%25B9%25CC%25B8%25A3%25BF%25C0%25C5%25E4%25C4%25B7%25C7%25CE%25C0%25E5_1.jpg%3Ftype%3Dm500_500",128.8602509,35.5435596);

    private String name;
    private String address;
    private String imgSrc;
    private double locationX;
    private double locationY;

    CampingList(String name, String address, String imgSrc, double locationX, double locationY) {
        this.name = name;
        this.address = address;
        this.imgSrc = imgSrc;
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
