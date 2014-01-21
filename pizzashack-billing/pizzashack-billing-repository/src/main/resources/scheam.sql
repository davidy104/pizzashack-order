--sequences
create sequence ACCOUNT_SEQ;
create sequence ACCOUNT_TRANS_SEQ;

create table T_ACCOUNT (
	ACCOUNT_ID number(10) not null,
	ACCOUNT_NO varchar2(50),
	SECURITY_NO varchar2(10),
	ACCOUNT_TYPE number(2),
	BALANCE number(10,2),
	CREATE_TIME datetime,
	EXPIRE_DATE date
);
alter table T_ACCOUNT add constraint T_ACCOUNT_PK primary key (ACCOUNT_ID);

create table T_ACCOUNT_TRANSACTION (
	ACCOUNT_TRANS_ID number(10) not null,
	ACCOUNT_TRANS_NO varchar2(50),
	TRANS_TYPE number(2),
	TRANS_AMT number(10,2),
	CREATE_TIME datetime,
	ACCOUNT_ID number(10) not null
);
alter table T_ACCOUNT_TRANSACTION add constraint T_ACCOUNT_TRANSACTION_PK primary key (ACCOUNT_TRANS_ID);


create table T_BILLING_REQUEST_HISTORY (
	message_id varchar2(50) not null,
	processor_name varchar2(50),
	TRANS_NO varchar2(50),
	ACCOUNT_NO varchar2(50),
	CODE varchar2(50),
	reasons varchar2(250),
	created_at datetime,
);
alter table T_BILLING_REQUEST_HISTORY add constraint T_BILLING_REQUEST_HISTORY_PK primary key (message_id);

