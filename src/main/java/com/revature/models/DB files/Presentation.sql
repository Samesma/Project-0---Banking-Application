
CREATE TABLE public.users (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	first_name varchar(30) NOT NULL,
	last_name varchar(30) NOT NULL,
	email varchar(50) NOT NULL,
	username varchar(64) NOT NULL,
	passwd varchar(50) NOT NULL,
	ssn varchar(15) NOT NULL,
	usertype varchar(10) NOT NULL,
	CONSTRAINT users_email_key UNIQUE (email),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT users_ssn_key UNIQUE (ssn),
	CONSTRAINT users_username_key UNIQUE (username)
);



CREATE TABLE public.accounts (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	acc_number int4 NOT NULL DEFAULT nextval('accnoseq'::regclass),
	balance numeric(20, 2) NOT NULL,
	acc_type varchar(30) NOT NULL,
	start_date varchar(50) NOT NULL DEFAULT now(),
	status varchar(64) NOT NULL,
	CONSTRAINT accounts_acc_number_key UNIQUE (acc_number),
	CONSTRAINT accounts_pkey PRIMARY KEY (id)
);



CREATE TABLE public.accounts_users_junction (
	user_id int4 NOT NULL,
	account_id int4 NOT NULL,
	CONSTRAINT accounts_users_junction_pkey PRIMARY KEY (user_id, account_id)
);


-- public.accounts_users_junction foreign keys

ALTER TABLE public.accounts_users_junction ADD CONSTRAINT accounts_users_junction_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.accounts(id);
ALTER TABLE public.accounts_users_junction ADD CONSTRAINT accounts_users_junction_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


CREATE TABLE public.transactions (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	"source" int4 NOT NULL,
	destination int4 NOT NULL,
	amount int4 NOT NULL,
	requserid int4 NOT NULL,
	regdate varchar(40) NULL DEFAULT now(),
	CONSTRAINT transactions_pkey PRIMARY KEY (id)
);


-- public.transactions foreign keys

ALTER TABLE public.transactions ADD CONSTRAINT transactions_destination_fkey FOREIGN KEY (destination) REFERENCES public.accounts(id);
ALTER TABLE public.transactions ADD CONSTRAINT transactions_requserid_fkey FOREIGN KEY (requserid) REFERENCES public.users(id);
ALTER TABLE public.transactions ADD CONSTRAINT transactions_source_fkey FOREIGN KEY ("source") REFERENCES public.accounts(id);


INSERT INTO public.users
(first_name, last_name, email, username, passwd, ssn, usertype)
VALUES('test', 'testfname', 'test1111@mail.com', 'testunamee', 'testpass', '142363214', 'Customer');

CREATE or replace PROCEDURE createaccount(bl double precision, tp varchar(30),st varchar(64), uid int)
AS $$
declare last_id int := 0;
begin 
	
	 insert into  accounts(balance, acc_type,status)
				values(bl,tp,st) returning id into last_id ;
	
	insert into accounts_users_junction (user_id ,account_id) 
			  values( uid , last_id );
		  
end;
$$ language 'plpgsql';


create or replace function getaccountsbyuserid(userid int)
returns refcursor as $$
declare ref refcursor;
begin
	open ref for select acc.id , acc.acc_number ,acc.balance , acc.acc_type 
	,acc.status,acc.start_date 
	from accounts_users_junction auj, accounts acc
	where acc.id = auj.account_id and auj.user_id = userid
	order by acc.id desc;
	return ref;
end;
$$ language 'plpgsql';


create sequence accnoseq start 1000;

alter table accounts alter column acc_number set DEFAULT nextval('accnoseq');

