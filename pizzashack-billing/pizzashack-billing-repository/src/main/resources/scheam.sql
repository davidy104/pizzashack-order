--sequences
create sequence account_seq;
create sequence account_hist_seq;

create table t_account (
	account_id number(10) not null,
	account_no varchar2(20),
	security_no varchar2(10),
	account_type number(2),
	balance number(10,2),
	create_time datetime,
	expire_date date
);
alter table t_account add constraint t_account_pk primary key (account_id);

create table t_account_history (
	account_hist_id number(10) not null,
	account_trans_no varchar2(20),
	trans_type number(2),
	trans_amount number(10,2),
	create_time datetime,
	account_id number(10) not null
);
alter table t_account_history add constraint t_account_history_pk primary key (account_hist_id);

