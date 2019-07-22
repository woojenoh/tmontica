use tmontica;
select * from options;



-- users 임시 데이터
insert into users values("김현정", "testid", "test@tmoncorp.com", "1994-11-11", "1234", "user", sysdate(), 5000);
-- orders 임시 데이터
insert into orders values(1, sysdate(), "현장결제", 5000, 2000, 3000, "결제완료", "testid", "모바일");
insert into orders values(2, sysdate(), "현장결제", 7000, 2000, 5000, "준비완", "testid", "모바일");
-- menus 임시 데이터
insert into menus values("americano",1000,"beverage",0,1,"url","맛있는 아메리카노",1000,10,"2019-07-01","2019-07-11","admin","admin",20,"아메리카노",0);
insert into menus values("coppuccino",1500,"beverage",0,1,"url","맛있는 카푸치노",1500,10,"2019-07-01","2019-07-11","admin","admin",20,"카푸치노",0);
-- order_details 임시 데이터
insert into order_details values(0,1, "HOT / 샷추가 (1개) / SIZE UP", 1000, 1, 1);
insert into order_details values(0,1, "HOT / 샷추가 (2개) ", 3000, 2, 2);



select * from orders;
select * from order_details;
select * from menus;



-- 한 개 주문의 메뉴 이름들만 검색
select name_ko from menus inner join order_details on menus.id=order_details.menu_id
where order_details.order_id=1;