
    create table module (
       id integer not null,
        ects integer not null,
        module_type integer,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table module_relevance (
       module_id integer not null,
        relevance_id integer not null,
        primary key (module_id, relevance_id)
    ) engine=InnoDB

    create table module_semester_plan (
       module_id integer not null,
        semester_plan_id integer not null
    ) engine=InnoDB

    create table module_plan (
       id CHAR(36) not null,
        ects integer not null,
        module_name varchar(255),
        relevance integer not null,
        semester integer not null,
        semesterstart integer not null,
        year integer not null,
        primary key (id)
    ) engine=InnoDB

    create table relevance (
       id integer not null,
        relevance_point integer not null,
        specialisation integer,
        primary key (id)
    ) engine=InnoDB

    create table semester_plan (
       id integer not null,
        semester_type integer,
        year integer not null,
        primary key (id)
    ) engine=InnoDB

    create table studyplan (
       id CHAR(36) not null,
        date datetime,
        name varchar(255),
        study_program integer,
        primary key (id)
    ) engine=InnoDB

    create table studyplan_module (
       study_plan_id CHAR(36) not null,
        module_id CHAR(36) not null
    ) engine=InnoDB

    alter table module_relevance 
       add constraint UK_ob8slnjt590suvw7u80e8s564 unique (relevance_id)

    alter table module_semester_plan 
       add constraint UK_h41a9slhikuxquqioajmhmvid unique (semester_plan_id)

    alter table studyplan_module 
       add constraint UK_l59are4dgl61w753nnfjfpcvi unique (module_id)

    alter table module_relevance 
       add constraint FKnrney3f24x1wq1c0u3x86a0fr 
       foreign key (relevance_id) 
       references relevance (id)

    alter table module_relevance 
       add constraint FKa26ay3bu2opvhge75b3fvrwn9 
       foreign key (module_id) 
       references module (id)

    alter table module_semester_plan 
       add constraint FKt86utyl0cx7f9twmpsu6qfa12 
       foreign key (semester_plan_id) 
       references semester_plan (id)

    alter table module_semester_plan 
       add constraint FKp66t7svjtbl61h9ov0cdlnxid 
       foreign key (module_id) 
       references module (id)

    alter table studyplan_module 
       add constraint FKc4mct98hyjumtk1nb6axeou8a 
       foreign key (module_id) 
       references module_plan (id)

    alter table studyplan_module 
       add constraint FKrqxn1qnwnt2opaof1vxs2wyd9 
       foreign key (study_plan_id) 
       references studyplan (id)
