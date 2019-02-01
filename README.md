# 서울 살이 :goat:
![Seoul42 Service](https://img.shields.io/badge/service-android-green.svg)
![Seoul42 Pattern](https://img.shields.io/badge/pattern-MVVM-blue.svg)
[![Seoul42 DI](https://img.shields.io/badge/DI-koin-blue.svg)](https://insert-koin.io)
[![Seoul42_UI](https://img.shields.io/badge/UI-zeplin-blue.svg)](https://app.zeplin.io/project/5c4db2597a8bebbfe8be9d39/dashboard)

[![Seoul42_UI](https://img.shields.io/badge/library-RxJava2-blue.svg)](https://github.com/amitshekhariitbhu/RxJava2-Android-Samples)
[![Seoul42_UI](https://img.shields.io/badge/library-Room-blue.svg)](https://developer.android.com/topic/libraries/architecture/room.html)
[![Seoul42_UI](https://img.shields.io/badge/library-LiveData-blue.svg)](https://developer.android.com/topic/libraries/architecture/livedata.html)


booscamp3_C팀의 서울살이 repository입니다. 해당 프로젝트는 MVVM기반의 Android프로젝트입니다. 
의존성 주입을 위해서 koin이 사용되었고, RxJava, Room, LiveData, MotionLayout등 꾸준한 학습에 의해서 진행되고 있습니다.

#### 프로토타입 UI: [![Seoul42_UI](https://img.shields.io/badge/UI-zeplin-blue.svg)](https://app.zeplin.io/project/5c4db2597a8bebbfe8be9d39/dashboard)
![image](https://user-images.githubusercontent.com/22374750/52126227-17dd0a00-2672-11e9-9678-2cf1e2aeb6fc.png)

해당 프로젝트는 협업툴을 Zeplin을 사용했습니다. 구체적인 내용은 해당 뱃지를 누르면 확인할 수 있습니다.<br>
저희는 Zeplin을 통해서 Style 가이드를 작성했고, 각종 xml의 가이드 기준을 정했습니다.

#### 패키지 소개:
1. **api**: Retrofit을 사용하기 위한 interface를 모아두는 패키지입니다.
2. **di**: Koin을 사용하여 의존성 주입을 했고, 해당 패키지에 di 관련 모듈을 만들었습니다.
3. **firebase**: Firebase에 관련한 유틸리티성 클래스를 모아두는 패키지입니다.
4. **model**: 주소, 북마크, 집정보, 가격등에 관련한 데이터 클래스를 모아두는 패키지입니다.
5. **repository**: ViewModel과 Model사이에 repository패턴을 사용해서 각종 값을 받아오는 클래스를 모아둔 패키지입니다.
6. **ui**: View에 해당하는 클래스들을 모아두는 패키지입니다.
7. **util**: 프로젝트내에서 각종 유틸리티성 클래스를 모아두는 패키지입니다.

### [기획안](https://drive.google.com/file/d/1ui4lvMc81kCAki4UVtxirsg0szLEbqD2/view?usp=sharing)
### [일정관리](https://docs.google.com/spreadsheets/d/1nQlae8ONeO42Rk9Pr0tZFxilmCsfuNRmOOc4p7cFlYY/edit?usp=sharing)
### [UI기획](https://drive.google.com/file/d/13BLtMr3i-YnhjuDIkYmLRVolUX6OQnIu/view?usp=sharing)
### [기능명세서](https://docs.google.com/spreadsheets/d/1Y4Xpb8lSP5qQ53e1NPewsZMmYud5io1H1SQxxZZOmY4/edit?usp=sharing)

### [서버](https://github.com/seoul42/seoul42-server) 서울살이 서버 github 입니다.

## 1주차 산출물 
[DOCS](https://github.com/boostcampth/boostcamp3_C/tree/dev/docs) 문서 작업 (기획서, 기능정의서, 프로젝트 일정, Api명세서)

Zeplin
![Zeplin](/docs/ui/seoul42_ui.png)

