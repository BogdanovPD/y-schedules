open module why.studio.schedules {

    requires spring.beans;
    requires spring.context;
    requires spring.core;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.tx;

    requires java.sql;
    requires java.persistence;

    requires java.xml.bind;
    requires com.sun.xml.bind;

    requires net.bytebuddy;
    requires org.hibernate.orm.core;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;

    requires com.google.api.client;
    requires google.api.client;
    requires google.api.services.calendar.v3.rev20181125;
    requires google.oauth.client;
    requires google.http.client.jackson2;
    requires google.oauth.client.java6;
    requires google.oauth.client.jetty;

    requires static lombok;

}