#########################################
#CSV 발송 발송 방법
#########################################
1. 일반 CSV 발송 데이타 포맷
	발송아이디, 이름
	ex)
	MIUM001,홍길동1
	MIUM002,홍길동2
	
	
2. 템플릿 CSV발송데이타 포맷
	발송아이디, 이름, 변수1,변수2... 변수9
	ex)
	MIUM001,홍길동1,"2017-12-12","싼타페","267300"
	MIUM002,홍길동2,"2018-12-01","그랜저","267300"
	
	<<발송 메세지 예제>> 
	발송메세지 : 안녕하세요. %CNAME%님! 날자 : %VAR1%에 차종 %VAR2%을 타고 기름 %VAR3%을 넣음. 